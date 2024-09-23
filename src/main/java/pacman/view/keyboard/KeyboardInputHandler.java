package pacman.view.keyboard;

import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import pacman.model.engine.GameEngine;

/**
 * Responsible for handling keyboard input from player
 */
public class KeyboardInputHandler {
  private final GameEngine pacman;

  public KeyboardInputHandler(GameEngine pacman) {
    this.pacman = pacman;
  }

  public void handlePressed(KeyEvent keyEvent) {
    KeyCode keyCode = keyEvent.getCode();

    switch (keyCode) {
      case LEFT:
        pacman.moveLeft();
        break;
      case RIGHT:
        pacman.moveRight();
        break;
      case DOWN:
        pacman.moveDown();
        break;
      case UP:
        pacman.moveUp();
        break;
    }
  }
}
