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
    public abstract void drawMe(GraphicsContext gc)
    {
        gc.setFill(Color.BLUE);
        gc.fillRect(x, y, size, size);
    }

    // Allows the class to do their action
    public abstract void doThing(GraphicsContext gc);

    // Getters and setters
    public abstract int getSize() { return size; }
    public abstract int getX() { return x; }
    public abstract int getY() { return y; }
    public abstract void setX(int x) { this.x = x; }
    public abstract void setY(int y) { this.y = y; }
    public abstract void setSize(int size) { this.size = size; }
}