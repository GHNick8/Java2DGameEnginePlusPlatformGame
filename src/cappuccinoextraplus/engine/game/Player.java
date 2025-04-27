package src.cappuccinoextraplus.engine.game;

import java.awt.event.KeyEvent;
import java.util.ArrayList;
import src.cappuccinoextraplus.engine.GamePanel;
import src.cappuccinoextraplus.engine.Input;
import src.cappuccinoextraplus.engine.Renderer;

public class Player {
    private float x, y;
    private float velocityY = 0;
    private boolean onGround = false;

    private boolean jumping = false;
    private float jumpTime = 0;
    private final float maxJumpTime = 0.25f;

    private final ArrayList<Bullet> bullets = new ArrayList<>();

    private float shootCooldown = 0;
    private final float maxShootCooldown = 0.2f; 

    public Player(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public void update(GamePanel gp, float dt) {
        Input input = gp.getInput();

        if (input.isKey(KeyEvent.VK_Q)) x -= 100 * dt;
        if (input.isKey(KeyEvent.VK_D)) x += 100 * dt;

        if (input.isKey(KeyEvent.VK_Z) && onGround) {
            velocityY = -250;
            jumping = true;
            jumpTime = 0;
            onGround = false;
        }

        if (jumping) {
            jumpTime += dt;
            if (input.isKey(KeyEvent.VK_Z) && jumpTime < maxJumpTime) {
                velocityY = -250; 
            } else {
                jumping = false; 
            }
        }

        velocityY += 500 * dt;
        y += velocityY * dt;

        if (y > gp.getHeight()) {
            y = 100;
            velocityY = 0;
        }

        // Shooting logic 
        if (shootCooldown <= 0) {
            if (input.isKey(KeyEvent.VK_UP) && input.isKey(KeyEvent.VK_LEFT)) {
                bullets.add(new Bullet(x + 8, y + 8, -0.707f, -0.707f)); 
                shootCooldown = maxShootCooldown;
            }
            else if (input.isKey(KeyEvent.VK_UP) && input.isKey(KeyEvent.VK_RIGHT)) {
                bullets.add(new Bullet(x + 8, y + 8, 0.707f, -0.707f)); 
                shootCooldown = maxShootCooldown;
            }
            else if (input.isKey(KeyEvent.VK_DOWN) && input.isKey(KeyEvent.VK_LEFT)) {
                bullets.add(new Bullet(x + 8, y + 8, -0.707f, 0.707f)); 
                shootCooldown = maxShootCooldown;
            }
            else if (input.isKey(KeyEvent.VK_DOWN) && input.isKey(KeyEvent.VK_RIGHT)) {
                bullets.add(new Bullet(x + 8, y + 8, 0.707f, 0.707f)); 
                shootCooldown = maxShootCooldown;
            }
            else if (input.isKey(KeyEvent.VK_LEFT)) {
                bullets.add(new Bullet(x + 8, y + 8, -1, 0)); 
                shootCooldown = maxShootCooldown;
            }
            else if (input.isKey(KeyEvent.VK_RIGHT)) {
                bullets.add(new Bullet(x + 8, y + 8, 1, 0)); 
                shootCooldown = maxShootCooldown;
            }
            else if (input.isKey(KeyEvent.VK_UP)) {
                bullets.add(new Bullet(x + 8, y + 8, 0, -1)); 
                shootCooldown = maxShootCooldown;
            }
            else if (input.isKey(KeyEvent.VK_DOWN)) {
                bullets.add(new Bullet(x + 8, y + 8, 0, 1)); 
                shootCooldown = maxShootCooldown;
            }
        }        

        // Update all bullets
        for (int i = 0; i < bullets.size(); i++) {
            
            Bullet b = bullets.get(i);
            b.update(dt);
        
            if (b.getX() < -100 || b.getX() > 3000 || b.getY() < -100 || b.getY() > 500) {
                bullets.remove(i);
                i--;
            }
        }        

        // Update cooldown 
        if (shootCooldown > 0) {
            shootCooldown -= dt;
        }
    }

    public void render(Renderer r, int cameraX) {
        r.drawFillRect((int) x - cameraX, (int) y, 16, 16, 0xffffff00);

        // Draw bullets
        for (Bullet b : bullets) {
            b.render(r, cameraX);
        }
    }

    public boolean checkCollision(Platform platform) {
        return x + 16 > platform.getX() && x < platform.getX() + 32 &&
               y + 16 > platform.getY() && y + 16 < platform.getY() + 10;
    }

    public void landOnPlatform(Platform platform) {
        y = platform.getY() - 16;
        velocityY = 0;
        onGround = true;
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

    public ArrayList<Bullet> getBullets() {
        return bullets;
    }    
}
