import javafx.scene.paint.*;
import javafx.scene.canvas.*;
import javafx.scene.text.Font;
import java.util.*;
public class Archer extends Player {
    private int arrowCount;

    
    // Public constructor
    public Archer() {
        super();
        arrowCount = 100;
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
        gc.setFont(new Font("SansSerif",12));
        gc.setFill(Color.WHITE);
        gc.fillText("Arrows: " + arrowCount, getX(), getY() - 5);
    }

    // Archery action
private int arrowX = -1; // -1 means no arrow in flight
private int arrowY = -1;
private boolean shouldShoot = false;
private List<int[]> arrows = new ArrayList<>(); // each int[] is {x, y}

@Override
public void doThing(GraphicsContext gc) {
    // Launch a new arrow if space was pressed
    if (arrowCount > 0 && shouldShoot) {
        arrows.add(new int[]{getX() + getSize(), getY() + getSize() / 2 - 5});
        arrowCount--;
        shouldShoot = false;
    }

    // Move and draw all arrows every frame
    Iterator<int[]> it = arrows.iterator();
    while (it.hasNext()) {
        int[] arrow = it.next();
        arrow[0] += 15;

        int ax = arrow[0];
        int ay = arrow[1];

        // Arrow shaft
        gc.setStroke(Color.BROWN);
        gc.setLineWidth(2);
        gc.strokeLine(ax, ay, ax + 20, ay);

        // Arrow tip (triangle)
        gc.setFill(Color.DARKGRAY);
        double[] tipX = {ax + 20, ax + 30, ax + 20};
        double[] tipY = {ay - 4, ay, ay + 4};
        gc.fillPolygon(tipX, tipY, 3);

        // Arrow tail/fletching
        gc.setStroke(Color.RED);
        gc.setLineWidth(1);
        gc.strokeLine(ax, ay, ax - 5, ay - 4);
        gc.strokeLine(ax, ay, ax - 5, ay + 4);

        if (arrow[0] > 1368) {
            it.remove();
        }
    }
}



    // Arrow getters/setters
    public int getArrowCount() { return arrowCount; }
    public void setArrowCount(int arrowCount) { this.arrowCount = arrowCount; }
    public void resetArrows() { this.arrowCount = 10; }
    public void setShouldShoot(boolean b) { shouldShoot = b; }
}