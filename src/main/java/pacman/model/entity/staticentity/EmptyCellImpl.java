package pacman.model.entity.staticentity;

import javafx.scene.image.Image;
import pacman.model.entity.dynamic.physics.BoundingBox;

public class EmptyCellImpl extends StaticEntityImpl  {

    public EmptyCellImpl(BoundingBox boundingBox, Layer layer, Image image) {
        super(boundingBox, layer, image);
    }

    @Override
    public boolean canPassThrough(){
        return true;
    }
}
