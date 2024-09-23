package pacman.view.keyboard.command;

import pacman.model.entity.dynamic.physics.Direction;
import pacman.model.entity.dynamic.player.Controllable;

public class ConcreteLeftCommand implements Command {
    private Controllable pacman;

    public ConcreteLeftCommand(Controllable pacman) {
        this.pacman = pacman;
    }

    public void execute() {
        if (pacman.isValidMove(Direction.LEFT)) {
            pacman.down();
        }
        else {
            pacman.setLastDirection(Direction.LEFT);
        }
    }

}
