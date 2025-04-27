package src.cappuccinoextraplus.engine.game;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import src.cappuccinoextraplus.engine.AbstractGame;
import src.cappuccinoextraplus.engine.GamePanel;
import src.cappuccinoextraplus.engine.Renderer;

public class GameManager extends AbstractGame {

    private final Player player;
    private final ArrayList<Platform> platforms;

    private final ArrayList<Enemy> enemies = new ArrayList<>();

    private int cameraX;

    public GameManager() {
        player = new Player(100, 100);
        platforms = new ArrayList<>();

        ArrayList<String> levelData = loadLevel("res/level1.txt");

        for (int y = 0; y < levelData.size(); y++) {
            String line = levelData.get(y);
            for (int x = 0; x < line.length(); x++) {
                if (line.charAt(x) == '1') {
                    platforms.add(new Platform(x * 32, y * 32));
                }
            }
        }

        // Spawn enemies
        enemies.add(new Enemy(150, 150));
        enemies.add(new Enemy(200, 150));
        enemies.add(new Enemy(250, 150));
    }

    @Override
    public void update(GamePanel gp, float dt) {
        player.update(gp, dt);

        for (Platform platform : platforms) {
            if (player.checkCollision(platform)) {
                player.landOnPlatform(platform);
            }
        }

        int deadzoneLeft = 100;
        int deadzoneRight = 220;

        if (player.getX() - cameraX < deadzoneLeft) {
            cameraX = (int)(player.getX() - deadzoneLeft);
        }
        if (player.getX() - cameraX > deadzoneRight) {
            cameraX = (int)(player.getX() - deadzoneRight);
        }

        if (cameraX < 0) cameraX = 0;

        // Update enemy class 
        for (Enemy enemy : enemies) {
            enemy.update(dt);
        }        

        // Check bullets against enemies
        for (int i = 0; i < player.getBullets().size(); i++) {
            Bullet bullet = player.getBullets().get(i);
            for (Enemy enemy : enemies) {
                if (enemy.isAlive() && enemy.checkCollision(bullet)) {
                    enemy.destroy();
                    player.getBullets().remove(i);
                    i--; 
                    break;
                }
            }
        }        
    }

    @Override
    public void render(GamePanel gp, Renderer r) {
        r.setAmbientColor(-1);

        player.render(r, cameraX);

        // Render platforms
        for (Platform platform : platforms) {
            if (platform.getX() > cameraX - 64 && platform.getX() < cameraX + 320 + 64) {
                platform.render(r, cameraX);
            }
        }

        // Render enemies
        for (Enemy enemy : enemies) {
            if (enemy.getX() > cameraX - 64 && enemy.getX() < cameraX + 320 + 64) {
                enemy.render(r, cameraX);
            }
        }
    }

    private ArrayList<String> loadLevel(String path) {
        ArrayList<String> lines = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(path))) {
            String line;
            while ((line = reader.readLine()) != null) {
                lines.add(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return lines;
    }

    public static void main(String[] args) {
        GamePanel gp = new GamePanel(new GameManager());
        gp.setWidth(320);
        gp.setHeight(240);
        gp.setScale(2f);
        gp.start();
    }
}