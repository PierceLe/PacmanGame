package pacman.entityfactory;


import pacman.entityfactory.dynamicentity.GhostsFactory;
import pacman.entityfactory.dynamicentity.PlayerFactory;
import pacman.entityfactory.staticentity.EmptyCellFactory;
import pacman.entityfactory.staticentity.PelletsFactory;
import pacman.entityfactory.staticentity.WallsFactory;
import java.util.HashMap;
import java.util.Map;

public class FactoryCollectionSingleton {
    // Static instance for Singleton
    private static volatile FactoryCollectionSingleton instance;
    private final Map<Character, EntityFactory> factories;

    // Private constructor to prevent instantiation
    private FactoryCollectionSingleton() {
        factories = new HashMap<>();
        initEntities();
    }

    // Public static method to get the single instance
    public static FactoryCollectionSingleton getInstance() {
        if (instance == null) {
            synchronized (FactoryCollectionSingleton.class) {
                if (instance == null) {
                    instance = new FactoryCollectionSingleton();
                }
            }
        }
        return instance;
    }


    private void initEntities() {
        ImageCollectionSingleton imageCollectionSingleton = ImageCollectionSingleton.getInstance();
        registerEntityCreator('0', new EmptyCellFactory(imageCollectionSingleton.getRenderableImage('0')));
        registerEntityCreator('1', new WallsFactory(imageCollectionSingleton.getRenderableImage('1')));
        registerEntityCreator('2', new WallsFactory(imageCollectionSingleton.getRenderableImage('2')));
        registerEntityCreator('3', new WallsFactory(imageCollectionSingleton.getRenderableImage('3')));
        registerEntityCreator('4', new WallsFactory(imageCollectionSingleton.getRenderableImage('4')));
        registerEntityCreator('5', new WallsFactory(imageCollectionSingleton.getRenderableImage('5')));
        registerEntityCreator('6', new WallsFactory(imageCollectionSingleton.getRenderableImage('6')));
        registerEntityCreator('7', new PelletsFactory(imageCollectionSingleton.getRenderableImage('7')));
        registerEntityCreator('g', new GhostsFactory(imageCollectionSingleton.getRenderableImage('g')));
        registerEntityCreator('p', new PlayerFactory(imageCollectionSingleton.getRenderableImage('p')));
    }


    private void registerEntityCreator(char renderableType, EntityFactory creator) {
        factories.put(renderableType, creator);
    }


    public EntityFactory getEntityCreator(char renderableType) {
        return factories.get(renderableType);
    }
}