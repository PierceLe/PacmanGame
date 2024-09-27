package pacman.view;

import javafx.animation.KeyFrame;
import javafx.animation.PauseTransition;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.util.Duration;
import pacman.model.engine.GameEngine;
import pacman.model.engine.GameState;
import pacman.model.entity.Renderable;
import pacman.view.background.BackgroundDrawer;
import pacman.view.background.StandardBackgroundDrawer;
import pacman.view.entity.EntityView;
import pacman.view.entity.EntityViewImpl;
import pacman.view.keyboard.KeyboardInputHandler;
import pacman.observer.gameStateObserver.GameStateObserver;
import pacman.observer.livesObserver.LivesObserver;
import pacman.observer.livesObserver.LivesSubject;
import pacman.observer.scoreObserver.ScoreObserver;
import pacman.observer.scoreObserver.ScoreSubject;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Responsible for managing the Pac-Man Game View
 */
public class GameWindow implements ScoreObserver, LivesObserver, GameStateObserver {

  public static final File FONT_FILE = new File("src/main/resources/maze/PressStart2P-Regular.ttf");

  private final Scene scene;
  private final Pane pane;
  private final GameEngine model;
  private final List<EntityView> entityViews;

  private final Label scoreLabel;
  private final int numLives = 3;
  private final List<ImageView> livesImageViews = new ArrayList<>();
  private final Label gameOverLabel = new Label("GAME OVER");
  private Text stateText;

  public GameWindow(GameEngine model, int width, int height) {
    this.model = model;

    // Create a score observer
    ((ScoreSubject) model.getPacman()).registerObserver(this);
    model.registerObserver(this);

    //init score label
    scoreLabel = new Label("0");
    pane = new Pane();
    scene = new Scene(pane, width, height);

    entityViews = new ArrayList<>();

    // Add the score label to the pane
    scoreLabel.setLayoutX(10);
    scoreLabel.setLayoutY(10);
    scoreLabel.setStyle("-fx-text-fill: white");
    // set font FONT_FILE
    scoreLabel.setFont(Font.loadFont("file:" + FONT_FILE.getAbsolutePath(), 24));

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
    gameOverLabel.setStyle("-fx-text-fill: red;");
    gameOverLabel.setVisible(false);
    Platform.runLater(() -> {
      // set font FONT_FILE
      gameOverLabel.setFont(Font.loadFont("file:" + FONT_FILE.getAbsolutePath(), 0));
      gameOverLabel.setLayoutX(scene.getWidth() / 2 - gameOverLabel.getWidth() / 2 - 20);
      gameOverLabel.setLayoutY(scene.getHeight() / 2 - gameOverLabel.getHeight() / 2 + 40);
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
    // Create a lives observer
  }

  private void draw() {
    model.tick();
    ((LivesSubject) model.getLevelImpl()).registerObserver(this);

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
  }

  @Override
  public void updateScore(int newScore) {
    scoreLabel.setText(String.format("%d", newScore));
  }

  @Override
  public void updateState() {
    if (stateText == null) {
      stateText = new Text();
      stateText.setX(165);
      stateText.setY(340);
      stateText.setFont(javafx.scene.text.Font.loadFont("file:" + FONT_FILE.getAbsolutePath(), 20));
      pane.getChildren().add(stateText);
    }
    if (model.getGameState() == GameState.LOADING) {
      drawStateBasedOnContentAndColour("READY!", "YELLOW");
      PauseTransition pause = new PauseTransition(Duration.seconds(3.4));
      pause.setOnFinished(e -> {
        stateText.setText("");
        model.updateState(GameState.PROCESSING);
      });
      pause.play();
    } else if (model.getGameState() == GameState.LOSE) {
      gameOverLabel.setVisible(true);
      PauseTransition pause = new PauseTransition(Duration.seconds(3.4));
      pause.setOnFinished(e -> {
        stateText.setText("");
        System.exit(0);
      });
      pause.play();
    }
    else if (model.getGameState() == GameState.WIN) {
      drawStateBasedOnContentAndColour("YOU WIN!", "WHITE");
      PauseTransition pause = new PauseTransition(Duration.seconds(3.4));
      pause.setOnFinished(e -> {
        stateText.setText("");
        System.exit(0);
      });
      pause.play();
    } else {
      stateText.setText("");
    }


  }
  private void drawStateBasedOnContentAndColour(String content, String colour) {
    stateText.setText(content);
    stateText.setFill(Paint.valueOf(colour));
  }
}
