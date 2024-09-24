package pacman.view.keyboard.command;

import pacman.model.entity.dynamic.physics.Direction;
import pacman.model.entity.dynamic.player.Controllable;

public class ConcreteDownCommand implements Command {
    private Controllable pacman;

    public ConcreteDownCommand(Controllable pacman) {
        this.pacman = pacman;
    }

    public void execute() {
        if (pacman.isValidMove(Direction.DOWN)) {
            pacman.down();
        }
        else {
            pacman.setLastDirection(Direction.DOWN);
        }
    }

}
