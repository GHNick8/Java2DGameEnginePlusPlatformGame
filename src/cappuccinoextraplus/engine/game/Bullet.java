package src.cappuccinoextraplus.engine.game;

import src.cappuccinoextraplus.engine.Renderer;

public class Bullet {
    private float x, y;
    private float velocityX, velocityY;
    private float speed = 300;

    public Bullet(float x, float y, float velocityX, float velocityY) {
        this.x = x;
        this.y = y;
        this.velocityX = velocityX * speed;
        this.velocityY = velocityY * speed;
    }

    public void update(float dt) {
        x += velocityX * dt;
        y += velocityY * dt;
    }

    public void render(Renderer r, int cameraX) {
        r.drawFillRect((int) x - cameraX, (int) y, 8, 8, 0xffff0000);
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public float getVelocityX() {
        return velocityX;
    }

    public void setVelocityX(float velocityX) {
        this.velocityX = velocityX;
    }

    public float getVelocityY() {
        return velocityY;
    }

    public void setVelocityY(float velocityY) {
        this.velocityY = velocityY;
    }

    public float getSpeed() {
        return speed;
    }

    public void setSpeed(float speed) {
        this.speed = speed;
    }
}