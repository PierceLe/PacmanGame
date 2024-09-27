package pacman.entityfactory.staticentity;

import javafx.scene.image.Image;
import pacman.model.entity.Renderable;
import pacman.model.entity.staticentity.StaticEntityImpl;

public class WallsFactory extends StaticEntityFactory {
    public WallsFactory(Image image) {
        super(image);
    }

    @Override
    public Renderable createRenderableObject(int x, int y) {
        return new StaticEntityImpl(getBoundingBoxOfObject(x, y), layer, image);
    }
}
