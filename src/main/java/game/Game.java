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
    Paddle p1;
    Paddle p2;
    
    
    public Game(Stage primaryStage, char humanPaddleSide) {
        
     
        Group root = new Group();
        Scene scene = new Scene(root, Constants.STAGE_W, Constants.STAGE_H);
        primaryStage.setScene(scene);
        
         
        Canvas canvas = new Canvas(Constants.STAGE_W, Constants.STAGE_H);
        root.getChildren().add( canvas );
        
        /**
        * Create Pong Paddles
        */
        int humanPaddleX = 20;
        int cpuPaddleX = Constants.STAGE_W - 20;
        char cpuPaddleSide = 'r';
        if (humanPaddleSide == 'r') {
            cpuPaddleSide = 'l';
            humanPaddleX = Constants.STAGE_W - 20;
            cpuPaddleX = 20;
        }
        p1 = new Paddle(humanPaddleX, Constants.STAGE_H/2 - Paddle.PADDLE_H/2, Color.BLACK, true, humanPaddleSide);
        root.getChildren().add(p1);
        
        p2 = new Paddle(cpuPaddleX, Constants.STAGE_H/2 - Paddle.PADDLE_H/2, Color.BLUE, false, cpuPaddleSide);
        root.getChildren().add(p2);
        
        /**
         * Create ball
         */
        
        ball = new Ball(Constants.STAGE_W/2, Constants.STAGE_H/2, Color.BLACK);
        root.getChildren().add(ball);
        
        this.primaryStage = primaryStage;
       
        if (p1.isHuman) {
            EventHandler<MouseEvent> mouseHandler = new EventHandler<MouseEvent>() { 
                @Override 
                public void handle(MouseEvent e) { 
                    //System.out.println("mouse moved");
                    p1.setY(e.getY() - p1.PADDLE_H/2);
                }
            };   
            //Adding event Filter 
            root.addEventFilter(MouseEvent.MOUSE_MOVED, mouseHandler);
        }
        
        // start game
        gameLoop = new AnimationTimer() {
            
            @Override
            public void handle(long now) {
                System.out.println(ball.getCenterY());
                System.out.println(ball.getCenterX());
                if(collision()) {
                    System.out.println("collision");
                    ball.changeXVelocity();
                }
                
                ball.updateXVelocity(2);
                ball.updateYVelocity(.4);
                
            }

         
        };

        gameLoop.start();
        
       
        
    }
    
    private boolean collision() {
        
        if (ball.getBoundsInParent().intersects(p1.getBoundsInParent())) {
            return true;
        }
        
        if (ball.getBoundsInParent().intersects(p2.getBoundsInParent())) {
            return true;
        }
        
        return false;
    }
    
}
