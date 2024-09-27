package pacman.entityfactory.dynamicentity;

import javafx.scene.image.Image;
import pacman.model.entity.Renderable;
import pacman.model.entity.dynamic.ghost.GhostImpl;
import pacman.model.entity.dynamic.ghost.GhostMode;
import pacman.model.entity.dynamic.physics.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class GhostsFactory extends DynamicFactory {
    private Map<Integer, Vector2D> corners;
    private final Random RANDOM;


    public GhostsFactory(Image image) {
        super(image);
        corners = new HashMap<>();
        initCorners();
        RANDOM = new Random();
    }


    private void initCorners() {
        corners.put(0, new Vector2D(0, 0));
        corners.put(1, new Vector2D(26 * 16, 0));
        corners.put(2, new Vector2D(0, 32 * 16));
        corners.put(3, new Vector2D(26 * 16, 32 * 16));
    }


    @Override
    public Renderable createRenderableObject(int x, int y) {
        int randomNum = RANDOM.nextInt(4);
        Vector2D targetCorner = corners.get(randomNum);
        BoundingBoxImpl boundingBox = getBoundingBoxOfObject(x, y);
        KinematicStateImpl kinematicState = getKinematicState(boundingBox);
        Direction direction = Direction.values()[RANDOM.nextInt(4)];
        return new GhostImpl(super.image, boundingBox, kinematicState, GhostMode.SCATTER, targetCorner, direction);
    }

}
