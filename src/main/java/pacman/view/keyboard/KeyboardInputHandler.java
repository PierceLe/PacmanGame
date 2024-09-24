package pacman.view.keyboard;

import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import pacman.model.engine.GameEngine;
import pacman.model.entity.Renderable;
import pacman.model.entity.dynamic.player.Controllable;
import pacman.model.entity.dynamic.player.Pacman;
import pacman.view.keyboard.command.*;

/**
 * Responsible for handling keyboard input from player
 */
public class KeyboardInputHandler {
  private final Controllable pacman;
  private RemoteController remoteController;

  public KeyboardInputHandler(GameEngine gameEngine) {

    this.pacman = (Controllable) gameEngine.getPacman();
    this.remoteController = new RemoteController(pacman);
    remoteController.setCommand(KeyCode.LEFT, new ConcreteLeftCommand(pacman));
    remoteController.setCommand(KeyCode.RIGHT, new ConcreteRightCommand(pacman));
    remoteController.setCommand(KeyCode.UP, new ConcreteUpCommand(pacman));
    remoteController.setCommand(KeyCode.DOWN, new ConcreteDownCommand(pacman));
  }

  public void handlePressed(KeyEvent keyEvent) {
    KeyCode keyCode = keyEvent.getCode();
    switch (keyCode) {
      case LEFT:
        remoteController.onKeyPressed(KeyCode.LEFT);
        break;
      case RIGHT:
        remoteController.onKeyPressed(KeyCode.RIGHT);
        break;
      case DOWN:
        remoteController.onKeyPressed(KeyCode.DOWN);
        break;
      case UP:
        remoteController.onKeyPressed(KeyCode.UP);
        break;
    }
  }
}
