package pacman.creator;

import javafx.scene.image.Image;
import pacman.model.entity.Renderable;
import pacman.model.entity.dynamic.physics.BoundingBoxImpl;
import pacman.model.entity.dynamic.physics.Vector2D;
import pacman.model.entity.staticentity.StaticEntityImpl;
import pacman.model.maze.RenderableType;

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
            return new StaticEntityImpl(boundingBoxOfObject, Renderable.Layer.BACKGROUND, new Image(getClass().getResource("/maze/walls/vertical.png").toExternalForm()));
        }
        if (rendererType == RenderableType.DOWN_LEFT_WALL) {
            Double[] topLeftCordinate = topLeftCoordinate(x, y, 16, 16);
            BoundingBoxImpl boundingBoxOfObject = new BoundingBoxImpl(new Vector2D(topLeftCordinate[0], topLeftCordinate[1]),16, 16);
            return new StaticEntityImpl(boundingBoxOfObject, Renderable.Layer.BACKGROUND, new Image(getClass().getResource("/maze/walls/downLeft.png").toExternalForm()));
        }
        if (rendererType == RenderableType.DOWN_RIGHT_WALL) {
            Double[] topLeftCordinate = topLeftCoordinate(x, y, 16, 16);
            BoundingBoxImpl boundingBoxOfObject = new BoundingBoxImpl(new Vector2D(topLeftCordinate[0], topLeftCordinate[1]),16, 16);
            return new StaticEntityImpl(boundingBoxOfObject, Renderable.Layer.BACKGROUND, new Image(getClass().getResource("/maze/walls/downRight.png").toExternalForm()));
        }
        if (rendererType == RenderableType.UP_LEFT_WALL) {
            Double[] topLeftCordinate = topLeftCoordinate(x, y, 16, 16);
            BoundingBoxImpl boundingBoxOfObject = new BoundingBoxImpl(new Vector2D(topLeftCordinate[0], topLeftCordinate[1]),16, 16);
            return new StaticEntityImpl(boundingBoxOfObject, Renderable.Layer.BACKGROUND, new Image(getClass().getResource("/maze/walls/upLeft.png").toExternalForm()));
        }
        if (rendererType == RenderableType.UP_RIGHT_WALL) {
            Double[] topLeftCordinate = topLeftCoordinate(x, y, 16, 16);
            BoundingBoxImpl boundingBoxOfObject = new BoundingBoxImpl(new Vector2D(topLeftCordinate[0], topLeftCordinate[1]),16, 16);
            return new StaticEntityImpl(boundingBoxOfObject, Renderable.Layer.BACKGROUND, new Image(getClass().getResource("/maze/walls/upRight.png").toExternalForm()));
        }
        if (rendererType == RenderableType.PELLET) {
            Double[] topLeftCordinate = topLeftCoordinate(x, y, 16, 16);
            BoundingBoxImpl boundingBoxOfObject = new BoundingBoxImpl(new Vector2D(topLeftCordinate[0], topLeftCordinate[1]),16, 16);
            return new StaticEntityImpl(boundingBoxOfObject, Renderable.Layer.FOREGROUND, new Image(getClass().getResource("/maze/pellet.png").toExternalForm()));
        }
        if (rendererType == RenderableType.GHOST) {
            Double[] topLeftCordinate = topLeftCoordinate(x, y, 28, 28);
            BoundingBoxImpl boundingBoxOfObject = new BoundingBoxImpl(new Vector2D(topLeftCordinate[0], topLeftCordinate[1]),28, 28);
            return new StaticEntityImpl(boundingBoxOfObject, Renderable.Layer.FOREGROUND, new Image(getClass().getResource("/maze/ghosts/ghost.png").toExternalForm()));
        }
        if (rendererType == RenderableType.PACMAN) {
            Double[] topLeftCordinate = topLeftCoordinate(x, y, 16, 16);
            BoundingBoxImpl boundingBoxOfObject = new BoundingBoxImpl(new Vector2D(topLeftCordinate[0], topLeftCordinate[1]),16, 16);
            return new StaticEntityImpl(boundingBoxOfObject, Renderable.Layer.FOREGROUND, new Image(getClass().getResource("/maze/pacman/playerDown.png").toExternalForm()));
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
