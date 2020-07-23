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
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.stage.Stage;



public class Main extends Application {
    
    Stage primaryStage;
    Game game;
    char humanPaddleSide = 'l';
   
   
    @Override
    public void start(Stage primaryStage) {
        
        primaryStage.setTitle( "JavaFXPong" );
        
        game = new Game(primaryStage, humanPaddleSide);
        
        
    }
    
    
    public static void main(String[] args) {
        launch(args);
    }
    
}
