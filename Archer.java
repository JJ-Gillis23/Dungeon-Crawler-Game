import javafx.scene.paint.*;
import javafx.scene.canvas.*;
import javafx.scene.text.Font;

public class Archer extends Player {
    private int arrowCount;

    
    // Public constructor
    public Archer() {
        super();
        arrowCount = 10;
    }

   

    // Getters and setters
    @Override
    public int getX() { return x; }

    @Override
    public int getY() { return y; }

    @Override
    public int getSize() { return size; }

    @Override
    public void setX(int x) { this.x = x; }

    @Override
    public void setY(int y) { this.y = y; }

    @Override
    public void setSize(int size) { this.size = size; }

    // Draws the archer
    @Override
    public void drawMe(int x, int y, GraphicsContext gc) {
        gc.setFill(Color.BLUE);
        gc.fillRect(x, y, size, size);
        gc.setFont(new Font(12));
        gc.setFill(Color.GREEN);
        gc.fillText("Arrows: " + arrowCount, getX(), getY() - 5);
    }

    // Archery action
private int arrowX = -1; // -1 means no arrow in flight
private int arrowY = -1;
private boolean arrowFlying = false;

@Override
public void doThing(GraphicsContext gc) {
    // Only launch a new arrow if none is flying
    if (!arrowFlying && arrowCount > 0) {
        arrowX = getX() + getSize();
        arrowY = getY() + getSize() / 2 - 5;
        arrowFlying = true;
        arrowCount--;
    }

    // Move and draw the arrow if it's in flight
    if (arrowFlying) {
        arrowX += 10; // speed — increase to go faster
        gc.setFill(Color.YELLOW);
        gc.fillOval(arrowX, arrowY, 10, 10);

        // Stop the arrow when it leaves the screen
        if (arrowX >= 1350) {
            arrowFlying = false;
        }
    }
}

    // Arrow getters/setters
    public int getArrowCount() { return arrowCount; }
    public void setArrowCount(int arrowCount) { this.arrowCount = arrowCount; }
    public void resetArrows() { this.arrowCount = 10; }
    public boolean checkFlight() {return arrowFlying;}
}