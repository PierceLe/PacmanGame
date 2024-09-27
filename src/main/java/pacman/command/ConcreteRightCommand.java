package pacman.command;

import pacman.model.entity.dynamic.physics.Direction;
import pacman.model.entity.dynamic.player.Controllable;

public class ConcreteRightCommand implements Command {
    private Controllable pacman;

    public ConcreteRightCommand(Controllable pacman) {
        this.pacman = pacman;
    }

    public void execute() {
        if (pacman.isValidMove(Direction.RIGHT)) {
            pacman.right();
        }
        else {
            pacman.setLastDirection(Direction.RIGHT);
        }
    }

}
