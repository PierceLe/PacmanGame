package pacman.entityfactory.dynamicentity;

import javafx.scene.image.Image;
import pacman.entityfactory.EntityFactory;
import pacman.model.entity.Renderable;
import pacman.model.entity.dynamic.physics.*;


public abstract class DynamicFactory extends EntityFactory {

    public DynamicFactory(Image image) {
        super(image);
    }
    public abstract Renderable createRenderableObject(int x, int y);

    protected KinematicStateImpl getKinematicState(BoundingBox boundingBoxObj) {
        return new KinematicStateImpl.KinematicStateBuilder()
                .setPosition(new Vector2D(boundingBoxObj.getLeftX(), boundingBoxObj.getTopY())).build();
    }

    @Override
    protected BoundingBoxImpl getBoundingBoxOfObject(int x, int y) {
        double imageWidth = image.getWidth();
        double imageHeight = image.getHeight();
        Vector2D topLeftCorner = new Vector2D(x + 4 , y - 4);
        return new BoundingBoxImpl(topLeftCorner, imageHeight, imageWidth);
    }
}
