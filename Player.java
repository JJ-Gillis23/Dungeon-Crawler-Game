import javafx.scene.paint.*;
import javafx.scene.canvas.*;

public class Player {
    private int x;
    private int y;
    private int size;

    // Single instance
    private static Player instance = null;

    // Private constructor
    private Player() {
        x = 0;
        y = 0;
        size = 25; // default size
    }

    // Public method to access the singleton
    public static Player getInstance() {
        if (instance == null) {
            instance = new Player();
        }
        return instance;
    }

    // Draws the player
    public void drawMe(GraphicsContext gc) {
        gc.setFill(Color.RED);
        gc.fillRect(x, y, size, size);
    }

    // Getters and setters
    public int getSize() { return size; }
    public int getX() { return x; }
    public int getY() { return y; }
    public void setX(int x) { this.x = x; }
    public void setY(int y) { this.y = y; }
    public void setSize(int size) { this.size = size; }
}