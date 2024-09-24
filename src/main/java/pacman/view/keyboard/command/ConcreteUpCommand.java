package pacman.view.keyboard.command;

import pacman.model.entity.dynamic.physics.Direction;
import pacman.model.entity.dynamic.player.Controllable;

public class ConcreteUpCommand implements Command {
    private Controllable pacman;

    public ConcreteUpCommand(Controllable pacman) {
        this.pacman = pacman;
    }

    public void execute() {
        if (pacman.isValidMove(Direction.UP)) {
            pacman.up();
        }
        else {
            pacman.setLastDirection(Direction.UP);
        }
    }

}
