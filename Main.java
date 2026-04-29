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
   boolean enemycreator = true;
   long lastEnemyShot = 0;
   long lastWaveTime = 0;
   int wave = 0;
   String gameState = "MAIN_MENU"; // MAIN_MENU, CLASS_MENU, GAME

   ComboBox<String> menu = new ComboBox<>();
   StackPane sp = new StackPane();
   Canvas theCanvas = new Canvas(1368,768);
   GraphicsContext gc = theCanvas.getGraphicsContext2D(); 
   Image menuBackground = new Image("mainmenu.png");
   Button startButton = new Button("Start Game");
   Button archerButton = new Button("Archer Class");
   Button ninjaButton = new Button("Ninja Class");
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
   
      menu.getItems().addAll("Save", "Load", "Reset", "Exit");
      menu.setOnAction(new ComboBoxListener());
      menu.setVisible(false);
      archerButton.setVisible(false);
      ninjaButton.setVisible(false);
      
   
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
            if (player != null)
            {
               player.drawMe(player.getX(), player.getY(), gc);
            
               if (action)
               {
                  player.setShouldShoot(true);
                  action = false;
               }
            
               player.doThing(gc);
            
               if (up)    player.setY(player.getY() - 5);
               if (down)  player.setY(player.getY() + 5);
               if (left)  player.setX(player.getX() - 5);
               if (right) player.setX(player.getX() + 5);
            }
            if (enemycreator)
            {
               createEnemies();
            }
            drawEnemyWaves();
            enemyShooting(now);
            handleWave(now);
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
      for (int i = 0; i < 21; i++) {
         Enemy e = new Enemy();
         if(i < 7)
         {
            e.setX(1000);
            e.setY(100 + i * 100);
            enemies.add(e);            
         }
         else if(i > 6 && i < 14)
         {
            e.setX(1100);
            e.setY(100 + (i-7) * 100);
            enemies.add(e);
         }
         else if(i > 13)
         {
            e.setX(1200);
            e.setY(100 + (i-14) * 100);
            enemies.add(e);
         }
      }
      enemycreator = false;
   }
   public void drawEnemyWaves()
   {
      if(wave == 1)
      {
         for (int i = 0; i < 7; i++) 
         {
            enemies.get(i).drawMe(enemies.get(i).getX(), enemies.get(i).getY(), gc); 
         }
      }
      else if(wave == 2)
      {
         for (int i = 0; i < 14; i++) 
         {
            enemies.get(i).drawMe(enemies.get(i).getX(), enemies.get(i).getY(), gc); 
         }
      
      }   
      else if (wave == 3)
      {
         for (int i = 0; i < enemies.size(); i++) 
         {
            enemies.get(i).drawMe(enemies.get(i).getX(), enemies.get(i).getY(), gc); 
         }      
      
      }  
   
   }
   public void enemyShooting(long now)
   {
            if(wave == 1)
            {
               for (int i = 0; i < 7; i++) 
               {
                   // Only shoot every 5 seconds
                  if (now - lastEnemyShot >= 3_000_000_000L) {
                     enemies.get(i).setShouldShoot(true);
                  } 
                  enemies.get(i).doThing(gc);
               }
            
            // Reset the timer after all enemies have been told to shoot
               if (now - lastEnemyShot >= 3_000_000_000L) {
                  lastEnemyShot = now;
               }
            
            } 
            if(wave == 2)
            {
               for (int i = 0; i < 14; i++) 
               {
                   // Only shoot every 5 seconds
                  if (now - lastEnemyShot >= 3_000_000_000L) {
                     enemies.get(i).setShouldShoot(true);
                  } 
                  enemies.get(i).doThing(gc);
               }
            
            // Reset the timer after all enemies have been told to shoot
               if (now - lastEnemyShot >= 3_000_000_000L) {
                  lastEnemyShot = now;
               }
            
            }  
            if(wave == 3)
            {
               for (int i = 0; i < enemies.size(); i++) 
               {
                   // Only shoot every 5 seconds
                  if (now - lastEnemyShot >= 3_000_000_000L) {
                     enemies.get(i).setShouldShoot(true);
                  } 
                  enemies.get(i).doThing(gc);
               }
            
            // Reset the timer after all enemies have been told to shoot
               if (now - lastEnemyShot >= 3_000_000_000L) {
                  lastEnemyShot = now;
               }
            
            }               
   
   
   
   
   }
   public void handleWave(long now) {
       if (now - lastWaveTime >= 7_000_000_000L) {
           if(wave == 3)
           {
           
           }
           else
           {
           wave++;
           }
           lastWaveTime = now;
           System.out.println("Wave: " + wave); // remove once you have UI for it
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