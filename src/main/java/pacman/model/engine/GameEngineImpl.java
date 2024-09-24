package pacman.model.engine;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import pacman.model.entity.Renderable;
import pacman.model.level.Level;
import pacman.model.level.LevelImpl;
import pacman.model.maze.Maze;
import pacman.model.maze.MazeCreator;
import pacman.view.observer.gameStateObserver.GameStateObserver;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Implementation of GameEngine - responsible for coordinating the Pac-Man model
 */
public class GameEngineImpl implements GameEngine {
    private GameState currentGameState;
    private Level currentLevel;
    private int numLevels;
    private int currentLevelNo;
    private Maze maze;
    private JSONArray levelConfigs;
    private List<GameStateObserver> observers;

    public GameEngineImpl(String configPath) {
        this.currentLevelNo = 0;
        currentGameState = GameState.READY;
        observers = new ArrayList<>();
        init(new GameConfigurationReader(configPath));
    }

    private void init(GameConfigurationReader gameConfigurationReader) {
        // Set up map
        String mapFile = gameConfigurationReader.getMapFile();
        MazeCreator mazeCreator = new MazeCreator(mapFile);
        this.maze = mazeCreator.createMaze();
        this.maze.setNumLives(gameConfigurationReader.getNumLives());

        // Get level configurations
        this.levelConfigs = gameConfigurationReader.getLevelConfigs();
        System.out.println(levelConfigs.toJSONString());
        this.numLevels = levelConfigs.size();
        if (levelConfigs.isEmpty()) {
            System.exit(0);
        }
    }

    @Override
    public List<Renderable> getRenderables() {
        return this.currentLevel.getRenderables();
    }

    @Override
    public void moveUp() {
        currentLevel.moveUp();
    }

    @Override
    public void moveDown() {
        currentLevel.moveDown();
    }

    @Override
    public void moveLeft() {
        currentLevel.moveLeft();
    }

    @Override
    public void moveRight() {
        currentLevel.moveRight();
    }

    @Override
    public void startGame() {
        startLevel();
    }

    private void startLevel() {
        updateState(GameState.READY);
        JSONObject levelConfig = (JSONObject) levelConfigs.get(currentLevelNo);
        // reset renderables to starting state
        maze.reset();
        this.currentLevel = new LevelImpl(levelConfig, maze, this);

    }

    @Override
    public void tick() {
        currentLevel.tick();
    }

    @Override
    public Renderable getPacman() {
        return this.maze.getControllable();
    }

    @Override
    public LevelImpl getLevelImpl() {
        return (LevelImpl) this.currentLevel;
    }


    @Override
    public void registerObserver(GameStateObserver observer) {
        observers.add(observer);
    }

    @Override
    public void removeObserver(GameStateObserver observer) {
        observers.remove(observer);
    }

    @Override
    public void notifyObservers() {
        for (GameStateObserver observer : observers) {
            observer.updateState();
        }
    }

    public void updateState(GameState state) {
        this.currentGameState = state;
        notifyObservers();
    }

    public GameState getGameState() {
        return currentGameState;
    }

    public int getNumLevels() {
        return numLevels;
    }

    public int getCurrentLevelNo() {
        return currentLevelNo;
    }

    public void nextLevel() {
        currentLevelNo++;
        System.out.println(currentLevelNo);
        startGame();
    }


}

