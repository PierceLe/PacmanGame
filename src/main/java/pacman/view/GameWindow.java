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

  private Label scoreLabel;
  private final int numLives = 3;
  private final List<ImageView> livesImageViews = new ArrayList<>();
  private final Label stateLabel = new Label();

  public GameWindow(GameEngine model, int width, int height) {
    entityViews = new ArrayList<>();
    this.model = model;
    this.pane = new Pane();
    this.scene = new Scene(pane, width, height);

    registerObservers();
    initializeScoreLabel();
    initializeLivesImages(height);
    initializeStateLabel();
    initializeKeyboardHandler();
    initializeBackground();
  }

  private void registerObservers() {
    ((ScoreSubject) model.getPacman()).registerObserver(this);
    model.registerObserver(this);
  }

  private void initializeScoreLabel() {
    scoreLabel = new Label("0");
    scoreLabel.setLayoutX(10);
    scoreLabel.setLayoutY(10);
    scoreLabel.setStyle("-fx-text-fill: white");
    scoreLabel.setFont(Font.loadFont("file:" + FONT_FILE.getAbsolutePath(), 24));
    pane.getChildren().add(scoreLabel);
  }

  private void initializeLivesImages(int height) {
    for (int i = 0; i < numLives; i++) {
      ImageView livesImageView = new ImageView("/maze/pacman/playerRight.png");
      livesImageView.setFitWidth(20);
      livesImageView.setFitHeight(20);
      livesImageView.setLayoutX(10 + i * 35);
      livesImageView.setLayoutY(height - 30);
      livesImageViews.add(livesImageView);
      pane.getChildren().add(livesImageView);
    }
  }

  private void initializeStateLabel() {
    Platform.runLater(() -> {
      stateLabel.setFont(Font.loadFont("file:" + FONT_FILE.getAbsolutePath(), 0));
      stateLabel.setLayoutX(scene.getWidth() / 2 - stateLabel.getWidth() / 2 - 20);
      stateLabel.setLayoutY(scene.getHeight() / 2 - stateLabel.getHeight() / 2 + 40);
    });
    pane.getChildren().add(stateLabel);
  }

  private void initializeKeyboardHandler() {
    KeyboardInputHandler keyboardInputHandler = new KeyboardInputHandler(model);
    scene.setOnKeyPressed(keyboardInputHandler::handlePressed);
  }

  private void initializeBackground() {
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
    if (model.getGameState() == GameState.LOADING) {
      updateStateLabelWithPause("READY!", "YELLOW", 3.4, () -> model.updateState(GameState.PROCESSING));
    } else if (model.getGameState() == GameState.LOSE) {
      updateStateLabelWithPause("GAME OVER", "RED", 3.4, () -> System.exit(0));
    } else if (model.getGameState() == GameState.WIN) {
      updateStateLabelWithPause("YOU WIN!", "WHITE", 3.4, () -> System.exit(0));
    } else {
      stateLabel.setVisible(false);
    }
  }

  private void updateStateLabelWithPause(String text, String color, double duration, Runnable onFinish) {
    stateLabel.setText(text);
    stateLabel.setTextFill(Paint.valueOf(color));
    stateLabel.setVisible(true);

    PauseTransition pause = new PauseTransition(Duration.seconds(duration));
    pause.setOnFinished(e -> onFinish.run());
    pause.play();
  }
}