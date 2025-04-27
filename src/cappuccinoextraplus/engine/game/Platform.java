package src.cappuccinoextraplus.engine.game;

import src.cappuccinoextraplus.engine.Renderer;

public class Platform {
    private final int x, y;

    public Platform(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void render(Renderer r, int cameraX) {
        r.drawFillRect(x - cameraX, y, 32, 10, 0xff00ff00);
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
}
