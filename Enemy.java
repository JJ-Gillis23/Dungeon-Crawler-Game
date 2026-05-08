import javafx.scene.paint.*;
import javafx.scene.canvas.*;
import javafx.scene.text.Font;
import java.util.*;
public class Enemy extends Player {

    
    // Public constructor
    public Enemy() {
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
    

    // Draws the enemy
@Override
public void drawMe(int x, int y, GraphicsContext gc) {
    int radius = size / 2;
    int cx = x + radius;
    int cy = y + radius;

    // Outer glow effect
    gc.setFill(Color.color(0.5, 0, 0, 0.3));
    gc.fillOval(x - 5, y - 5, size + 10, size + 10);

    // Main body
    gc.setFill(Color.DARKRED);
    gc.fillOval(x, y, size, size);

    // Shine highlight
    gc.setFill(Color.color(1, 0.3, 0.3, 0.4));
    gc.fillOval(x + size/4, y + size/6, size/4, size/5);

    // Left eye white
    gc.setFill(Color.WHITE);
    gc.fillOval(cx - 14, cy - 10, 12, 10);

    // Right eye white
    gc.fillOval(cx + 2, cy - 10, 12, 10);

    // Left pupil (angry — shifted inward)
    gc.setFill(Color.BLACK);
    gc.fillOval(cx - 10, cy - 8, 6, 7);

    // Right pupil (angry — shifted inward)
    gc.fillOval(cx + 5, cy - 8, 6, 7);

    // Angry eyebrows
    gc.setStroke(Color.BLACK);
    gc.setLineWidth(2);
    gc.strokeLine(cx - 15, cy - 14, cx - 2, cy - 11); // left brow angled down inward
    gc.strokeLine(cx + 2, cy - 11, cx + 15, cy - 14); // right brow angled down inward

    // Mouth (jagged evil grin)
    gc.setStroke(Color.BLACK);
    gc.setLineWidth(2);
    gc.strokeLine(cx - 10, cy + 8, cx - 6, cy + 12);
    gc.strokeLine(cx - 6, cy + 12, cx - 2, cy + 8);
    gc.strokeLine(cx - 2, cy + 8, cx + 2, cy + 12);
    gc.strokeLine(cx + 2, cy + 12, cx + 6, cy + 8);
    gc.strokeLine(cx + 6, cy + 8, cx + 10, cy + 12);
}

//Bullet action
protected boolean shouldShoot = false;
protected List<int[]> bullets = new ArrayList<>(); // each int[] is {x, y}

@Override
public void doThing(GraphicsContext gc) {
    // Launch a new bullet if triggered
    if (shouldShoot) {
        bullets.add(new int[]{getX(), getY() + getSize() / 2}); // spawn at left edge of enemy
        shouldShoot = false;
    }

    // Move and draw all bullets every frame
    Iterator<int[]> it = bullets.iterator();
    while (it.hasNext()) {
        int[] bullet = it.next();
        bullet[0] -= 15; // move LEFT instead of right

        int ax = bullet[0];
        int ay = bullet[1];

        // Bullet body (small rectangle)
        gc.setFill(Color.YELLOW);
        gc.fillRect(ax, ay - 4, 16, 8); // wide rectangle for bullet body

        // Bullet tip (triangle pointing LEFT)
        gc.setFill(Color.ORANGE);
        double[] tipX = {ax, ax - 10, ax};
        double[] tipY = {ay - 4, ay, ay + 4};
        gc.fillPolygon(tipX, tipY, 3);

        // Bullet casing rim (right edge detail)
        gc.setStroke(Color.DARKGRAY);
        gc.setLineWidth(2);
        gc.strokeLine(ax + 16, ay - 4, ax + 16, ay + 4);

        // Remove when off the left edge of screen
        if (bullet[0] < -20) {
            it.remove();
        }
    }
}
@Override
public void upgradeddoThing(GraphicsContext gc) {
    // Launch a new bullet if triggered
    if (shouldShoot) {
        bullets.add(new int[]{getX(), getY() + getSize() / 2}); // spawn at left edge of enemy
        bullets.add(new int[]{getX(), getY() + getSize() / 2 - 20}); // spawn above the enemy
        bullets.add(new int[]{getX(), getY() + getSize() / 2 + 20}); // spawn below the enemy
        shouldShoot = false;
    }

    // Move and draw all bullets every frame
    Iterator<int[]> it = bullets.iterator();
    while (it.hasNext()) {
        int[] bullet = it.next();
        bullet[0] -= 15; // move LEFT instead of right

        int ax = bullet[0];
        int ay = bullet[1];

        // Bullet body (small rectangle)
        gc.setFill(Color.YELLOW);
        gc.fillRect(ax, ay - 4, 16, 8); // wide rectangle for bullet body

        // Bullet tip (triangle pointing LEFT)
        gc.setFill(Color.ORANGE);
        double[] tipX = {ax, ax - 10, ax};
        double[] tipY = {ay - 4, ay, ay + 4};
        gc.fillPolygon(tipX, tipY, 3);

        // Bullet casing rim (right edge detail)
        gc.setStroke(Color.DARKGRAY);
        gc.setLineWidth(2);
        gc.strokeLine(ax + 16, ay - 4, ax + 16, ay + 4);

        // Remove when off the left edge of screen
        if (bullet[0] < -20) {
            it.remove();
        }
    }
}
public boolean checkCollisions(Player player) {
    Iterator<int[]> it = bullets.iterator();
    while (it.hasNext()) {
        int[] bullet = it.next();
        int ax = bullet[0];
        int ay = bullet[1];
        // Simple collision check: if bullet overlaps player rectangle
        if (ax < player.getX() + player.getSize() &&
            ax + 16 > player.getX() &&
            ay - 4 < player.getY() + player.getSize() &&
            ay + 4 > player.getY()) {
            // Collision detected
            it.remove(); // Remove bullet on hit
            return true; // Collision detected
        }
    }
    return false; // No collision detected
}
public void move(Enemy enemy) {
    // Move left by 1 pixel every frame
    enemy.setX(enemy.getX() - 1);
}


    public void setShouldShoot(boolean b) { shouldShoot = b; }
}