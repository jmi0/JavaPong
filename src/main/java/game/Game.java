/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package game;

/**
 *
 * @author josephiannone
 */

import javafx.animation.Animation;
import javafx.animation.AnimationTimer;
import javafx.animation.KeyFrame;
import javafx.animation.ParallelTransition;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Cursor;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

import javafx.util.Duration;

public class Game {
    
    private Stage primaryStage;
    private AnimationTimer gameLoop;
    private Ball ball;

  
    
    public Game(Stage primaryStage, char humanPaddleSide) {
        
     
        Group root = new Group();
        Scene scene = new Scene( root );
        scene.setCursor(Cursor.OPEN_HAND);
        primaryStage.setScene( scene );
        
         
        Canvas canvas = new Canvas(Constants.STAGE_W, Constants.STAGE_H);
        root.getChildren().add( canvas );
        
        /**
        * Create Pong Paddles
        */
        int humanPaddleX = 20;
        int cpuPaddleX = Constants.STAGE_W - 20;
        if (humanPaddleSide == 'r') {
            humanPaddleX = Constants.STAGE_W - 20;
            cpuPaddleX = 20;
        }
        Paddle humanPaddle = new Paddle(humanPaddleX, Constants.STAGE_H/2 - Paddle.PADDLE_H/2, Color.BLACK, true);
        root.getChildren().add(humanPaddle);
        
        Paddle cpuPaddle = new Paddle(cpuPaddleX, Constants.STAGE_H/2 - Paddle.PADDLE_H/2, Color.BLUE, false);
        root.getChildren().add(cpuPaddle);
        
        /**
         * Create ball
         */
        
        ball = new Ball(Constants.STAGE_W/2, Constants.STAGE_H/2, Color.BLACK);
        root.getChildren().add(ball);
        
        this.primaryStage = primaryStage;
        
        
        EventHandler<MouseEvent> mouseHandler = new EventHandler<MouseEvent>() { 
            @Override 
            public void handle(MouseEvent e) { 
                System.out.println("mouse moved");
                humanPaddle.setY(e.getY() - Paddle.PADDLE_H/2);
            }
        };   
        //Adding event Filter 
        root.addEventFilter(MouseEvent.MOUSE_MOVED, mouseHandler);
        
        // start game
        gameLoop = new AnimationTimer() {
            
            @Override
            public void handle(long now) {
                System.out.println(humanPaddle.getY());
                ball.changeXVelocity(ball.xVelocity + .1);
                ball.changeYVelocity(ball.yVelocity + .1);
            }
        };

        gameLoop.start();
        
        primaryStage.setResizable(false);
        primaryStage.show();
        
    }
    
}
