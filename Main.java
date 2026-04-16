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

import java.util.*;

public class Main extends Application
{
   boolean start, up, down, left, right;

   ComboBox<String> menu = new ComboBox<>();
   StackPane sp = new StackPane();
   Canvas theCanvas = new Canvas(1368,768);
   GraphicsContext gc = theCanvas.getGraphicsContext2D(); 

   AnimationHandler ta = new AnimationHandler();

   public void start(Stage stage)
   {
      sp.getChildren().add(theCanvas);
      sp.getChildren().add(menu);

      menu.getItems().addAll("Save", "Load", "Reset", "Exit");
      menu.setOnAction(new ComboBoxListener());
      menu.setVisible(false);

      Scene scene = new Scene(sp, 1368, 768);
      stage.setScene(scene);
      stage.setTitle("Contraption Zac");

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

   public class AnimationHandler extends AnimationTimer
   {
      @Override
      public void handle(long now)
      {
         drawBackground();
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