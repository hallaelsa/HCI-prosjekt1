/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hcigul;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 *
 * @author Miina
 */
public class HciGul extends Application implements PressHandlerListener, KeyboardHandlerListener {
    private PressHandler pressHandler = new PressHandler();
    private KeyboardHandler keyboardHandler = new KeyboardHandler();
    private String word = "";

    public TextArea txt= new TextArea();
    public TextArea output = new TextArea();
    public TextArea explaination = new TextArea();
    
    
    private AnimationTimer timer = new AnimationTimer() {
        @Override
        public void handle(long now) {
            String result = "";
            String result2 = "";
            
            for (int i = 0; i < keyboardHandler.keys.length; i++) {
                for (int j = 0; j < keyboardHandler.keys[i].length; j++) {
                    
                    if (i == keyboardHandler.currentRow && j == keyboardHandler.currentKey)
                        result += "[" + keyboardHandler.keys[i][j] + "]";
                        
                    else
                        result += " " + keyboardHandler.keys[i][j] + " ";
                }
                result += "\r\n";
            }
            
            result2 +=  word;
            txt.setText(result);
            output.setText("Din tekst:   " + result2);
        }
    };
    
    @Override
    public void start(Stage stage) throws Exception {
        txt.setEditable(false);
        output.setEditable(false);
        explaination.setEditable(false);
        explaination.setText("Click to move towards left \n"
                + "Double click to choose a letter \n"
                + "Press and hold to move one row down");
        explaination.setPrefRowCount(10);
        explaination.setStyle("-fx-color: orangered;"
                + "-fx-faint-focus-color: transparent;"
                + "-fx-focus-color: transparent;");
        txt.setStyle("-fx-font-family: monospace;" 
                + "-fx-background-color: orangered;"
                + "-fx-font-size: 30;"
                + "-fx-faint-focus-color: transparent;"
                + "-fx-focus-color: transparent;");
        output.setStyle("-fx-background-color: orangered;"
                + "-fx-font-size: 30;"
                + "-fx-faint-focus-color: transparent;"
                + "-fx-focus-color: transparent;");
        
        
        GridPane grid = new GridPane();
        VBox vbox = new VBox(explaination,txt, output);
        Scene scene = new Scene(vbox, 700, 500);
        
        stage.setScene(scene);
        stage.show();
        timer.start();
        
        pressHandler.addListener(this);
        keyboardHandler.addListener(this);
        
        // Key is pressed
        scene.addEventFilter(KeyEvent.KEY_PRESSED, event -> {
            if (event.getCode() == KeyCode.SPACE)
                pressHandler.Pressed();
        });
               
        // Key is released
        scene.setOnKeyReleased(e -> {
            if (e.getCode() == KeyCode.SPACE)
                pressHandler.Released();
        });
    }
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void onSingleClick() {
        keyboardHandler.next();
    }

    @Override
    public void onDoubleClick() {
        keyboardHandler.select();
    }

    @Override
    public void onLongPress() {
        keyboardHandler.down();
    }

    @Override
    public void onKeySelect(String key) {
        if(key != "Delete" && key != "Delete All")
            word += key;
    }

    @Override
    public void onKeyDelete(String key) {
        if(key == "Delete")
            word = word.substring(0, word.length()-1);
        
        if(key == "Delete All")
            word = "";
    }
    
}
