package pacman.entityfactory.dynamicentity;

import javafx.scene.image.Image;
import pacman.entityfactory.FactoryCollectionSingleton;
import pacman.entityfactory.ImageCollectionSingleton;
import pacman.model.entity.Renderable;
import pacman.model.entity.dynamic.physics.BoundingBoxImpl;
import pacman.model.entity.dynamic.physics.KinematicStateImpl;
import pacman.model.entity.dynamic.player.Pacman;
import pacman.model.entity.dynamic.player.PacmanVisual;

import java.util.HashMap;
import java.util.Map;

public class PlayerFactory extends DynamicFactory{
    Map<PacmanVisual, Image> pacmanVisuals;

    public PlayerFactory(Image image) {
        super(image);
        pacmanVisuals = ImageCollectionSingleton.getInstance().getPacmanVisualImages();
    }


    @Override
    public Renderable createRenderableObject(int x, int y) {
        BoundingBoxImpl boundingBox = getBoundingBoxOfObject(x, y);
        KinematicStateImpl kinematicState = getKinematicState(boundingBox);
        return new Pacman(pacmanVisuals.get(PacmanVisual.CLOSED), pacmanVisuals, boundingBox, kinematicState);
    }


}
