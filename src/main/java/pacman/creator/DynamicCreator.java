package pacman.creator;

import javafx.scene.image.Image;
import pacman.model.entity.Renderable;
import pacman.model.entity.dynamic.ghost.GhostImpl;
import pacman.model.entity.dynamic.ghost.GhostMode;
import pacman.model.entity.dynamic.physics.BoundingBoxImpl;
import pacman.model.entity.dynamic.physics.Direction;
import pacman.model.entity.dynamic.physics.KinematicStateImpl;
import pacman.model.entity.dynamic.physics.Vector2D;
import pacman.model.entity.dynamic.player.Pacman;
import pacman.model.entity.dynamic.player.PacmanVisual;
import pacman.model.maze.RenderableType;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class DynamicCreator extends EntityCreator {
    Map<Integer, Vector2D> corners = new HashMap<>();

    public DynamicCreator(Image image) {
        super(image);
        corners.put(1, new Vector2D(16, 64));
        corners.put(2, new Vector2D(26 * 16, 64));
        corners.put(3, new Vector2D(16, 32 * 16));
        corners.put(4, new Vector2D(26 * 16, 32 * 16));
    }

    public Renderable createRenderableObject(int x, int y, char rendererSymbol) {
        BoundingBoxImpl boundingBox = getBoundingBoxOfObject(x, y-4);
        KinematicStateImpl kinematicState = new KinematicStateImpl.KinematicStateBuilder()
                .setPosition(new Vector2D(boundingBox.getLeftX(), boundingBox.getTopY())).build();
        if (rendererSymbol == RenderableType.GHOST) {
            Random random = new Random();
            int randomNum = 1;
            Vector2D targetCorner = corners.get(randomNum);
            return new GhostImpl(super.image, boundingBox, kinematicState, GhostMode.SCATTER, targetCorner, Direction.values()[random.nextInt(4)]);
        }
        Map<PacmanVisual, Image> pacmanVisuals = new HashMap<PacmanVisual, Image>();
        pacmanVisuals.put(PacmanVisual.UP, new Image(getClass().getResource("/maze/pacman/playerUp.png").toExternalForm()));
        pacmanVisuals.put(PacmanVisual.CLOSED, new Image(getClass().getResource("/maze/pacman/playerClosed.png").toExternalForm()));
        pacmanVisuals.put(PacmanVisual.DOWN, new Image(getClass().getResource("/maze/pacman/playerDown.png").toExternalForm()));
        pacmanVisuals.put(PacmanVisual.LEFT, new Image(getClass().getResource("/maze/pacman/playerLeft.png").toExternalForm()));
        pacmanVisuals.put(PacmanVisual.RIGHT, new Image(getClass().getResource("/maze/pacman/playerRight.png").toExternalForm()));
        return new Pacman(pacmanVisuals.get(PacmanVisual.CLOSED), pacmanVisuals, boundingBox, kinematicState);
    }
}
