package src.cappuccinoextraplus.engine.game;

import src.cappuccinoextraplus.engine.Renderer;

public class Enemy {
    private float x, y;
    private boolean alive = true;

    public Enemy(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public void update(float dt) {
        // not used 
    }

    public void render(Renderer r, int cameraX) {
        if (alive) {
            r.drawFillRect((int) x - cameraX, (int) y, 16, 16, 0xffff00ff);
        }
    }

    public boolean checkCollision(Bullet bullet) {
        return bullet.getX() > x && bullet.getX() < x + 16 &&
                bullet.getY() > y && bullet.getY() < y + 16;
    }

    public void destroy() {
        alive = false;
    }

    public boolean isAlive() {
        return alive;
    }

    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }
}
