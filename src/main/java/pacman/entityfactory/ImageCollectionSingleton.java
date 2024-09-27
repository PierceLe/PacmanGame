package pacman.entityfactory;

import javafx.scene.image.Image;
import pacman.model.entity.dynamic.player.PacmanVisual;
import pacman.model.maze.RenderableType;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class ImageCollectionSingleton {

    private static ImageCollectionSingleton instance;

    // Paths to images
    private final String ghostPath = "/maze/ghosts/ghost.png";
    private final String playerClosedPath = "/maze/pacman/playerClosed.png";
    private final String playerDownPath = "/maze/pacman/playerDown.png";
    private final String playerLeftPath = "/maze/pacman/playerLeft.png";
    private final String playerRightPath = "/maze/pacman/playerRight.png";
    private final String playerUpPath = "/maze/pacman/playerUp.png";
    private final String wallsDownLeftPath = "/maze/walls/downLeft.png";
    private final String wallsDownRightPath = "/maze/walls/downRight.png";
    private final String wallsUpLeftPath = "/maze/walls/upLeft.png";
    private final String wallsUpRightPath = "/maze/walls/upRight.png";
    private final String wallsHorizontalPath = "/maze/walls/horizontal.png";
    private final String wallsVerticalPath = "/maze/walls/vertical.png";
    private final String pelletsPath = "/maze/pellet.png";

    // Maps to store images
    private final Map<Character, Image> renderableImages;
    private final Map<PacmanVisual, Image> pacmanVisualImages;

    // Private constructor to implement Singleton pattern
    private ImageCollectionSingleton() {
        renderableImages = new HashMap<>();
        pacmanVisualImages = new HashMap<>();
        initImages();
    }

    // Public method to get the singleton instance
    public static synchronized ImageCollectionSingleton getInstance() {
        if (instance == null) {
            instance = new ImageCollectionSingleton();
        }
        return instance;
    }

    // Initialize images
    private void initImages() {
        loadRenderableImages();
        loadPacmanVisualImages();
    }

    // Load images for renderable types
    private void loadRenderableImages() {
        renderableImages.put(RenderableType.HORIZONTAL_WALL, loadImage(wallsHorizontalPath));
        renderableImages.put(RenderableType.VERTICAL_WALL, loadImage(wallsVerticalPath));
        renderableImages.put(RenderableType.UP_LEFT_WALL, loadImage(wallsUpLeftPath));
        renderableImages.put(RenderableType.UP_RIGHT_WALL, loadImage(wallsUpRightPath));
        renderableImages.put(RenderableType.DOWN_LEFT_WALL, loadImage(wallsDownLeftPath));
        renderableImages.put(RenderableType.DOWN_RIGHT_WALL, loadImage(wallsDownRightPath));
        renderableImages.put(RenderableType.PELLET, loadImage(pelletsPath));
        renderableImages.put(RenderableType.GHOST, loadImage(ghostPath));
        renderableImages.put(RenderableType.PACMAN, loadImage(playerClosedPath));
    }

    // Load images for Pacman visuals
    private void loadPacmanVisualImages() {
        pacmanVisualImages.put(PacmanVisual.UP, loadImage(playerUpPath));
        pacmanVisualImages.put(PacmanVisual.CLOSED, loadImage(playerClosedPath));
        pacmanVisualImages.put(PacmanVisual.DOWN, loadImage(playerDownPath));
        pacmanVisualImages.put(PacmanVisual.LEFT, loadImage(playerLeftPath));
        pacmanVisualImages.put(PacmanVisual.RIGHT, loadImage(playerRightPath));
    }

    // Utility method to load an image from a path
    private Image loadImage(String path) {
        return new Image(Objects.requireNonNull(getClass().getResource(path)).toExternalForm());
    }

    // Getter for renderable images
    public Image getRenderableImage(char renderableType) {
        return renderableImages.get(renderableType);
    }

    // Getter for Pacman visuals
    public  Map<PacmanVisual, Image> getPacmanVisualImages() {
        return pacmanVisualImages;
    }
}