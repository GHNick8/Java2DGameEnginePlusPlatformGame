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
    }

    @Override
    public void render(GamePanel gp, Renderer r) {
        r.setAmbientColor(-1);

        player.render(r, cameraX);

        for (Platform platform : platforms) {
            platform.render(r, cameraX);
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