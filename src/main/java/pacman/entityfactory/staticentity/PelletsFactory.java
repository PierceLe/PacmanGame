package pacman.entityfactory.staticentity;

import javafx.scene.image.Image;
import pacman.model.entity.Renderable;
import pacman.model.entity.staticentity.collectable.Pellet;

public class PelletsFactory extends StaticEntityFactory {
    private final int POINT;
    public PelletsFactory(Image image) {
        super(image);
        layer = Renderable.Layer.FOREGROUND;
        POINT = 100;
    }

    @Override
    public Renderable createRenderableObject(int x, int y) {
        return new Pellet(getBoundingBoxOfObject(x, y), layer, image, POINT);
    }
}
