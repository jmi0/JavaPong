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

import javafx.animation.AnimationTimer;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.stage.Stage;


public class Game {
    
    private Stage primaryStage;
    private AnimationTimer gameLoop;
    private Ball ball;
    Canvas canvas;
    Paddle p1;
    Paddle p2;
    
    
    public Game(Stage primaryStage, char humanPaddleSide) {
        
     
        Group root = new Group();
        Scene scene = new Scene(root, Constants.STAGE_W, Constants.STAGE_H);
        scene.setFill(Color.BLACK);
        primaryStage.setScene(scene);
        
         
        canvas = new Canvas(Constants.STAGE_W, Constants.STAGE_H);
        root.getChildren().add( canvas );
        
        /**
        * Create Pong Paddles
        */
        double humanPaddleX = 20;
        double cpuPaddleX = canvas.getWidth() - 20 - Paddle.PADDLE_W;
        char cpuPaddleSide = 'r';
        if (humanPaddleSide == 'r') {
            cpuPaddleSide = 'l';
            humanPaddleX = canvas.getWidth() - 20 - Paddle.PADDLE_W;
            cpuPaddleX = 20;
        }
        p1 = new Paddle(humanPaddleX, Constants.STAGE_H/2 - Paddle.PADDLE_H/2, Color.WHITE, true, humanPaddleSide);
        root.getChildren().add(p1);
        
        p2 = new Paddle(cpuPaddleX, Constants.STAGE_H/2 - Paddle.PADDLE_H/2, Color.WHITE, false, cpuPaddleSide);
        root.getChildren().add(p2);
        
        /**
         * Create ball
         */
        
        ball = new Ball(Constants.STAGE_W/2, Constants.STAGE_H/2, Color.WHITE, Constants.BALL_RADIUS);
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
                if(xCollision()) {
                    System.out.println("x collision");
                    ball.changeXVelocity();
                }
                if(yCollision()) {
                    System.out.println("y collision");
                    ball.changeYVelocity();
                }
                
                
                ball.updateXVelocity(4);
                ball.updateYVelocity(1);
                
            }

         
        };

        gameLoop.start();
        
       
        
    }
    
    /**
     * Check for x collision (ball and paddle)
     * @return 
     */
    private boolean xCollision() {
        
        if (ball.getBoundsInParent().intersects(p1.getBoundsInParent())) return true;
        
        if (ball.getBoundsInParent().intersects(p2.getBoundsInParent())) return true;
        
        return false;
    
    }
    
    /**
     * Check for y collision (ball and wall)
     * @return 
     */
    private boolean yCollision() {
        
        if (ball.getCenterY() + ball.getRadius() >= canvas.getHeight()) return true;
        
        if (ball.getCenterY() - ball.getRadius() <= 0) return true;
      
        return false;
        
    }
    
    private void checkForScore() {
        
      
    }
    
}
