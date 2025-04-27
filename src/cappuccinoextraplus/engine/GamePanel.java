package src.cappuccinoextraplus.engine;

import javax.swing.JPanel;

public class GamePanel extends JPanel implements Runnable {

    private Thread thread;
    private Window window;
    private Renderer renderer;
    private Input input;
    private final AbstractGame game;

    int fps = 60;

    private int width, height;
    private float scale = 1f;

    private String title = "Java 2D Game Engine v1.0";

    public GamePanel(AbstractGame game) {
        this.game = game;
    }

    public void start() {
        window = new Window(this);
        renderer = new Renderer(this);
        input = new Input(this);
        thread = new Thread(this);

        thread.start();
    }

    @Override
    public void run() {

        double drawInterval = 1000000000/fps;
        double delta = 0;
        long lastTime = System.nanoTime();
        long currentTime;
        long timer = 0;
        @SuppressWarnings("unused")
        long drawCount = 0;
        while (thread != null) {
            currentTime = System.nanoTime();
            long elapsedTime = currentTime - lastTime;
            delta += elapsedTime / drawInterval;
            timer += elapsedTime;
            lastTime = currentTime;

            float deltaTime = elapsedTime / 1000000000f;
            game.update(this, deltaTime);
            input.update();

            if (delta >= 1) {
                update();
                repaint();

                renderer.clear();

                game.render(this, renderer);
                renderer.process();
                renderer.drawText("FPS:" + fps, 0, 0, 0xff00ffff);

                window.update();
                delta--;
                drawCount++;
            }
 
            if (timer >= 1000000000) {
                // System.out.println("FPS:" + drawCount); // FPS:60 
                drawCount = 0;
                timer = 0;
            }
        }
    }

    public void update() {

    }

    @Override
    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    @Override
    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public float getScale() {
        return scale;
    }

    public void setScale(float scale) {
        this.scale = scale;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Window getWindow() {
        return window;
    }

    public void setWindow(Window window) {
        this.window = window;
    }

    public Input getInput() {
        return input;
    }

    public void setInput(Input input) {
        this.input = input;
    }

    public Renderer getRenderer() {
        return renderer;
    }

    public void setRenderer(Renderer renderer) {
        this.renderer = renderer;
    }
}