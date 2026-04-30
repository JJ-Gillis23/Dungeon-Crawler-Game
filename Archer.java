import javafx.scene.paint.*;
import javafx.scene.canvas.*;
import javafx.scene.text.Font;
import java.util.*;
public class Archer extends Player {

    
    // Public constructor
    public Archer() {
        super();
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

    @Override
    public void setHealth(int health) { this.health = health; }

    @Override
    public int getHealth() { return health; }

    // Draws the archer
    @Override
    public void drawMe(int x, int y, GraphicsContext gc) {
        gc.setFill(Color.BLUE);
        gc.fillRect(x, y, size, size);
    }

    // Archery action
private int arrowX = -1; // -1 means no arrow in flight
private int arrowY = -1;
private boolean shouldShoot = false;
private List<int[]> arrows = new ArrayList<>(); // each int[] is {x, y}

@Override
public void doThing(GraphicsContext gc) {
    // Launch a new arrow if space was pressed
    if (shouldShoot) {
        arrows.add(new int[]{getX() + getSize(), getY() + getSize() / 2 - 5});
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

public boolean checkCollisions(Player player)
{
    Iterator<int[]> it = arrows.iterator();
    while (it.hasNext()) {
        int[] arrow = it.next();
        
        System.out.println("Arrow: " + arrow[0] + ", " + arrow[1]);
        System.out.println("Player bounds: " + player.getX() + " to " + (player.getX() + player.getSize()) + " | " + player.getY() + " to " + (player.getY() + player.getSize()));
        
        if (arrow[0] + 30 >= player.getX() && // +30 accounts for arrow width
            arrow[0] <= player.getX() + player.getSize() &&
            arrow[1] + 8 >= player.getY() && // +8 accounts for arrow height
            arrow[1] - 8 <= player.getY() + player.getSize()) {
            it.remove();
            return true;
        }
    }
    return false;
}

    // Arrow shooting control
    public void setShouldShoot(boolean b) { shouldShoot = b; }
}