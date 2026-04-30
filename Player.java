import javafx.scene.paint.*;
import javafx.scene.canvas.*;
import javafx.scene.text.Font;
public abstract class Player {
    protected int x, y, size, health; // protected so subclasses can access
    protected String name; // player's name

    public Player() {
        x = 0; y = 0; size = 35; health = 100; name = "Player";
    }

    public abstract void drawMe(int x, int y, GraphicsContext gc);
    public abstract void doThing(GraphicsContext gc);
    public abstract void upgradeddoThing(GraphicsContext gc);
    public abstract void setShouldShoot(boolean b);
    public abstract boolean checkCollisions(Player player);

    // Concrete getters/setters — no need to be abstract
    public int getX() { return x; }
    public int getY() { return y; }
    public int getSize() { return size; }
    public void setX(int x) { this.x = x; }
    public void setY(int y) { this.y = y; }
    public void setSize(int size) { this.size = size; }
    public int getHealth() { return health; }
    public void setHealth(int health) { this.health = health; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
}