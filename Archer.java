import javafx.scene.paint.*;
import javafx.scene.canvas.*;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
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
    @Override
    public String getName() { return name; }
    @Override
    public void setName(String name) { this.name = name; }

    // Draws the archer
@Override
public void drawMe(int x, int y, GraphicsContext gc) {
    // Body
    gc.setFill(Color.GREEN);
    gc.fillRect(x + 8, y + 20, 14, 18);

    // Head
    gc.setFill(Color.PEACHPUFF);
    gc.fillOval(x + 6, y + 4, 18, 18);

    // Eyes
    gc.setFill(Color.WHITE);
    gc.fillOval(x + 9, y + 8, 5, 4);
    gc.fillOval(x + 16, y + 8, 5, 4);

    // Pupils
    gc.setFill(Color.BLACK);
    gc.fillOval(x + 11, y + 9, 2, 2);
    gc.fillOval(x + 18, y + 9, 2, 2);

    // Legs
    gc.setFill(Color.BROWN);
    gc.fillRect(x + 8, y + 38, 5, 12);
    gc.fillRect(x + 17, y + 38, 5, 12);

    // Bow (arc)
    gc.setStroke(Color.SADDLEBROWN);
    gc.setLineWidth(3);
    gc.strokeArc(x + 22, y + 10, 12, 28, 90, 180, javafx.scene.shape.ArcType.OPEN);

    // Bowstring
    gc.setStroke(Color.WHITE);
    gc.setLineWidth(1);
    gc.strokeLine(x + 22, y + 10, x + 22, y + 38);

    // Arrow on bow
    gc.setStroke(Color.BROWN);
    gc.setLineWidth(2);
    gc.strokeLine(x + 14, y + 24, x + 28, y + 24);

    // Arrow tip
    gc.setFill(Color.DARKGRAY);
    double[] tipX = {x + 28, x + 34, x + 28};
    double[] tipY = {y + 20, y + 24, y + 28};
    gc.fillPolygon(tipX, tipY, 3);

    // Hat
    gc.setFill(Color.DARKGREEN);
    gc.fillRect(x + 4, y + 2, 22, 5);
    gc.fillRect(x + 8, y - 4, 14, 8);

    // Username
    gc.setFill(Color.WHITE);
    gc.setFont(Font.font("Orbitron", FontWeight.BOLD, 12));
    gc.fillText(name, x, y - 10);
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
@Override
public void upgradeddoThing(GraphicsContext gc) {
    // Launch a new arrow if space was pressed
    if (shouldShoot) {
        arrows.add(new int[]{getX() + getSize(), getY() + getSize() / 2 - 5});       // middle
        arrows.add(new int[]{getX() + getSize(), getY() + getSize() / 2 - 5 - 20});  // above
        arrows.add(new int[]{getX() + getSize(), getY() + getSize() / 2 - 5 + 20});  // below
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
public boolean checkCollisions(Player enemy)
{
    Iterator<int[]> it = arrows.iterator();
    while (it.hasNext()) {
        int[] arrow = it.next();

        // Center of TARGET's circle — not this player's position
        int cx = enemy.getX() + enemy.getSize() / 2;
        int cy = enemy.getY() + enemy.getSize() / 2;
        int radius = enemy.getSize() / 2;

        double dist = Math.sqrt(Math.pow(arrow[0] - cx, 2) + Math.pow(arrow[1] - cy, 2));

        if (dist <= radius + 15) {
            it.remove();
            return true;
        }
    }
    return false;
}
    // Arrow shooting control
    public void setShouldShoot(boolean b) { shouldShoot = b; }
}