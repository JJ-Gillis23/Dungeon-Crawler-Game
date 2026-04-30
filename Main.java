import javafx.application.*;
import javafx.scene.*;
import javafx.stage.*;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.*;
import javafx.event.*;
import javafx.scene.canvas.*;
import javafx.scene.input.*;
import javafx.animation.*;
import javafx.scene.image.Image;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

import java.util.*;

public class Main extends Application
{
   boolean up, down, left, right, action;
   boolean bossSpawnedThisLevel = false;
   boolean enemycreator = true;
   boolean alive = true;
   boolean bossactive = false;
   int wave = 1;
   int level = 3;
   int score = 0;
   long lastWaveTime = 0;
   long lastEnemyShot = 0;
   String gameState = "MAIN_MENU"; // MAIN_MENU, CLASS_MENU, GAME
   String name;

   ComboBox<String> menu = new ComboBox<>();
   StackPane sp = new StackPane();
   Canvas theCanvas = new Canvas(1368,768);
   GraphicsContext gc = theCanvas.getGraphicsContext2D(); 
   Image menuBackground = new Image("mainmenu.png");
   Button startButton = new Button("Start Game");
   Button archerButton = new Button("Archer Class");
   Button ninjaButton = new Button("Ninja Class");
   Button restartButton = new Button("Restart Game");
   Label label1 = new Label("Name:");
   TextField username = new TextField ();
   Player player = null;
   ArrayList<Enemy> enemies = new ArrayList<>();
   AnimationHandler ta = new AnimationHandler();

   public void start(Stage stage)
   {
      sp.getChildren().add(theCanvas);
      sp.getChildren().add(menu);
      sp.getChildren().add(startButton);
      sp.getChildren().add(archerButton);
      sp.getChildren().add(ninjaButton);
      sp.getChildren().add(restartButton);
      sp.getChildren().add(username);
   
      menu.getItems().addAll("Save", "Load", "Reset", "Exit");
      menu.setOnAction(new ComboBoxListener());
      menu.setVisible(false);
      archerButton.setVisible(false);
      ninjaButton.setVisible(false);
      restartButton.setVisible(false);
      username.setVisible(false);
      
   
      // Start button goes to class menu
      startButton.setOnAction(
         e -> {
            gameState = "CLASS_MENU";
            startButton.setVisible(false);
         });
   
      // Archer button starts the game
      archerButton.setOnAction(
         e -> {
            player = new Archer();
            player.setX(500);
            player.setY(200);
            gameState = "GAME";
            archerButton.setVisible(false);
            ninjaButton.setVisible(false);
            username.setVisible(false);
         });
      //Ninja button starts the game
      ninjaButton.setOnAction(
         e -> {
            player = new Ninja();
            player.setX(500);
            player.setY(200);
            gameState = "GAME";
            ninjaButton.setVisible(false);
            archerButton.setVisible(false);
            username.setVisible(false);
         });
      
   
      Scene scene = new Scene(sp, 1368, 768);
      stage.setScene(scene);
      stage.setTitle("Dungeon Crawler");
   
      scene.setOnKeyPressed(new KeyListenerDown());
      scene.setOnKeyReleased(new KeyListenerUp());
   
      stage.show();
      sp.requestFocus();
   
      ta.start();
   } 

   public void drawMainMenu()
   {
      gc.clearRect(0, 0, theCanvas.getWidth(), theCanvas.getHeight());
      gc.drawImage(menuBackground, 0, 0, theCanvas.getWidth(), theCanvas.getHeight());
   
      if (!startButton.isVisible())
      {
         startButton.setVisible(true);
      }
   
      startButton.setFont(Font.font("Arial", FontWeight.BOLD, 24));
      
   }

   public void drawClassMenu()
   {
      gc.clearRect(0, 0, theCanvas.getWidth(), theCanvas.getHeight());
      gc.setFill(Color.BLACK);
      gc.fillRect(0, 0, theCanvas.getWidth(), theCanvas.getHeight());
      gc.setFill(Color.WHITE);
      gc.setFont(Font.font("Arial", FontWeight.BOLD, 36));
      gc.fillText("Choose Your Class", 530, 200);
      gc.setFont(Font.font("Arial", FontWeight.BOLD, 20));
      gc.setFill(Color.CYAN);
      gc.fillText("- Given 100 arrows to",400,300);
      gc.fillText("take down your enemies",400,320);
      gc.fillText("- Can upgrade to shoot",400,370);
      gc.fillText("multiple arrows at once",400,390);
      gc.setFill(Color.CYAN);
      gc.fillText("- Given 100 shurikens to",780,300);
      gc.fillText("take down your enemies",780,320);
      gc.fillText("- Can upgrade to shoot",780,370);
      gc.fillText("multiple shurikens at once",780,390);
      // Draw a decorative arrow on the class menu
      int ax = 470; // adjust X position to match your button
      int ay = 435;  // adjust Y position to match your button
      
      // Arrow shaft
      gc.setStroke(Color.BROWN);
      gc.setLineWidth(4);
      gc.strokeLine(ax, ay, ax + 60, ay);
      
      // Arrow tip (triangle)
      gc.setFill(Color.DARKGRAY);
      double[] tipX = {ax + 60, ax + 80, ax + 60};
      double[] tipY = {ay - 8, ay, ay + 8};
      gc.fillPolygon(tipX, tipY, 3);
      
      // Arrow tail/fletching
      gc.setStroke(Color.RED);
      gc.setLineWidth(2);
      gc.strokeLine(ax, ay, ax - 10, ay - 8);
      gc.strokeLine(ax, ay, ax - 10, ay + 8);
      //Draw a decorative shuriken on the class menu
      int sx = 885; // adjust X position to match your button
      int sy = 435;  // adjust Y position to match your button
      gc.save();
      gc.translate(sx, sy);
      gc.rotate(45);
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
   
   
      if (!archerButton.isVisible())
      {
         archerButton.setVisible(true);
      }
      if (!ninjaButton.isVisible())
      {
         ninjaButton.setVisible(true);
      }
   
      archerButton.setFont(Font.font("Arial", FontWeight.BOLD, 24));
      archerButton.setTranslateX(-180); // negative = left, positive = right
      archerButton.setTranslateY(100);    // negative = up, positive = down
      ninjaButton.setFont(Font.font("Arial", FontWeight.BOLD, 24));
      ninjaButton.setTranslateX(200); // negative = left, positive = right
      ninjaButton.setTranslateY(100);    // negative = up, positive = down 
      gc.setFill(Color.WHITE);
      gc.setFont(Font.font("Arial", FontWeight.BOLD, 24));
      gc.fillText("Please Enter a Username:", 300, 625);
      username.setVisible(true);
      username.setTranslateX(30);  // move left/right
      username.setTranslateY(230);  // move up/down
      username.setMaxWidth(200);
      username.setMaxHeight(30);
      username.setFont(Font.font("Arial", 28)); // smaller font    
      username.setOnKeyPressed(e -> {
         if (e.getCode() == KeyCode.ENTER) {
           name = username.getText().trim();
            if (!name.isEmpty()) {
               username.setVisible(false);
               sp.requestFocus();
            }
         }
      });    
   }
   public void drawHud()
   {
      int barX = 10;
      int barY = 50;
      int barWidth = 200;
      int barHeight = 20;
      int borderThickness = 3;

      // Level text
      gc.setFill(Color.WHITE);
      gc.setFont(Font.font("Orbitron", FontWeight.BOLD, 18));
      gc.fillText("Level: " + level, 10, 40);
      gc.fillText("Score: " + score, 110, 40); // display score

      // White border
      gc.setFill(Color.WHITE);
      gc.fillRect(barX - borderThickness, barY - borderThickness, 
                  barWidth + borderThickness * 2, barHeight + borderThickness * 2);

      // Black background of bar
      gc.setFill(Color.BLACK);
      gc.fillRect(barX, barY, barWidth, barHeight);

      // Red health fill — out of 100
      gc.setFill(Color.RED);
      if(player == null) return; // safety check
      double healthPercent = player.getHealth() / 100.0;
      gc.fillRect(barX, barY, (int)(barWidth * healthPercent), barHeight);
   }
   public void drawDeathScreen()
   {
      gc.clearRect(0, 0, theCanvas.getWidth(), theCanvas.getHeight());
      gc.setFill(Color.BLACK);
      gc.fillRect(0, 0, theCanvas.getWidth(), theCanvas.getHeight());
      gc.setFill(Color.RED);
      gc.setFont(Font.font("Orbitron", FontWeight.BOLD, 48));
      gc.fillText("You Died!", 550, 300);
      gc.setFont(Font.font("Orbitron", FontWeight.BOLD, 48));
      gc.fillText("Restart and try again!", 530, 350);

      restartButton.setVisible(true);
      restartButton.setFont(Font.font("Orbitron", FontWeight.BOLD, 24));
      restartButton.setTranslateX(0);   // centered horizontally
      restartButton.setTranslateY(100); // slightly below center
      restartButton.setOnAction(e -> {
         gameState = "CLASS_MENU";
         restartButton.setVisible(false);
      });
   }
   public void drawBackground()
   {
      gc.setFill(Color.BLACK);
      gc.fillRect(0, 0, theCanvas.getWidth(), theCanvas.getHeight());   
   }

   public class AnimationHandler extends AnimationTimer
   {
      @Override
      public void handle(long now)
      {
         if (gameState.equals("MAIN_MENU"))
         {
            drawMainMenu();
         }
         else if (gameState.equals("CLASS_MENU"))
         {  

            drawClassMenu();
         }
         else if (gameState.equals("GAME"))
         {
            drawBackground();
            checkDeath();
            if (player != null)
            {
               player.setName(name);
               player.drawMe(player.getX(), player.getY(), gc);
               drawHud();
            
               if (action)
               {
                  player.setShouldShoot(true);
                  action = false;
               }
            
               if(level >= 3) {
                  player.upgradeddoThing(gc);
               }
               else {
                  player.doThing(gc);
               }
            
               if (up)    player.setY(player.getY() - 5);
               if (down)  player.setY(player.getY() + 5);
               if (left)  player.setX(player.getX() - 5);
               if (right) player.setX(player.getX() + 5);

               if (enemycreator && !(level % 3 == 0)) // don't spawn normal enemies on boss levels
               {
                  createEnemies();
               }
               else if (level % 3 == 0 && !bossactive && !bossSpawnedThisLevel) {
                  createBoss();
               }
               handleWave(now);
               drawEnemyWaves();
               enemyShooting(now);
               moveEnemies();
               checkCollisions(player);
            }
         }
         else if (gameState.equals("DEATH_SCREEN"))
         {
            drawDeathScreen();
         }
           
      }
   }

   public class KeyListenerDown implements EventHandler<KeyEvent>  
   {
      public void handle(KeyEvent event) 
      { 
         if (event.getCode() == KeyCode.ESCAPE) 
         {
            if (menu.isVisible()) 
            {
               menu.setVisible(false);
               sp.requestFocus();
            } 
            else 
            {
               menu.setVisible(true);
               menu.requestFocus();
            }
         }
      
         if (event.getCode() == KeyCode.W) up = true;
         if (event.getCode() == KeyCode.A) left = true;
         if (event.getCode() == KeyCode.S) down = true;
         if (event.getCode() == KeyCode.D) right = true;
         if (event.getCode() == KeyCode.SPACE) action = true;
      }
   }

   public class KeyListenerUp implements EventHandler<KeyEvent>  
   {
      public void handle(KeyEvent event) 
      { 
         if (event.getCode() == KeyCode.W) up = false;
         if (event.getCode() == KeyCode.A) left = false;
         if (event.getCode() == KeyCode.S) down = false;
         if (event.getCode() == KeyCode.D) right = false;
         if (event.getCode() == KeyCode.SPACE) action = false;
      }
   }

public void createEnemies()
{
    
    int columns = getColumnsForWave(wave);
    
    for (int col = 0; col < columns; col++) {
        for (int row = 0; row < 6; row++) {
            Enemy e = new Enemy();
            e.setX(1000 + col * 100);
            e.setY(100 + row * 100);
            e.setSize(50);
            e.setHealth(25); // scale health with level (optional)
            enemies.add(e);
        }
    }
    enemycreator = false;
}
public void createBoss()
{
    enemies.clear();

    // Boss position
    int bossX = 1250;
    int bossY = 334;

    // 5 columns of guards in front of boss, 6 rows — same as createEnemies
    for (int col = 0; col < 5; col++) {
        for (int row = 0; row < 6; row++) {
            Enemy guard = new Enemy();
            guard.setX(bossX - 350 + (col * 100)); // same 100px column spacing
            guard.setY(100 + (row * 100));           // same 100px row spacing
            guard.setSize(50);
            guard.setHealth(25);
            enemies.add(guard);
        }
    }

    // Boss behind the wall, vertically centered
    SuperEnemy boss = new SuperEnemy();
    boss.setX(bossX);
    boss.setY(bossY);
    boss.setSize(80);
    boss.setHealth(50);
    enemies.add(boss);
    bossactive = true;
    bossSpawnedThisLevel = true;
}


// How many columns based on wave and level
public int getColumnsForWave(int wave)
{
    int base = 1 + (wave / 3); // adds a column every 3 waves
    return Math.min(base + level, 5); // cap at 5 columns, scales with level
}

// How many rows based on wave and level
/*public int getRowsForWave(int wave)
{
    int base = 3 + (wave / 2); // adds a row every 2 waves
    return Math.min(base + level, 8); // cap at 8 rows, scales with level
}*/

public void drawEnemyWaves()
{
    for (int i = 0; i < enemies.size(); i++)
    {
        enemies.get(i).drawMe(enemies.get(i).getX(), enemies.get(i).getY(), gc);
    }
}

public void enemyShooting(long now)
{
    // Initialize timer on first frame
    if (lastEnemyShot == 0) {
        lastEnemyShot = now;
        return;
    }

    long shootInterval = Math.max(1_000_000_000L, 5_000_000_000L - (wave * 100_000_000L) - (level * 200_000_000L));

    for (int i = 0; i < enemies.size(); i++)
    {
        if (now - lastEnemyShot >= shootInterval) {
            enemies.get(i).setShouldShoot(true);
        }
        enemies.get(i).doThing(gc);
    }

    if (now - lastEnemyShot >= shootInterval) {
        lastEnemyShot = now;
    }
}
public void handleWave(long now)
{
       if (lastWaveTime == 0) {
        lastWaveTime = now;
        return;
    }
        // Boss level — advance when all enemies are dead
    if (level % 3 == 0 && bossSpawnedThisLevel && enemies.isEmpty()) {
        level++;
        player.setHealth(100);
        wave = 1;
        enemycreator = true;
        bossSpawnedThisLevel = false;
        bossactive = false;
        lastWaveTime = now;
        System.out.println("Boss defeated! Level: " + level);
        return;
    }
    if (now - lastWaveTime >= 7_000_000_000L)
    {
        if (wave < getMaxWaves())
        {
            wave++;
            enemycreator = true; // respawn enemies for new wave
            createEnemies();
        }
        else
        {
            // All waves done — go to next level
            level++;
            player.setHealth(100); // restore health on level up
            wave = 1;
            enemycreator = true;
            bossSpawnedThisLevel = false; // reset boss spawn flag for new level
            createEnemies();
            System.out.println("Level: " + level);
        }
        lastWaveTime = now;
        System.out.println("Wave: " + wave);
    }
}

// Easy to change max waves per level
public int getMaxWaves()
{
    return 5 + level *2;
}
public void moveEnemies() {
    for (Enemy e : enemies) {
        e.move(e);
    }
}
public void checkCollisions(Player player)
{
    for (int i = enemies.size() - 1; i >= 0; i--) // iterate backwards when removing
    {
        if (i >= enemies.size()) continue; // safety check

        if (enemies.get(i).checkCollisions(player)) {
            player.setHealth(player.getHealth() - 5);
        }
         if (i < enemies.size() && player.checkCollisions(enemies.get(i))) {
            enemies.get(i).setHealth(enemies.get(i).getHealth() - 25);
            System.out.println("Enemy health: " + enemies.get(i).getHealth()); // debug
            if (enemies.get(i).getHealth() <= 0) {
               boolean isBoss = enemies.get(i) instanceof SuperEnemy; // ✅ check BEFORE remove
               enemies.remove(i);
               if (isBoss) {
                     bossactive = false;
                     score += 50;
               } else {
                     score += 25;
               }
            }
            continue;
         }
        if (i < enemies.size() && enemies.get(i).getX() < 0) { 
         // mark as "dead" to remove in next loop
         boolean isBoss = enemies.get(i) instanceof SuperEnemy;
         enemies.remove(i);
         if (isBoss) {
            bossactive = false;
            player.setHealth(player.getHealth() - 20); // penalty for letting enemy pass
        }
        else {
            player.setHealth(player.getHealth() - 1); // bigger penalty for letting regular enemy pass
        }
      }
    }
}
public void checkDeath() {
    if(player.getHealth() <= 0) {
        alive = false;
        gameState = "DEATH_SCREEN";
        player = null;
        enemies.clear();
        wave = 1;
        level = 1;
    }
}

   public class ComboBoxListener implements EventHandler<ActionEvent>
   {
      public void handle(ActionEvent e)
      {
         if (menu.getValue() == null)
         {
            menu.setValue("Menu");
            return;
         }
         sp.requestFocus();
         switch(menu.getValue())
         {
            case "Save":
               break;
            case "Load":
               break;
            case "Reset":
               gameState = "MAIN_MENU";
               break;
            case "Exit":
               System.exit(0);
               break;
         }
         menu.setVisible(false);
         archerButton.setVisible(false);
         ninjaButton.setVisible(false);
         sp.requestFocus();
      }
   }
}