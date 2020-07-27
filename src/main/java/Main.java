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
import java.io.File;
import java.io.IOException;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.VBox;
import javafx.scene.media.AudioClip;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;



public class Main extends Application {
    
    private Stage primaryStage;
    private Game game;
    private char paddleSelection;
    private int difficultySelection;
    
    AudioClip menumusic = new AudioClip(new File(getClass().getResource("/sounds/menumusic.wav").getPath()).toURI().toString());
    
   
    @Override
    public void start(Stage primaryStage) throws IOException {
        
        primaryStage.setTitle( "JavaPong" );
        
        VBox root = new VBox();
   
        Scene scene = new Scene(root, 800, 600);
        scene.getStylesheets().add("/css/mainmenu.css");
        primaryStage.setScene(scene);
        scene.setRoot(root);
        
        Text header1 = new Text("JavaPong");
        Text header2 = new Text("A pong implementation inspired by the classic Atari arcade game. Built in Java using the JavaFX API.");
        header1.getStyleClass().add("header1");
        header2.getStyleClass().add("header2");
        header1.setFill(Color.ANTIQUEWHITE);
        header2.setFill(Color.ANTIQUEWHITE);
        header2.maxWidth(500);
        header2.setWrappingWidth(500);
        
        ToggleGroup paddleOptions = new ToggleGroup();
        RadioButton leftOptButton = new RadioButton("Play left side of screen");
        leftOptButton.setToggleGroup(paddleOptions);
        leftOptButton.setSelected(true);
        RadioButton rightOptButton = new RadioButton("Play right side of screen");
        rightOptButton.setToggleGroup(paddleOptions);
        RadioButton neitherOptButton = new RadioButton("Neither (Watch the computer play itself.)");
        neitherOptButton.setToggleGroup(paddleOptions);
        
        
        ToggleGroup difficultyOptions = new ToggleGroup();
        RadioButton easy = new RadioButton("Easy");
        easy.setToggleGroup(difficultyOptions);
        easy.setSelected(true);
        RadioButton medium = new RadioButton("Medium");
        medium.setToggleGroup(difficultyOptions);
        RadioButton hard = new RadioButton("Hard");
        hard.setToggleGroup(difficultyOptions);
        Button startGameButton = new Button("Start Game");
        
        startGameButton.setCursor(Cursor.HAND);
        
        root.setSpacing(30);
        
        
        /**
         * Listen for q key to return to Main Menu
         */
        primaryStage.addEventFilter(KeyEvent.KEY_PRESSED, new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                /**
                 * Come back to main menu if Q is pressed.
                 */
                if (event.getCode() == KeyCode.Q) {
                    game = null;
                    menumusic.play();
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
                /**
                 * determine paddle selection
                 */
                if (leftOptButton.isSelected()) paddleSelection = 'l';
                else if (rightOptButton.isSelected()) paddleSelection = 'r';
                else paddleSelection = 'n';
                
                /**
                 * Determine difficulty selection
                 */
                if (easy.isSelected()) difficultySelection = 1;
                else if (medium.isSelected()) difficultySelection = 2;
                else difficultySelection = 3;
                menumusic.stop();
                
                /**
                 * init game
                 */
                game = new Game(primaryStage, paddleSelection, difficultySelection);
                /**
                 * start game
                 */
                game.startMatch();
            }
        });
        
        
        /**
         * Add elements to root (will add difficulty elements when feature is ready)
         */
        root.getChildren().addAll(header1, header2, leftOptButton, rightOptButton, neitherOptButton, startGameButton);
        menumusic.setCycleCount(AudioClip.INDEFINITE);
        menumusic.play();
        primaryStage.setResizable(false);
        primaryStage.show();
      
    }
    
    
    
    public static void main(String[] args) {
        launch(args);
    }
    
}
