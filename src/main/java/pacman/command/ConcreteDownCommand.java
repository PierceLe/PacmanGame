package pacman.command;

import pacman.model.entity.dynamic.physics.Direction;
import pacman.model.entity.dynamic.player.Controllable;

public class ConcreteDownCommand implements Command {
    private final Controllable pacman;

    public ConcreteDownCommand(Controllable pacman) {
        this.pacman = pacman;
    }

    public void execute() {
        if (pacman.getPossibleDirections().contains(Direction.DOWN)) {
            pacman.down();
        }
        else {
            pacman.setPreviousDirection(Direction.DOWN);
        }
    }

}
