package pacman.entityfactory;

import javafx.scene.image.Image;
import pacman.model.entity.Renderable;
import pacman.model.entity.dynamic.physics.BoundingBoxImpl;
import pacman.model.entity.dynamic.physics.Vector2D;

public abstract class EntityFactory {
    protected Image image;
    protected Renderable.Layer layer;

    public EntityFactory(Image image) {
        this.image = image;
    }



    protected BoundingBoxImpl getBoundingBoxOfObject(int x, int y) {
        double imageWidth = image.getWidth();
        double imageHeight = image.getHeight();
        Vector2D topLeftCorner = new Vector2D(x , y);
        return new BoundingBoxImpl(topLeftCorner, imageHeight, imageWidth);
    }


    public abstract Renderable createRenderableObject(int x, int y);
}
