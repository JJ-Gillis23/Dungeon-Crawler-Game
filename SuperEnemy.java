import javafx.scene.paint.*;
import javafx.scene.canvas.*;
import javafx.scene.text.Font;
import java.util.*;
public class SuperEnemy extends Enemy {

    
    // Public constructor
    public SuperEnemy() {
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
    int scale = 2; // twice the size
    int radius = (size * scale) / 2;
    int cx = x + radius;
    int cy = y + radius;

    // Outer glow effect
    gc.setFill(Color.color(0.5, 0, 0, 0.3));
    gc.fillOval(x - 10, y - 10, size * scale + 20, size * scale + 20);

    // Main body
    gc.setFill(Color.DARKRED);
    gc.fillOval(x, y, size * scale, size * scale);

    // Shine highlight
    gc.setFill(Color.color(1, 0.3, 0.3, 0.4));
    gc.fillOval(x + (size * scale)/4, y + (size * scale)/6, (size * scale)/4, (size * scale)/5);

    // Left eye white
    gc.setFill(Color.WHITE);
    gc.fillOval(cx - 28, cy - 20, 24, 20);

    // Right eye white
    gc.fillOval(cx + 4, cy - 20, 24, 20);

    // Left pupil
    gc.setFill(Color.BLACK);
    gc.fillOval(cx - 20, cy - 16, 12, 14);

    // Right pupil
    gc.fillOval(cx + 10, cy - 16, 12, 14);

    // Angry eyebrows
    gc.setStroke(Color.BLACK);
    gc.setLineWidth(4);
    gc.strokeLine(cx - 30, cy - 28, cx - 4, cy - 22);
    gc.strokeLine(cx + 4, cy - 22, cx + 30, cy - 28);

    // Mouth (jagged evil grin)
    gc.setStroke(Color.BLACK);
    gc.setLineWidth(4);
    gc.strokeLine(cx - 20, cy + 16, cx - 12, cy + 24);
    gc.strokeLine(cx - 12, cy + 24, cx - 4, cy + 16);
    gc.strokeLine(cx - 4, cy + 16, cx + 4, cy + 24);
    gc.strokeLine(cx + 4, cy + 24, cx + 12, cy + 16);
    gc.strokeLine(cx + 12, cy + 16, cx + 20, cy + 24);
}

//Bullet action
private List<int[]> bullets = new ArrayList<>();

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