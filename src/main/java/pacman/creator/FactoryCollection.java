package pacman.creator;

import javafx.scene.image.Image;
import pacman.model.maze.RenderableType;

import java.util.HashMap;
import java.util.Map;

public class FactoryCollection {
    private Map<Character, EntityCreator> factories;
    private HashMap<Character, Image> images;

    public FactoryCollection() {
        images = new HashMap<>();
        initImages();
        factories = new HashMap<>();
        initEntities();
    }


    private void initImages() {
        images.put(RenderableType.HORIZONTAL_WALL, new Image(getClass().getResource("/maze/walls/horizontal.png").toExternalForm()));
        images.put(RenderableType.VERTICAL_WALL, new Image(getClass().getResource("/maze/walls/vertical.png").toExternalForm()));
        images.put(RenderableType.UP_LEFT_WALL, new Image(getClass().getResource("/maze/walls/upLeft.png").toExternalForm()));
        images.put(RenderableType.UP_RIGHT_WALL, new Image(getClass().getResource("/maze/walls/upRight.png").toExternalForm()));
        images.put(RenderableType.DOWN_LEFT_WALL, new Image(getClass().getResource("/maze/walls/downLeft.png").toExternalForm()));
        images.put(RenderableType.DOWN_RIGHT_WALL, new Image(getClass().getResource("/maze/walls/downRight.png").toExternalForm()));
        images.put(RenderableType.PELLET, new Image(getClass().getResource("/maze/pellet.png").toExternalForm()));
        images.put(RenderableType.GHOST, new Image(getClass().getResource("/maze/ghosts/ghost.png").toExternalForm()));
        images.put(RenderableType.PACMAN, new Image(getClass().getResource("/maze/pacman/playerDown.png").toExternalForm()));
    }

    private void initEntities() {
        registerEntityCreator('1', new StaticEntityCreator(images.get(RenderableType.HORIZONTAL_WALL)));
        registerEntityCreator('2', new StaticEntityCreator(images.get(RenderableType.VERTICAL_WALL)));
        registerEntityCreator('3', new StaticEntityCreator(images.get(RenderableType.UP_LEFT_WALL)));
        registerEntityCreator('4', new StaticEntityCreator(images.get(RenderableType.UP_RIGHT_WALL)));
        registerEntityCreator('5', new StaticEntityCreator(images.get(RenderableType.DOWN_LEFT_WALL)));
        registerEntityCreator('6', new StaticEntityCreator(images.get(RenderableType.DOWN_RIGHT_WALL)));
        registerEntityCreator('7', new StaticEntityCreator(images.get(RenderableType.PELLET)));
        registerEntityCreator('g', new DynamicCreator(images.get(RenderableType.GHOST)));
        registerEntityCreator('p', new DynamicCreator(images.get(RenderableType.PACMAN)));
    }

    public EntityCreator getEntityCreator(char renderableType) {
        return factories.get(renderableType);
    }

    private void registerEntityCreator(char renderableType, EntityCreator creator) {
        factories.put(renderableType, creator);
    }





}
