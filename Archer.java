import javafx.scene.paint.*;
import javafx.scene.canvas.*;
import javafx.scene.text.Font;

public class Archer extends Player {
    private int arrowCount;

    // Singleton instance
    private static Archer instance = null;

    // Private constructor
    private Archer() {
        super(); // calls Player constructor
        arrowCount = 10;
    }

    // Access singleton
    public static Archer getInstance() {
        if (instance == null) {
            instance = new Archer();
        }
        return instance;
    }
    public int getX() { return super.getX(); }
    public int getY() { return super.getY(); }
    public int setX(int x) { super.setX(x); }
    public int setY(int y) { super.setY(y); }

    // Draws the archer
    @Override
    public void drawMe(GraphicsContext gc) {
        super.drawMe(gc); // draw base player
        gc.setFont(new Font(12));
        gc.setFill(Color.GREEN);
        gc.fillText("Arrows: " + arrowCount, getX(), getY() - 5);
    }

    // Archery action
    @Override
    public void doThing(GraphicsContext gc) {
        gc.setFill(Color.YELLOW);
        gc.fillOval(getX() + getSize(), getY() + getSize() / 2 - 5, 10, 10);
        for(int i = 0; i < 10; i++) {
          gc.fillOval(getX() + getSize() + 10 + i*10, getY() + getSize() / 2 - 5, 10, 10);  
        }
        if (arrowCount > 0) {
            arrowCount--;
        }

    }

    // Arrow getters/setters
    public int getArrowCount() { return arrowCount; }
    public void setArrowCount(int arrowCount) { this.arrowCount = arrowCount; }
    public void resetArrows() { this.arrowCount = 10; }
}
