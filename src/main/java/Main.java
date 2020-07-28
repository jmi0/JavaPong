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


/**
 * Initialize Stage, display menu, initialize game, create event filters and 
 * button listeners for navigation between game and menu
 * 
 * @author josephiannone
 */
public class Main extends Application {
    
    private Stage primaryStage;
    private Game game;
    private char paddleSelection;
    private int difficultySelection;
    
    /**
     * initialize audio for opening menu
     */
    AudioClip menumusic = new AudioClip(
            new File(getClass().getResource("/sounds/menumusic.wav").getPath()).toURI().toString());
    
   
    @Override
    public void start(Stage primaryStage) throws IOException {
        
        primaryStage.setTitle( "JavaPong" );
        
        VBox root = new VBox();
        root.setSpacing(30);
        
        Scene scene = new Scene(root, 800, 600);
        scene.getStylesheets().add("/css/mainmenu.css");
        scene.setRoot(root);
        
        primaryStage.setScene(scene);
        
        
        /**
         * Text Headers for opening screen
         */
        Text header1 = new Text("JavaPong");
        Text header2 = new Text("A pong implementation inspired by the classic Atari arcade game. Built in Java using the JavaFX API.");
        header1.getStyleClass().add("header1");
        header2.getStyleClass().add("header2");
        header1.setFill(Color.ANTIQUEWHITE);
        header2.setFill(Color.ANTIQUEWHITE);
        header2.maxWidth(500);
        header2.setWrappingWidth(500);
        
        /**
         * Paddle option radio buttons
         */
        ToggleGroup paddleOptions = new ToggleGroup();
        RadioButton leftOptButton = new RadioButton("Play left side of screen");
        leftOptButton.setToggleGroup(paddleOptions);
        leftOptButton.setSelected(true);
        RadioButton rightOptButton = new RadioButton("Play right side of screen");
        rightOptButton.setToggleGroup(paddleOptions);
        RadioButton neitherOptButton = new RadioButton("Neither (Watch the computer play itself.)");
        neitherOptButton.setToggleGroup(paddleOptions);
        
        /**
         * Difficulty option radio buttons
         * TODO: still need to implement this feature in game package
         * so omitting from scene for now
         */
        ToggleGroup difficultyOptions = new ToggleGroup();
        RadioButton easy = new RadioButton("Easy");
        easy.setToggleGroup(difficultyOptions);
        easy.setSelected(true);
        RadioButton medium = new RadioButton("Medium");
        medium.setToggleGroup(difficultyOptions);
        RadioButton hard = new RadioButton("Hard");
        hard.setToggleGroup(difficultyOptions);
        
        /**
         * Start game button
         */
        Button startGameButton = new Button("Start Game");    
        startGameButton.setCursor(Cursor.HAND);
        
         
        /**
         * Add elements to root (will add difficulty elements when feature is ready)
         */
        root.getChildren().addAll(header1, header2, leftOptButton, rightOptButton, neitherOptButton, startGameButton);
        menumusic.setCycleCount(AudioClip.INDEFINITE);
        menumusic.play();
        primaryStage.setResizable(false);
        primaryStage.show();
        
        
        /**
         * Listen for q key to return to Main Menu
         */
        primaryStage.addEventFilter(KeyEvent.KEY_PRESSED, new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                /**
                 * Reset scene and game (Come back to main menu if Q is pressed.)
                 */
                if (event.getCode() == KeyCode.Q) {
                    game = null;
                    menumusic.stop();
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
        
      
    }
    
    
    public static void main(String[] args) {
        launch(args);
    }
    
}
