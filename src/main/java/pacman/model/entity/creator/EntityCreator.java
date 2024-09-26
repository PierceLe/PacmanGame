package pacman.model.entity.creator;

import javafx.scene.image.Image;
import pacman.model.entity.Renderable;
import pacman.model.entity.dynamic.physics.BoundingBoxImpl;
import pacman.model.entity.dynamic.physics.Vector2D;

public abstract class EntityCreator {
    protected Image image;

    public EntityCreator(Image image) {
        this.image = image;
    }



    protected BoundingBoxImpl getBoundingBoxOfObject(int x, int y) {
        double imageWidth = image.getWidth();
        double imageHeight = image.getHeight();
        Vector2D topLeftCorner = new Vector2D(x , y);
        return new BoundingBoxImpl(topLeftCorner, imageHeight, imageWidth);
    }


    public abstract Renderable createRenderableObject(int x, int y, char rendererSymbol);
}
