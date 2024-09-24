package pacman.model.entity.dynamic.player;

import pacman.model.entity.dynamic.DynamicEntity;
import pacman.model.entity.dynamic.physics.Direction;

/**
 * Represents a Controllable character in the Pac-Man game
 */
public interface Controllable extends DynamicEntity {

    /**
     * Directs player to move up
     */
    void up();

    /**
     * Directs player to move down
     */
    void down();

    /**
     * Directs player to move left
     */
    void left();

    /**
     * Directs player to move right
     */
    void right();

    /**
     * Sets speed of player
     */
    void setSpeed(double speed);

    /**
     * Switch image of player to closed/open player image
     */
    void switchImage();

    boolean isValidMove(Direction direction);

    void setLastDirection(Direction direction);

    Direction getLastDirection();

    void updateScore(int score);
}
