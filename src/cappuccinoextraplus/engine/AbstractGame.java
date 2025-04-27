package src.cappuccinoextraplus.engine;

public abstract class AbstractGame {
    public abstract void update(GamePanel gp, float dt);
    public abstract void render(GamePanel gp, Renderer r);
}
