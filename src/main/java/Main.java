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
import javafx.geometry.Insets;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;


public class Main extends Application {
    
    Stage primaryStage;
    Game currentGame; 
    VBox root;
   
    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        this.root = new VBox();
        root.setAlignment(Pos.CENTER);
        root.setSpacing(10);
	root.setPadding(new Insets(10, 10, 10, 10));
        
        Scene scene = new Scene(root, 1000, 800);
        //scene.getStylesheets().add(Styles.getStylesheet());
	primaryStage.setScene(scene);
	primaryStage.setResizable(false);
		
	primaryStage.show();
    }
    
    
    public static void main(String[] args) {
        launch(args);
    }
    
}
