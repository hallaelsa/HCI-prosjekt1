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
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 *
 * @author Miina
 */
public class backup extends Application {
    public int value = 0;
    public int collumn = 0;
    public int row = 0;
    public int counter = 0;
    public long startTime = 0;
    public long endTime = 0;
    public boolean rowDirection = true;
    public boolean isPressed = false;
    public boolean isReleased = false;
    public boolean started = false;
    public boolean changeRow = false;
    public boolean blockSelected = false;
    public boolean justSelected = false;
    public TextArea txt= new TextArea();
            
    public char[][] blockGrid = {
            {'A', 'B', 'C', 'D','E','F', 'G','H','I', 'J', 'K'},
            {'L', 'M','N','O', 'P','Q','R', 'S', 'T', 'U', 'V'},
            {'W','X', 'Y', 'Z', 'Æ','Ø','Å', ' ', '.', '1', '2'}
        };
    
    public AnimationTimer timer = new AnimationTimer() {
        @Override
        public void handle(long now) {
            if(!started)
                return;
            
            // change row/collumn direction when key is pressed
            if(!isReleased && !changeRow && !blockSelected){
                if(now-startTime > 400000000) {
                    // avoid it to keep changing row direction while key is pressed
                    changeRow = true;
                    rowDirection = !rowDirection;
                    System.out.println("rowDirection changed: " + rowDirection);
                } 
            }

            // select the current block or value on key release-and-wait
            if(!isPressed && (now - endTime) > 800000000) {
                select();
            }
            
            // go back to start when key is pressed and held when inside a block
            if(!isReleased && blockSelected && (now-startTime > 400000000)){
                started = false;
                resetValues();
            }
        }
    };
    
    @Override
    public void start(Stage stage) throws Exception {
        timer.start();
        //Parent root = FXMLLoader.load(getClass().getResource("FXMLDocument.fxml"));
        GridPane grid = new GridPane();
        VBox vbox = new VBox(txt, grid);
        Scene scene = new Scene(vbox, 400, 400);
        stage.setScene(scene);
        stage.show();
        
        // Key is pressed
        scene.addEventFilter(KeyEvent.KEY_PRESSED, event -> {
            isReleased = false;
            
            if(isPressed)
                return;
            
            isPressed = true;
            
            if (event.getCode() == KeyCode.SPACE) {
                startTime = System.nanoTime();
            }
        });
        
        
        
                
        // Key is released
        scene.setOnKeyReleased(e -> {
            isPressed = false;
            
            if(isReleased)
                return;

            changeRow = false;
            isReleased = true;
            if (e.getCode() == KeyCode.SPACE) {
                
                
                started = true;
                endTime = System.nanoTime();
                
                
                // avoid starting on second row when key is pressed for the first time
                if(counter == 0) {
                    counter++;
                    return;                
                }
                
                
                
                
    // check if we just entered a block
    // avoid starting at the next index after entering
                if(justSelected == true) {
                    value = 0;
                    justSelected = false;
                    return;
                }

                if(rowDirection == true && blockSelected == false) {
                    if(row == 2) {
                        row = 0;
                    } else {  
                        row++;
                    }
                } else if (rowDirection == false && blockSelected == false) {
                    if(collumn == 2) {
                        collumn = 0;
                    } else {
                        collumn++;
                    }
                } else {
                    // check if value is in bounds.....
                    
                    if(row == 0 && collumn == 0) {
                        if(value == 4) {
                            value = 0;
                        } else {
                            value++;
                        }
                    } else if (row == 1 && collumn == 2) {
                        if(value == 3) {
                            value = 0;
                        } else {
                            value++;
                        }
                    } else if (row == 2 && collumn == 1) {
                        if(value == 3) {
                            value = 0;
                        } else {
                            value++;                            
                        }
                    } else {
                        if(value == 2) {
                            value = 0;
                        } else {
                            value++;
                        }
                    }
                }
            
                counter++;
//                txt.setText(String.valueOf(blockGrid[collumn][row][value]));
//                System.out.println(" Collumn:" + collumn + " Row:" + row + " value:" + value);
            }
        });
        
        
        
      

    }
    
    public void next() {
        
    }
    
    public void down() {
        
    }

    public void select(){
        System.out.println("selected!!");
        started = false;
        
        if(blockSelected != true ) {
            blockSelected = true;
            justSelected = true;
        } else {
            txt.setText(String.valueOf(blockGrid[row][value]));
            System.out.println(" value:" + value);
            System.out.println("selected:" + blockGrid[row][value]);
            
            resetValues();
            
        }
    }
    
    public void resetValues() {
        counter = 0;
        collumn = 0;
        row = 0;
        value = 0;
        blockSelected = false;
        changeRow = false;
        rowDirection = true;
        justSelected = false;
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
