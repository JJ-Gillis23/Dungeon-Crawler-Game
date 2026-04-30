import javafx.scene.paint.*;
import javafx.scene.canvas.*;
import javafx.scene.text.Font;
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
    public void drawMe(int x, int y, GraphicsContext gc) {
        gc.setFill(Color.RED);
        gc.fillRect(x, y, size, size);

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
    public boolean checkCollisions(Player player)
    {
        Iterator<int[]> it = stars.iterator();
        while (it.hasNext()) {
            int[] star = it.next();

            if (star[0] + 15 >= player.getX() && 
                star[0] <= player.getX() + player.getSize() &&
                star[1] + 8 >= player.getY() && 
                star[1] - 8 <= player.getY() + player.getSize()) {
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