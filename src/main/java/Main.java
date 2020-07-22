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
import javafx.scene.control.Label;
import javafx.geometry.Insets;
import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
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
        
        HBox settings = new HBox();
        settings.setSpacing(20);
        settings.setAlignment(Pos.CENTER);
        
        final CheckBox rightMouseControl = new CheckBox();
	rightMouseControl.setGraphic(new Label("Right Paddle"));
	final CheckBox leftMouseControl = new CheckBox();
	leftMouseControl.setGraphic(new Label("Left Paddle"));
        
        leftMouseControl.selectedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                if (newValue) {
                    rightMouseControl.setSelected(false);
                } 
            }
	});
			
	rightMouseControl.selectedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                if (newValue) {
                    leftMouseControl.setSelected(false);
                } 
            }
	});
        
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
