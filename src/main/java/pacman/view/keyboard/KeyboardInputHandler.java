package pacman.view.keyboard;

import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import pacman.model.engine.GameEngine;
import pacman.model.entity.dynamic.player.Controllable;
import pacman.command.*;

/**
 * Responsible for handling keyboard input from player
 */
public class KeyboardInputHandler {
  private final Controllable pacman;
  private final RemoteControl remoteControl;

  public KeyboardInputHandler(GameEngine gameEngine) {

    this.pacman = (Controllable) gameEngine.getPacman();
    this.remoteControl = new RemoteControl(pacman);
    setCommands();
  }

  private void setCommands() {
    remoteControl.setCommand(KeyCode.LEFT, new ConcreteLeftCommand(pacman));
    remoteControl.setCommand(KeyCode.RIGHT, new ConcreteRightCommand(pacman));
    remoteControl.setCommand(KeyCode.UP, new ConcreteUpCommand(pacman));
    remoteControl.setCommand(KeyCode.DOWN, new ConcreteDownCommand(pacman));
  }

  public void handlePressed(KeyEvent keyEvent) {
    KeyCode keyCode = keyEvent.getCode();
    switch (keyCode) {
      case LEFT:
        remoteControl.onKeyPressed(KeyCode.LEFT);
        break;
      case RIGHT:
        remoteControl.onKeyPressed(KeyCode.RIGHT);
        break;
      case DOWN:
        remoteControl.onKeyPressed(KeyCode.DOWN);
        break;
      case UP:
        remoteControl.onKeyPressed(KeyCode.UP);
        break;
    }
  }
}
