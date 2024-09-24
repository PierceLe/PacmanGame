package pacman.view;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.util.Duration;
import pacman.model.engine.GameEngine;
import pacman.model.entity.Renderable;
import pacman.view.background.BackgroundDrawer;
import pacman.view.background.StandardBackgroundDrawer;
import pacman.view.entity.EntityView;
import pacman.view.entity.EntityViewImpl;
import pacman.view.keyboard.KeyboardInputHandler;
import pacman.view.observer.LivesObserver;
import pacman.view.observer.ScoreObserver;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Responsible for managing the Pac-Man Game View
 */
public class GameWindow implements ScoreObserver, LivesObserver {

  public static final File FONT_FILE = new File("src/main/resources/maze/PressStart2P-Regular.ttf");

  private final Scene scene;
  private final Pane pane;
  private final GameEngine model;
  private final List<EntityView> entityViews;

  private final Label scoreLabel;
  private final int numLives = 3;
  private final List<ImageView> livesImageViews = new ArrayList<>();
  private final Label gameOverLabel = new Label("GAME OVER");

  public GameWindow(GameEngine model, int width, int height) {
    this.model = model;

    //init score label
    scoreLabel = new Label("Score: " + 123);
    pane = new Pane();
    scene = new Scene(pane, width, height);

    entityViews = new ArrayList<>();

    // Add the score label to the pane
    scoreLabel.setLayoutX(10);
    scoreLabel.setLayoutY(10);
    scoreLabel.setStyle("-fx-text-fill: white");
    pane.getChildren().add(scoreLabel);
    // Add the game over label to the pane
    for (int i = 0; i < numLives; i++) {
      ImageView livesImageView = new ImageView("/maze/pacman/playerRight.png");
      livesImageView.setFitWidth(20);
      livesImageView.setFitHeight(20);
      livesImageView.setLayoutX(10 + i * 35);
      livesImageView.setLayoutY(height - 30);
      livesImageViews.add(livesImageView);
      pane.getChildren().add(livesImageView);
    }

    // Add the game over label to the pane
    gameOverLabel.setStyle("-fx-text-fill: red; -fx-font-size: 24;");
    gameOverLabel.setVisible(false);
    Platform.runLater(() -> {
      gameOverLabel.setLayoutX(scene.getWidth() / 2 - gameOverLabel.getWidth() / 2);
      gameOverLabel.setLayoutY(scene.getHeight() / 2 - gameOverLabel.getHeight() / 2 + 40); // Cách vị trí trung tâm 20px
    });
    pane.getChildren().add(gameOverLabel);

    KeyboardInputHandler keyboardInputHandler = new KeyboardInputHandler(model);
    scene.setOnKeyPressed(keyboardInputHandler::handlePressed);

    BackgroundDrawer backgroundDrawer = new StandardBackgroundDrawer();
    backgroundDrawer.draw(model, pane);
  }

  public Scene getScene() {
    return scene;
  }

  public void run() {
    Timeline timeline = new Timeline(new KeyFrame(Duration.millis(34),
        t -> this.draw()));

    timeline.setCycleCount(Timeline.INDEFINITE);
    timeline.play();

    model.startGame();
  }

  private void draw() {
    model.tick();

    List<Renderable> entities = model.getRenderables();

    for (EntityView entityView : entityViews) {
      entityView.markForDelete();
    }

    for (Renderable entity : entities) {
      boolean notFound = true;
      for (EntityView view : entityViews) {
        if (view.matchesEntity(entity)) {
          notFound = false;
          view.update();
          break;
        }
      }
      if (notFound) {
        EntityView entityView = new EntityViewImpl(entity);
        entityViews.add(entityView);
        pane.getChildren().add(entityView.getNode());
      }
    }

    for (EntityView entityView : entityViews) {
      if (entityView.isMarkedForDelete()) {
        pane.getChildren().remove(entityView.getNode());
      }
    }

    entityViews.removeIf(EntityView::isMarkedForDelete);
  }

  @Override
  public void updateLives(int lives) {
    for (int i = 0; i < numLives; i++) {
      livesImageViews.get(i).setVisible(i < lives);
    }

    if (lives == 0) {
      gameOverLabel.setVisible(true);
    }
  }

  @Override
  public void updateScore(int newScore) {
    scoreLabel.setText("Score: " + newScore);
  }
}
