import javafx.scene.paint.*;
import javafx.scene.canvas.*;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import java.util.*;


public class Ninja extends Player {
    private boolean shouldShoot = false;
    private List<int[]> stars = new ArrayList<>();

    // Public constructor
    public Ninja() {
        super();
    }

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

@Override
public void drawMe(int x, int y, GraphicsContext gc) {
    // Body
    gc.setFill(Color.RED);
    gc.fillRect(x + 8, y + 20, 14, 18);

    // Head
    gc.setFill(Color.PEACHPUFF);
    gc.fillOval(x + 6, y + 4, 18, 18);

    // Mask (covers lower face)
    gc.setFill(Color.RED);
    gc.fillRect(x + 6, y + 14, 18, 8);

    // Eyes (only visible part)
    gc.setFill(Color.WHITE);
    gc.fillOval(x + 9, y + 8, 5, 4);
    gc.fillOval(x + 16, y + 8, 5, 4);

    // Pupils
    gc.setFill(Color.BLACK);
    gc.fillOval(x + 11, y + 9, 2, 2);
    gc.fillOval(x + 18, y + 9, 2, 2);

    // Legs
    gc.setFill(Color.RED);
    gc.fillRect(x + 8, y + 38, 5, 12);
    gc.fillRect(x + 17, y + 38, 5, 12);

    // Arm holding shuriken
    gc.setStroke(Color.RED);
    gc.setLineWidth(2);
    gc.strokeLine(x + 22, y + 24, x + 32, y + 24);

    // Shuriken in hand
    gc.save();
    gc.translate(x + 34, y + 24);
    gc.rotate(45);
    gc.setFill(Color.DARKGRAY);
    double[] b1X = {0, -4, 4}; double[] b1Y = {-8, 0, 0};
    double[] b2X = {0, -4, 4}; double[] b2Y = {8, 0, 0};
    double[] b3X = {-8, 0, 0}; double[] b3Y = {0, -4, 4};
    double[] b4X = {8, 0, 0};  double[] b4Y = {0, -4, 4};
    gc.fillPolygon(b1X, b1Y, 3);
    gc.fillPolygon(b2X, b2Y, 3);
    gc.fillPolygon(b3X, b3Y, 3);
    gc.fillPolygon(b4X, b4Y, 3);
    gc.setFill(Color.SILVER);
    gc.fillOval(-3, -3, 6, 6);
    gc.restore();

    // Headband
    gc.setFill(Color.DARKGRAY);
    gc.fillRect(x + 5, y + 6, 20, 3);

    // Username
    gc.setFill(Color.WHITE);
    gc.setFont(Font.font("Orbitron", FontWeight.BOLD, 12));
    gc.fillText(name, x, y - 10);
}

    @Override
    public void doThing(GraphicsContext gc) {
        // Launch a new shuriken if space was pressed
        if (shouldShoot) {
            stars.add(new int[]{getX() + getSize(), getY() + getSize() / 2 - 5, 0});
            shouldShoot = false;
        }

        // Move and draw all shurikens every frame
        Iterator<int[]> it = stars.iterator();
        while (it.hasNext()) {
            int[] star = it.next();
            star[0] += 15;

            int ax = star[0];
            int ay = star[1];

            gc.save();
            gc.translate(ax, ay);
            gc.rotate(star[2]);
            star[2] = (star[2] + 10) % 360;

            // Top blade
            gc.setFill(Color.DARKGRAY);
            double[] blade1X = {0, -6, 6};
            double[] blade1Y = {-14, 0, 0};
            gc.fillPolygon(blade1X, blade1Y, 3);

            // Bottom blade
            double[] blade2X = {0, -6, 6};
            double[] blade2Y = {14, 0, 0};
            gc.fillPolygon(blade2X, blade2Y, 3);

            // Left blade
            double[] blade3X = {-14, 0, 0};
            double[] blade3Y = {0, -6, 6};
            gc.fillPolygon(blade3X, blade3Y, 3);

            // Right blade
            double[] blade4X = {14, 0, 0};
            double[] blade4Y = {0, -6, 6};
            gc.fillPolygon(blade4X, blade4Y, 3);

            // Center circle
            gc.setFill(Color.SILVER);
            gc.fillOval(-4, -4, 8, 8);

            gc.restore();

            if (star[0] > 1368) {
                it.remove();
            }
        }
    }
  @Override
    public void upgradeddoThing(GraphicsContext gc) {
        // Launch a new shuriken if space was pressed
        if (shouldShoot) {
            stars.add(new int[]{getX() + getSize(), getY() + getSize() / 2 - 5, 0});
            stars.add(new int[]{getX() + getSize(), getY() + getSize() / 2 - 5 - 20, 0});
            stars.add(new int[]{getX() + getSize(), getY() + getSize() / 2 - 5 + 20, 0});
            shouldShoot = false;
        }

        // Move and draw all shurikens every frame
        Iterator<int[]> it = stars.iterator();
        while (it.hasNext()) {
            int[] star = it.next();
            star[0] += 15;

            int ax = star[0];
            int ay = star[1];

            gc.save();
            gc.translate(ax, ay);
            gc.rotate(star[2]);
            star[2] = (star[2] + 10) % 360;

            // Top blade
            gc.setFill(Color.DARKGRAY);
            double[] blade1X = {0, -6, 6};
            double[] blade1Y = {-14, 0, 0};
            gc.fillPolygon(blade1X, blade1Y, 3);

            // Bottom blade
            double[] blade2X = {0, -6, 6};
            double[] blade2Y = {14, 0, 0};
            gc.fillPolygon(blade2X, blade2Y, 3);

            // Left blade
            double[] blade3X = {-14, 0, 0};
            double[] blade3Y = {0, -6, 6};
            gc.fillPolygon(blade3X, blade3Y, 3);

            // Right blade
            double[] blade4X = {14, 0, 0};
            double[] blade4Y = {0, -6, 6};
            gc.fillPolygon(blade4X, blade4Y, 3);

            // Center circle
            gc.setFill(Color.SILVER);
            gc.fillOval(-4, -4, 8, 8);

            gc.restore();

            if (star[0] > 1368) {
                it.remove();
            }
        }
    }    
public boolean checkCollisions(Player enemy)
{
    Iterator<int[]> it = stars.iterator();
    while (it.hasNext()) {
        int[] star = it.next();

        // Center of enemy circle
        int cx = enemy.getX() + enemy.getSize() / 2;
        int cy = enemy.getY() + enemy.getSize() / 2;
        int radius = enemy.getSize() / 2;

        // Check if shuriken point is within the circle
        double dist = Math.sqrt(Math.pow(star[0] - cx, 2) + Math.pow(star[1] - cy, 2));

        if (dist <= radius + 15) { // +15 accounts for shuriken speed
            it.remove();
            return true;
        }
    }
    return false;
}

    // Shuriken shooting control
    @Override
    public void setShouldShoot(boolean b) { shouldShoot = b; }
}