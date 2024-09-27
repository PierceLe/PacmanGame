package pacman.entityfactory.staticentity;

import javafx.scene.image.Image;
import pacman.entityfactory.EntityFactory;
import pacman.model.entity.Renderable;

public abstract class StaticEntityFactory extends EntityFactory {
    public StaticEntityFactory(Image image) {
        super(image);
        layer = Renderable.Layer.BACKGROUND;
    }

    public abstract Renderable createRenderableObject(int x, int y);
}