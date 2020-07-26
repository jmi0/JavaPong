/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author josephiannone
 */


import game.Game;
import java.io.IOException;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;



public class Main extends Application {
    
    Stage primaryStage;
    Game game;
    char humanPaddleSide = 'l';
   
   
    @Override
    public void start(Stage primaryStage) throws IOException {
        
        primaryStage.setTitle( "JavaPong" );
        
        VBox root = new VBox();
        //Parent root = FXMLLoader.load(getClass().getResource("/fxml/mainmenu.fxml"));
   
        Scene scene = new Scene(root, 800, 600);
        scene.getStylesheets().add("/css/mainmenu.css");
        primaryStage.setScene(scene);
        scene.setRoot(root);
        
        ToggleGroup leftRightOptGroup = new ToggleGroup();
        RadioButton leftOptButton = new RadioButton("Play left side of table");
        leftOptButton.setToggleGroup(leftRightOptGroup);
        leftOptButton.setSelected(true);
        RadioButton rightOptButton = new RadioButton("Play right side of table");
        rightOptButton.setToggleGroup(leftRightOptGroup);
        
        ToggleGroup difficultyOptions = new ToggleGroup();
        RadioButton easy = new RadioButton("Easy");
        easy.setToggleGroup(difficultyOptions);
        easy.setSelected(true);
        RadioButton medium = new RadioButton("Medium");
        medium.setToggleGroup(difficultyOptions);
        RadioButton hard = new RadioButton("Hard");
        hard.setToggleGroup(difficultyOptions);
        Button startGameButton = new Button("Start Game");
        
    
        primaryStage.addEventFilter(KeyEvent.KEY_PRESSED, new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                /**
                 * Come back to main menu if Q is pressed.
                 */
                if (event.getCode() == KeyCode.Q) {
                    primaryStage.setScene(scene);
                    scene.setRoot(root);
                }
            }
        });
        
        /**
         * Listen for start game button click and start game
         */
        startGameButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                
                if (rightOptButton.isSelected()) humanPaddleSide = 'r';
                game = new Game(primaryStage, humanPaddleSide);
            }
        });
        
        
        
        root.getChildren().addAll(leftOptButton, rightOptButton, easy, medium, hard, startGameButton);

        primaryStage.setResizable(false);
        primaryStage.show();
      
    }
    
    
    
    public static void main(String[] args) {
        launch(args);
    }
    
}
