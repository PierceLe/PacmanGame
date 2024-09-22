package pacman.creator;

import pacman.model.entity.Renderable;

public interface Creator {
    public Renderable createRenderableObject(char renderableType, int x, int y);
}
