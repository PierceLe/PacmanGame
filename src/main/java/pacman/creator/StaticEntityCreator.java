package pacman.creator;

import javafx.scene.image.Image;
import pacman.model.entity.Renderable;
import pacman.model.entity.dynamic.physics.BoundingBoxImpl;
import pacman.model.entity.dynamic.physics.Vector2D;
import pacman.model.entity.staticentity.CellEntityImpl;
import pacman.model.entity.staticentity.StaticEntityImpl;
import pacman.model.entity.staticentity.collectable.Pellet;
import pacman.model.maze.RenderableType;

public class StaticEntityCreator extends EntityCreator {
    public StaticEntityCreator(Image image) {
        super(image);
    }

    public Renderable createRenderableObject(int x, int y, char rendererSymbol) {
        if (rendererSymbol == RenderableType.PATH) {
            Vector2D vector2D = new Vector2D(x, y);
            BoundingBoxImpl boundingBox = new BoundingBoxImpl(vector2D, 16, 16);
            return new CellEntityImpl(boundingBox, Renderable.Layer.BACKGROUND, null);

        }
        BoundingBoxImpl boundingBoxOfObject = getBoundingBoxOfObject(x, y);
        if (rendererSymbol == RenderableType.PELLET) {
            return new Pellet(boundingBoxOfObject, Renderable.Layer.BACKGROUND, super.image, 100);
        }
        return new StaticEntityImpl(boundingBoxOfObject, Renderable.Layer.BACKGROUND, super.image);
    }
}
