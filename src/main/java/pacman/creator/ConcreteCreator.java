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
import pacman.model.entity.staticentity.StaticEntityImpl;
import pacman.model.entity.staticentity.collectable.Pellet;
import pacman.model.maze.RenderableType;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class ConcreteCreator implements Creator {
    public Renderable createRenderableObject(char rendererType, int x, int y) {
        if (rendererType == RenderableType.HORIZONTAL_WALL) {
            Double[] topLeftCordinate = topLeftCoordinate(x, y, 16, 16);
            BoundingBoxImpl boundingBoxOfObject = new BoundingBoxImpl(new Vector2D(topLeftCordinate[0], topLeftCordinate[1]),16, 16);
            Image image = new Image(getClass().getResource("/maze/walls/horizontal.png").toExternalForm());
            return new StaticEntityImpl(boundingBoxOfObject, Renderable.Layer.BACKGROUND, image);
        }
        if (rendererType == RenderableType.VERTICAL_WALL) {
            Double[] topLeftCordinate = topLeftCoordinate(x, y, 16, 16);
            BoundingBoxImpl boundingBoxOfObject = new BoundingBoxImpl(new Vector2D(topLeftCordinate[0], topLeftCordinate[1]),16, 16);
            Image image = new Image(getClass().getResource("/maze/walls/vertical.png").toExternalForm());
            return new StaticEntityImpl(boundingBoxOfObject, Renderable.Layer.BACKGROUND, image);
        }
        if (rendererType == RenderableType.DOWN_LEFT_WALL) {
            Double[] topLeftCordinate = topLeftCoordinate(x, y, 16, 16);
            BoundingBoxImpl boundingBoxOfObject = new BoundingBoxImpl(new Vector2D(topLeftCordinate[0], topLeftCordinate[1]),16, 16);
            Image image = new Image(getClass().getResource("/maze/walls/downLeft.png").toExternalForm());
            return new StaticEntityImpl(boundingBoxOfObject, Renderable.Layer.BACKGROUND, image);
        }
        if (rendererType == RenderableType.DOWN_RIGHT_WALL) {
            Double[] topLeftCordinate = topLeftCoordinate(x, y, 16, 16);
            BoundingBoxImpl boundingBoxOfObject = new BoundingBoxImpl(new Vector2D(topLeftCordinate[0], topLeftCordinate[1]),16, 16);
            Image image = new Image(getClass().getResource("/maze/walls/downRight.png").toExternalForm());
            return new StaticEntityImpl(boundingBoxOfObject, Renderable.Layer.BACKGROUND, image);
        }
        if (rendererType == RenderableType.UP_LEFT_WALL) {
            Double[] topLeftCordinate = topLeftCoordinate(x, y, 16, 16);
            BoundingBoxImpl boundingBoxOfObject = new BoundingBoxImpl(new Vector2D(topLeftCordinate[0], topLeftCordinate[1]),16, 16);
            Image image = new Image(getClass().getResource("/maze/walls/upLeft.png").toExternalForm());
            return new StaticEntityImpl(boundingBoxOfObject, Renderable.Layer.BACKGROUND, image);
        }
        if (rendererType == RenderableType.UP_RIGHT_WALL) {
            Double[] topLeftCordinate = topLeftCoordinate(x, y, 16, 16);
            BoundingBoxImpl boundingBoxOfObject = new BoundingBoxImpl(new Vector2D(topLeftCordinate[0], topLeftCordinate[1]),16, 16);
            Image image = new Image(getClass().getResource("/maze/walls/upRight.png").toExternalForm());
            return new StaticEntityImpl(boundingBoxOfObject, Renderable.Layer.BACKGROUND, image);
        }
        if (rendererType == RenderableType.PELLET) {
            Double[] topLeftCordinate = topLeftCoordinate(x, y, 16, 16);
            BoundingBoxImpl boundingBoxOfObject = new BoundingBoxImpl(new Vector2D(topLeftCordinate[0], topLeftCordinate[1]),16, 16);
            Image image = new Image(getClass().getResource("/maze/pellet.png").toExternalForm());
            return new Pellet(boundingBoxOfObject, Renderable.Layer.FOREGROUND, image, 100);
        }
        if (rendererType == RenderableType.GHOST) {
            Double[] topLeftCordinate = topLeftCoordinate(x, y, 28, 28);
            BoundingBoxImpl boundingBoxOfObject = new BoundingBoxImpl(new Vector2D(topLeftCordinate[0], topLeftCordinate[1]),28, 28);
            Image image = new Image(getClass().getResource("/maze/ghosts/ghost.png").toExternalForm());
            KinematicStateImpl kinematicState = new KinematicStateImpl.KinematicStateBuilder()
                    .setPosition(new Vector2D(topLeftCordinate[0], topLeftCordinate[1])).build();
            GhostMode ghostMode = GhostMode.SCATTER;
            Map<Integer, Vector2D> corners = new HashMap<>();
            corners.put(1, new Vector2D(0, 0));
            corners.put(2, new Vector2D(28, 28));
            corners.put(3, new Vector2D(0, 28));
            corners.put(4, new Vector2D(28, 0));
            Random random = new Random();
            int randomNum = random.nextInt(4) + 1;
            Vector2D targetCorner = corners.get(randomNum);
            return new GhostImpl(image, boundingBoxOfObject, kinematicState, ghostMode, targetCorner, Direction.LEFT);

        }
        if (rendererType == RenderableType.PACMAN) {
            Double[] topLeftCordinate = topLeftCoordinate(x, y, 16, 16);
            BoundingBoxImpl boundingBoxOfObject = new BoundingBoxImpl(new Vector2D(topLeftCordinate[0], topLeftCordinate[1]),16, 16);
            Map<PacmanVisual, Image> pacmanVisuals = new HashMap<PacmanVisual, Image>();
            pacmanVisuals.put(PacmanVisual.UP, new Image(getClass().getResource("/maze/pacman/playerUp.png").toExternalForm()));
            pacmanVisuals.put(PacmanVisual.CLOSED, new Image(getClass().getResource("/maze/pacman/playerClosed.png").toExternalForm()));
            pacmanVisuals.put(PacmanVisual.DOWN, new Image(getClass().getResource("/maze/pacman/playerDown.png").toExternalForm()));
            pacmanVisuals.put(PacmanVisual.LEFT, new Image(getClass().getResource("/maze/pacman/playerLeft.png").toExternalForm()));
            pacmanVisuals.put(PacmanVisual.RIGHT, new Image(getClass().getResource("/maze/pacman/playerRight.png").toExternalForm()));

            KinematicStateImpl kinematicState = new KinematicStateImpl.KinematicStateBuilder()
                    .setPosition(new Vector2D(topLeftCordinate[0], topLeftCordinate[1])).build();
            return new Pacman(pacmanVisuals.get(PacmanVisual.CLOSED), pacmanVisuals, boundingBoxOfObject,kinematicState);
        }
        if (rendererType == 0) {
            return null;
        }
        return null;
    }

    private Double[] topLeftCoordinate(int x, int y, int height, int width) {
        Double middleCoordinateX = (double) (16 * x + 16/2);
        Double middleCoordinateY = (double) (16 * y - 16/2);

        return new Double[]{middleCoordinateX - width / 2, middleCoordinateY - height / 2};
    }
}
