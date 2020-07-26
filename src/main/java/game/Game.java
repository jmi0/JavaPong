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
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;


public class Game {
    
    private Stage primaryStage;
    private AnimationTimer gameLoop;
    private Ball ball;
    private Scene scene;
    private Paddle p1;
    private Paddle p2;
    private Group root;
    private boolean inPlay = false;
    private int winningScore = 5;
    Text p1ScoreText;
    Text p2ScoreText;
    Text endMatchMessage;
    Text menuReplayMessage;
    VBox endMatchDisplay;
    
    
    public Game(Stage primaryStage, char humanPaddleSide) {
        
        root = new Group();
        scene = new Scene(root, Constants.STAGE_W, Constants.STAGE_H);
        scene.setFill(Color.BLACK);
        
        /**
         * Create court divider
         */
        Line courtDivider = new Line(scene.getWidth()/2, 0, scene.getWidth()/2, scene.getHeight());
        courtDivider.getStrokeDashArray().addAll(2d);
        courtDivider.setStroke(Color.WHITE);
        
        /**
        * Create Pong Paddles
        */
        double humanPaddleX = 20;
        double cpuPaddleX = scene.getWidth() - 20 - Paddle.PADDLE_W;
        char cpuPaddleSide = 'r';
        if (humanPaddleSide == 'r') {
            cpuPaddleSide = 'l';
            humanPaddleX = scene.getWidth() - 20 - Paddle.PADDLE_W;
            cpuPaddleX = 20;
        }
        p1 = new Paddle(humanPaddleX, Constants.STAGE_H/2 - Paddle.PADDLE_H/2, Color.WHITE, true, humanPaddleSide);
        p2 = new Paddle(cpuPaddleX, Constants.STAGE_H/2 - Paddle.PADDLE_H/2, Color.WHITE, false, cpuPaddleSide);
        
        /**
         * Create text display for score
         */
        p1ScoreText = new Text(Constants.STAGE_W/4, Constants.STAGE_H/6, Integer.toString(p1.points));
        p2ScoreText = new Text((Constants.STAGE_W/4)*3, Constants.STAGE_H/6, Integer.toString(p2.points));
        p1ScoreText.setFill(Color.GRAY);
        p1ScoreText.setFont(Font.font ("Verdana", Constants.STAGE_H/8));
        p2ScoreText.setFill(Color.GRAY);
        p2ScoreText.setFont(Font.font ("Verdana", Constants.STAGE_H/8));
        
     
        
        /**
         * Create ball
         */
        ball = new Ball(Constants.STAGE_W/2, (Math.random() * ((Constants.STAGE_H - 0) + 1)) + 0, Color.WHITE, Constants.BALL_RADIUS);
        
        
        if (p1.isHuman) {
            EventHandler<MouseEvent> mouseHandler = new EventHandler<MouseEvent>() { 
                @Override 
                public void handle(MouseEvent e) { 
                    p1.setY(e.getY() - p1.PADDLE_H/2);
                }
            };   
            //Adding event Filter 
            scene.addEventFilter(MouseEvent.MOUSE_MOVED, mouseHandler);
        }
        
        
        primaryStage.addEventFilter(KeyEvent.KEY_PRESSED, new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                /**
                 * Replay
                 */
                if (event.getCode() == KeyCode.R) {
                    p1.points = 0;
                    p1ScoreText.setText(Integer.toString(p1.points));
                    p2.points = 0;
                    p2ScoreText.setText(Integer.toString(p2.points));
                    root.getChildren().remove(endMatchDisplay);
                    
                    ball = new Ball(Constants.STAGE_W/2, (Math.random() * ((Constants.STAGE_H - 0) + 1)) + 0, Color.WHITE, Constants.BALL_RADIUS);
                    root.getChildren().add(ball);
                    startMatch();
                }
            }
        });
        
        
        
        
        root.getChildren().addAll(p1, p2, ball, courtDivider, p1ScoreText, p2ScoreText);
        primaryStage.setScene(scene);
        startMatch();
        inPlay = true;
        
       
        
    }
    
    /**
     * Check for x collision (ball and paddle)
     * @return 
     */
    private boolean xCollision() {
        
        if (ball.getBoundsInParent().intersects(p1.getBoundsInParent())) return true;
        
        else if (ball.getBoundsInParent().intersects(p2.getBoundsInParent())) return true;
        
        return false;
    
    }
    
    /**
     * Check for y collision (ball and wall)
     * @return 
     */
    private boolean yCollision() {
        
        if (ball.getCenterY() + ball.getRadius() >= scene.getHeight()) return true;
        
        else if (ball.getCenterY() - ball.getRadius() <= 0) return true;
      
        return false;
        
    }
    
    private boolean checkForScore(Paddle paddle) {
        
      if (paddle.xScoreBoundary == 0 && ball.getCenterX() <= paddle.xScoreBoundary) return true;
      
      else if (paddle.xScoreBoundary > 0 && ball.getCenterX() >= paddle.xScoreBoundary) return true;
    
      return false;
      
    }
    
    private void cpuPaddleControl() {
        /**
         * dev implementation
         */
        
        /**
         * Only if ball is moving toward paddle 2s side of court
         */
        if (ball.xVelocityIncreasing) {
            /**
             * move paddle up if ball is moving up and paddle is below ball
             */
            if (ball.yVelocityIncreasing && p2.getY() < Constants.STAGE_H && p2.getY() < ball.getCenterY()) {
                p2.setY(p2.getY() + 4);
            }
            /**
             * move paddle down if ball is moving down and paddle is above ball
             */
            else if (p2.getY() > 0 && p2.getY() > ball.getCenterY()) {
                p2.setY(p2.getY() - 4);
            }
        }
    }
    
    private void endMatchMessage(String message) {
        endMatchMessage = new Text(Constants.STAGE_W/2, Constants.STAGE_H/2, message);
        endMatchMessage.setFill(Color.GRAY);
        endMatchMessage.setFont(Font.font ("Verdana", Constants.STAGE_H/12));
        menuReplayMessage = new Text("Press 'q' to return to the main menu or 'r' to replay.");
        menuReplayMessage.setFill(Color.GRAY);
        menuReplayMessage.setFont(Font.font ("Verdana", Constants.STAGE_H/24));
        endMatchDisplay = new VBox(endMatchMessage, menuReplayMessage);
        System.out.println(endMatchDisplay.getHeight());
        endMatchDisplay.setLayoutX((Constants.STAGE_W/2) - 300);
        endMatchDisplay.setLayoutY((Constants.STAGE_H/2) - 100);

        root.getChildren().add(endMatchDisplay);

    }
    
    private void startMatch() {
        // start game
        gameLoop = new AnimationTimer() {
            
            @Override
            public void handle(long now) {
                /**
                 * Check for collisions and update velocity
                 */
                if(xCollision()) ball.changeXVelocity();
                if(yCollision()) ball.changeYVelocity();
                
                
              
                /**
                 * check for paddle 1 score
                 */
                if (checkForScore(p1)) {
                    //inPlay = false;
                    p1.points += 1;
                    p1ScoreText.setText(Integer.toString(p1.points));
                    root.getChildren().remove(ball);
                    if (p1.points >= winningScore) {
                        endMatchMessage("Player 1 Wins");
                        this.stop();
                        
                    } else {
                        ball = new Ball(Constants.STAGE_W/2, (Math.random() * ((Constants.STAGE_H - 0) + 1)) + 0, Color.WHITE, Constants.BALL_RADIUS);
                        root.getChildren().add(ball);
                    }   
                }    
                /**
                 * check for paddle 2 score
                 */
                else if (checkForScore(p2)) {
                    //inPlay = false;
                    p2.points += 1;
                    p2ScoreText.setText(Integer.toString(p2.points));
                    root.getChildren().remove(ball);
                    if (p2.points >= winningScore) {
                        endMatchMessage("Player 2 Wins");
                        this.stop();
                    } else {
                        ball = new Ball(Constants.STAGE_W/2, (Math.random() * ((Constants.STAGE_H - 0) + 1)) + 0, Color.WHITE, Constants.BALL_RADIUS);
                        root.getChildren().add(ball);
                    }
                } 
                /**
                 * move ball
                 */
                else {
                    cpuPaddleControl();
                    ball.updateXVelocity(6);
                    ball.updateYVelocity(3);
                }
                
            }

         
        };
        gameLoop.start();
    }
    
}
