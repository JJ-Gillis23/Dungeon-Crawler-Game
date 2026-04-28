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
import javafx.scene.text.FontPosture; // optional (for italic)

import java.util.*;

public class Main extends Application
{
   boolean start, up, down, left, right, action;

   ComboBox<String> menu = new ComboBox<>();
   StackPane sp = new StackPane();
   Canvas theCanvas = new Canvas(1368,768);
   GraphicsContext gc = theCanvas.getGraphicsContext2D(); 
   Image menuBackground = new Image("mainmenu.png");
   Button startButton = new Button("Start Game");
   Archer player = new Archer();
   AnimationHandler ta = new AnimationHandler();
   int arrowtimer = 0;

   public void start(Stage stage)
   {
      player.setX(500);
      player.setY(200);
      player.setArrowCount(10);
      sp.getChildren().add(theCanvas);
      sp.getChildren().add(menu);
      sp.getChildren().add(startButton);
      menu.getItems().addAll("Save", "Load", "Reset", "Exit");
      menu.setOnAction(new ComboBoxListener());
      menu.setVisible(false);

      Scene scene = new Scene(sp, 1368, 768);
      stage.setScene(scene);
      stage.setTitle("Dunegon Crawler");

      scene.setOnKeyPressed(new KeyListenerDown());
      scene.setOnKeyReleased(new KeyListenerUp());

      stage.show();
      sp.requestFocus();

      ta.start();
   } 

   public void drawBackground()
   {
      gc.setFill(Color.BLACK);
      gc.fillRect(0,0,theCanvas.getWidth(),theCanvas.getHeight());   
   }
   public void drawMenu()
      {
          gc.clearRect(0, 0, theCanvas.getWidth(), theCanvas.getHeight());
      
          // Draw the background image stretched to fit screen
          gc.drawImage(menuBackground, 0, 0, theCanvas.getWidth(), theCanvas.getHeight());
      
            startButton.setFont(Font.font("Arial", FontWeight.BOLD, 24));
            startButton.setLayoutX(350);
            startButton.setLayoutY(1000);

            startButton.setOnAction(e -> {
               startButton.setVisible(false);
               start = true;
               // call your game start method here
            });
      }

   public class AnimationHandler extends AnimationTimer
   {
      @Override
      public void handle(long now)
      {
         if(start == false)
         {
            drawMenu();
            if(startButton.isVisible() == false)
            {
               startButton.setVisible(true);
            }
         }
         else
         {  
            drawBackground();
            player.drawMe(player.getX(), player.getY(), gc);
            if (action || player.checkFlight()) {
                player.doThing(gc);
            }
            
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

         if (event.getCode() == KeyCode.H) start = true;
         if (event.getCode() == KeyCode.W) up = true;
         if (event.getCode() == KeyCode.A) left = true;
         if (event.getCode() == KeyCode.S) down = true;
         if (event.getCode() == KeyCode.D) right = true;
         if(event.getCode() == KeyCode.SPACE && !player.checkFlight() && !action)
         {
            action = true;
         }
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
         if(event.getCode() == KeyCode.SPACE) 
         {
            action = false;
         }
      }
   }

   public Color parseColor(String colorString) {
      try {
         return Color.web(colorString);
      } catch (IllegalArgumentException e) {
         return Color.BLACK;
      }
   }

   public class ComboBoxListener implements EventHandler<ActionEvent>
   {
      public void handle(ActionEvent e)
      {
         if(menu.getValue() == null)
         {
            menu.setValue("Menu");
            return;
         }

         switch(menu.getValue())
         {
            case "Save":
               // TODO: implement save logic
               break;

            case "Load":
               // TODO: implement load logic
               break;

            case "Reset":
               // TODO: implement reset logic
               break;

            case "Exit":
               System.exit(0);
               break;
         }
      }
   }
}