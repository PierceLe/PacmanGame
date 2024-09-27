package pacman.model.level;

import org.json.simple.JSONObject;
import pacman.ConfigurationParseException;
import pacman.model.engine.GameEngineImpl;
import pacman.model.engine.GameState;
import pacman.model.entity.Renderable;
import pacman.model.entity.dynamic.DynamicEntity;
import pacman.model.entity.dynamic.ghost.Ghost;
import pacman.model.entity.dynamic.ghost.GhostMode;
import pacman.model.entity.dynamic.physics.PhysicsEngine;
import pacman.model.entity.dynamic.player.Controllable;
import pacman.model.entity.dynamic.player.Pacman;
import pacman.model.entity.staticentity.StaticEntity;
import pacman.model.entity.staticentity.collectable.Collectable;
import pacman.model.maze.Maze;
import pacman.view.observer.livesObserver.LivesObserver;
import pacman.view.observer.livesObserver.LivesSubject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Concrete implement of Pac-Man level
 */
public class LevelImpl implements Level, LivesSubject {

  private static final int START_LEVEL_TIME = 200;
  private final Maze maze;
  private List<Renderable> renderables;
  private Controllable player;
  private List<Ghost> ghosts;
  private int tickCount;
  private Map<GhostMode, Integer> modeLengths;
  private int numLives;
  private List<Renderable> collectables;
  private GhostMode currentGhostMode;
  private final List<LivesObserver> livesObserveas;
  private GameEngineImpl gameEngine;

  public LevelImpl(JSONObject levelConfiguration,
                   Maze maze, GameEngineImpl gameEngine) {
    this.renderables = new ArrayList<>();
    this.gameEngine = gameEngine;
    this.maze = maze;
    this.tickCount = 0;
    this.modeLengths = new HashMap<>();
    this.currentGhostMode = GhostMode.SCATTER;
    livesObserveas = new ArrayList<>();
    initLevel(new LevelConfigurationReader(levelConfiguration));
  }

  private void initLevel(LevelConfigurationReader levelConfigurationReader) {
    // Fetch all renderables for the level
    this.renderables = maze.getRenderables();
    // Set up player
    if (!(maze.getControllable() instanceof Controllable)) {
      throw new ConfigurationParseException("Player entity is not controllable");
    }
    this.player = (Controllable) maze.getControllable();
    this.player.setSpeed(levelConfigurationReader.getPlayerSpeed());
    setNumLives(maze.getNumLives());

    // Set up ghosts
    this.ghosts = maze.getGhosts().stream()
        .map(element -> (Ghost) element)
        .collect(Collectors.toList());
    Map<GhostMode, Double> ghostSpeeds = levelConfigurationReader.getGhostSpeeds();

    for (Ghost ghost : this.ghosts) {
      ghost.setSpeeds(ghostSpeeds);
      ghost.setGhostMode(this.currentGhostMode);
    }
    this.modeLengths = levelConfigurationReader.getGhostModeLengths();

    // Set up collectables
    this.collectables = new ArrayList<>(maze.getPellets());

  }

  @Override
  public List<Renderable> getRenderables() {
    return this.renderables;
  }

  private List<DynamicEntity> getDynamicEntities() {
    return renderables.stream().filter(e -> e instanceof DynamicEntity).map(e -> (DynamicEntity) e).collect(
        Collectors.toList());
  }

  private List<StaticEntity> getStaticEntities() {
    return renderables.stream().filter(e -> e instanceof StaticEntity).map(e -> (StaticEntity) e).collect(
        Collectors.toList());
  }

  @Override
  public void tick() {
      if (tickCount == modeLengths.get(currentGhostMode)) {

          // update ghost mode
          this.currentGhostMode = GhostMode.getNextGhostMode(currentGhostMode);
          for (Ghost ghost : this.ghosts) {
              ghost.setGhostMode(this.currentGhostMode);
          }

          tickCount = 0;
      }


    if (tickCount % Pacman.PACMAN_IMAGE_SWAP_TICK_COUNT == 0) {
      this.player.switchImage();
    }

    // Update the dynamic entities
    List<DynamicEntity> dynamicEntities = getDynamicEntities();
    for (DynamicEntity dynamicEntity : dynamicEntities) {
      maze.updatePossibleDirections(dynamicEntity);
      dynamicEntity.update();
    }
    for (Ghost ghost : this.ghosts) {
      ghost.setPlayerPosition(player.getPosition());
    }

    for (int i = 0; i < dynamicEntities.size(); ++i) {
      DynamicEntity dynamicEntityA = dynamicEntities.get(i);

      // handle collisions between dynamic entities
      for (int j = i + 1; j < dynamicEntities.size(); ++j) {
        DynamicEntity dynamicEntityB = dynamicEntities.get(j);

        if (dynamicEntityA.collidesWith(dynamicEntityB) ||
            dynamicEntityB.collidesWith(dynamicEntityA)) {
          dynamicEntityA.collideWith(this, dynamicEntityB);
          dynamicEntityB.collideWith(this, dynamicEntityA);
        }
      }

      // handle collisions between dynamic entities and static entities
      for (StaticEntity staticEntity : getStaticEntities()) {
        if (dynamicEntityA.collidesWith(staticEntity)) {
          dynamicEntityA.collideWith(this, staticEntity);
          PhysicsEngine.resolveCollision(dynamicEntityA, staticEntity);
        }
      }
    }

    tickCount++;
  }

  @Override
  public boolean isPlayer(Renderable renderable) {
    return renderable == this.player;
  }

  @Override
  public boolean isCollectable(Renderable renderable) {
    return maze.getPellets().contains(renderable) && ((Collectable) renderable).isCollectable();
  }

  @Override
  public void moveLeft() {
    player.left();
  }

  @Override
  public void moveRight() {
    player.right();
  }

  @Override
  public void moveUp() {
    player.up();
  }

  @Override
  public void moveDown() {
    player.down();
  }

  @Override
  public boolean isLevelFinished() {
    for (Renderable collectable : collectables) {
      if (collectable instanceof Collectable && ((Collectable) collectable).isCollectable()) {
        return false;
      }
    }
    return true;
  }

  @Override
  public int getNumLives() {
    return this.numLives;
  }

  public void setNumLives(int numLives) {
    this.numLives = numLives;
    notifyObservers();
  }

  @Override
  public void handleLoseLife() {
    setNumLives(getNumLives() - 1);
    if (getNumLives() > 0) {
      maze.reset();
      gameEngine.updateState(GameState.READY);
    } else {
      handleGameEnd();
      gameEngine.updateState(GameState.GAME_OVER);
    }
    notifyObservers();
  }

  @Override
  public void handleGameEnd() {
    // clear dynamic entities
    renderables.removeAll(getDynamicEntities());
    // clear all pellets
    renderables.removeAll(maze.getPellets());
  }

  @Override
  public void collect(Collectable collectable) {
      collectable.collect();
      if (isLevelFinished() && gameEngine.getCurrentLevelNo() == gameEngine.getNumLevels() - 1) {
        handleGameEnd();
        gameEngine.updateState(GameState.WIN);
      }
      else if (isLevelFinished()) {
        gameEngine.nextLevel();
      }

  }
  @Override
  public void registerObserver(LivesObserver livesObserver) {
    if (!livesObserveas.contains(livesObserver)) {
      livesObserveas.add(livesObserver);
    }
  }

  @Override
  public void removeObserver(LivesObserver livesObserver) {
    livesObserveas.remove(livesObserver);
  }

  @Override
  public void notifyObservers() {
    for (LivesObserver livesObserver : livesObserveas) {
      livesObserver.updateLives(getNumLives());
    }
  }
}
