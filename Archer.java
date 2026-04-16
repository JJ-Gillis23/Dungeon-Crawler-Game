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

    // Draws the archer
    @Override
    public void drawMe(GraphicsContext gc) {
        super.drawMe(gc); // draw base player
        gc.setFont(new Font(12));
        gc.setFill(Color.GREEN);
        gc.fillText("Arrows: " + arrowCount, getX(), getY() - 5);
    }

    // Arrow getters/setters
    public int getArrowCount() { return arrowCount; }
    public void setArrowCount(int arrowCount) { this.arrowCount = arrowCount; }
    public void resetArrows() { this.arrowCount = 10; }
}
