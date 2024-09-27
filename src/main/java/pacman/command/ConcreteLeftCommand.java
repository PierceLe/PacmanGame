package pacman.command;

import pacman.model.entity.dynamic.physics.Direction;
import pacman.model.entity.dynamic.player.Controllable;

public class ConcreteLeftCommand implements Command {
    private final Controllable pacman;

    public ConcreteLeftCommand(Controllable pacman) {
        this.pacman = pacman;
    }

    public void execute() {
        if (pacman.getPossibleDirections().contains(Direction.LEFT)) {
            pacman.left();
        }
        else {
            pacman.setPreviousDirection(Direction.LEFT);
        }
    }

}
