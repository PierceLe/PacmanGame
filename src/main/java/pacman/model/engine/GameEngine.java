package pacman.model.engine;

import pacman.model.entity.Renderable;
import pacman.model.entity.dynamic.player.Pacman;
import pacman.model.level.Level;
import pacman.view.observer.gameStateObserver.GameStateObserver;
import pacman.view.observer.gameStateObserver.GameStateSubject;

import java.util.List;


/**
 * The base interface for interacting with the Pac-Man model
 */
public interface GameEngine extends GameStateSubject {

  /**
   * Gets the list of renderables in the game
   *
   * @return The list of renderables
   */
  List<Renderable> getRenderables();

  /**
   * Starts the game
   */
  void startGame();


  /**
   * Move the player up
   */
  void moveUp();

  /**
   * Move the player down
   */
  void moveDown();

  /**
   * Move the player left
   */
  void moveLeft();

  /**
   * Move the player right
   */
  void moveRight();

  /**
   * Instruct the model to progress forward in time by one increment.
   */
  void tick();

  Renderable getPacman();

  Object getLevelImpl();

  void updateState(GameState state);

  GameState getGameState();

}
