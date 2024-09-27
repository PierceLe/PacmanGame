package pacman.entityfactory.staticentity;

import javafx.scene.image.Image;
import pacman.model.entity.Renderable;
import pacman.model.entity.dynamic.physics.BoundingBoxImpl;
import pacman.model.entity.dynamic.physics.Vector2D;
import pacman.model.entity.staticentity.StaticEntityImpl;

public class EmptyCellFactory extends StaticEntityFactory {
    public EmptyCellFactory(Image image) {
        super(image);

    }

    @Override
    public Renderable createRenderableObject(int x, int y) {
        Vector2D vector2D = new Vector2D(x, y);
        BoundingBoxImpl boundingBox = new BoundingBoxImpl(vector2D, 16, 16);
        StaticEntityImpl emptyCellObject = new StaticEntityImpl(boundingBox, layer, null);
        emptyCellObject.seteCanPassThrough(true);
        return emptyCellObject;
    }
}
