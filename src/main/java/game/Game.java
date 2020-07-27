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

import java.io.File;
import java.util.Random;
import javafx.animation.AnimationTimer;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.media.AudioClip;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import javafx.util.Duration;


public class Game {
    
    private AnimationTimer gameLoop;
    private Ball ball;
    private Scene scene;
    private Paddle leftPaddle;
    private Paddle rightPaddle;
    private Group root;
    private int winningScore = 5;
    
    Text p1ScoreText;
    Text p2ScoreText;
    Text endMatchMessage;
    Text menuReplayMessage;
    VBox endMatchDisplay;
    
    AudioClip blip1 = new AudioClip(new File(getClass().getResource("/sounds/pongblip1.wav").getPath()).toURI().toString());
    AudioClip blip2 = new AudioClip(new File(getClass().getResource("/sounds/pongblip2.wav").getPath()).toURI().toString());
    AudioClip blipPoint = new AudioClip(new File(getClass().getResource("/sounds/score.wav").getPath()).toURI().toString());
    AudioClip blipWin = new AudioClip(new File(getClass().getResource("/sounds/win.wav").getPath()).toURI().toString());
    
    
    public Game(Stage primaryStage, char paddleSelection, int difficultySelection) {        
        
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
        boolean leftPaddleIsHuman = false;
        boolean rightPaddleIsHuman = false;
        if (paddleSelection == 'l') leftPaddleIsHuman = true;
        if (paddleSelection == 'r') rightPaddleIsHuman = true;
        
        leftPaddle = new Paddle(20, Constants.STAGE_H/2 - Paddle.PADDLE_H/2, Color.WHITE, Constants.STAGE_W, leftPaddleIsHuman);
        rightPaddle = new Paddle((scene.getWidth() - 20 - Paddle.PADDLE_W), Constants.STAGE_H/2 - Paddle.PADDLE_H/2, Color.WHITE, 0, rightPaddleIsHuman);
        
        
        
        /**
         * Create text display for score
         */
        p1ScoreText = new Text(Constants.STAGE_W/4, Constants.STAGE_H/6, Integer.toString(leftPaddle.points));
        p2ScoreText = new Text((Constants.STAGE_W/4)*3, Constants.STAGE_H/6, Integer.toString(rightPaddle.points));
        p1ScoreText.setFill(Color.GRAY);
        p1ScoreText.setFont(Font.font ("Helvetica", Constants.STAGE_H/8));
        p2ScoreText.setFill(Color.GRAY);
        p2ScoreText.setFont(Font.font ("Helvetica", Constants.STAGE_H/8));
        
     
        
        /**
         * Create ball
         */
        ball = new Ball(Constants.STAGE_W/2, randomDouble(0, Constants.STAGE_H), 6, randomDouble(3.0, 4.0), Constants.BALL_RADIUS, Color.WHITE);
        
        
        /**
         * Event handler for match replay
         */
        primaryStage.addEventFilter(KeyEvent.KEY_PRESSED, new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                /**
                 * Replay
                 */
                if (event.getCode() == KeyCode.R) {
                    
                    gameLoop.stop();
                    root.getChildren().remove(ball);
                    leftPaddle.points = 0;
                    p1ScoreText.setText(Integer.toString(leftPaddle.points));
                    rightPaddle.points = 0;
                    p2ScoreText.setText(Integer.toString(rightPaddle.points));
                    root.getChildren().remove(endMatchDisplay);
                    gameLoop = null;
                    resetBall();
                    startMatch();
                }
            }
        });
        
        /**
         * Listen for q key to return to stop match before going to main menu
         */
        primaryStage.addEventFilter(KeyEvent.KEY_PRESSED, new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                /**
                 * Come back to main menu if Q is pressed.
                 */
                if (event.getCode() == KeyCode.Q) {
                    gameLoop.stop();
                    root.getChildren().remove(ball);
                    leftPaddle.points = 0;
                    rightPaddle.points = 0;
                }
            }
        });
        
        
        
        /**
         * Add all objects to group/scene
         */
        root.getChildren().addAll(leftPaddle, rightPaddle, ball, courtDivider, p1ScoreText, p2ScoreText);
        primaryStage.setScene(scene);
        
        
        /**
         * start game
         */
        startMatch();
        
        
    }
    
    
    /**
     * Check for x collision (ball and paddle)
     * @return 
     */
    private boolean xCollision() {
        
        if (ball.getBoundsInParent().intersects(leftPaddle.getBoundsInParent())) return true;
        
        else if (ball.getBoundsInParent().intersects(rightPaddle.getBoundsInParent())) return true;
        
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
    
    /**
     * A method to handle automated paddle control
     * @param paddle 
     */
    private void cpuPaddleControl(Paddle paddle) {
        
        /**
         * Only if the ball is moving toward the paddle
         */
        if ((paddle.getX() < scene.getWidth() / 2 && !ball.xVelocityIncreasing) || (paddle.getX() > scene.getWidth() / 2 && ball.xVelocityIncreasing)) {
            /**
             * move paddle up if ball is moving up and paddle is below ball
             */
            if (ball.yVelocityIncreasing && paddle.getY() < Constants.STAGE_H && paddle.getY() < ball.getCenterY()) {
                paddle.setY(paddle.getY() + 4);
            }
            /**
             * move paddle down if ball is moving down and paddle is above ball
             */
            else if (paddle.getY() > 0 && paddle.getY() > ball.getCenterY()) {
                paddle.setY(paddle.getY() - 4);
            }
        }
        
    }
    
    
    /**
     * A method to display the match results and menu options 
     * @param message 
     */
    private void endMatchMessage(String message) {
        
        endMatchMessage = new Text(Constants.STAGE_W/2, Constants.STAGE_H/2, message);
        endMatchMessage.setFill(Color.GRAY);
        endMatchMessage.setFont(Font.font ("Verdana", Constants.STAGE_H/12));
        endMatchMessage.setTextAlignment(TextAlignment.CENTER);
        menuReplayMessage = new Text("Press \"q\" to return to the main menu or \"r\" to replay.");
        menuReplayMessage.setFill(Color.WHITE);
        menuReplayMessage.setFont(Font.font ("Verdana", Constants.STAGE_H/32));
        menuReplayMessage.setTextAlignment(TextAlignment.CENTER);
        endMatchDisplay = new VBox(endMatchMessage, menuReplayMessage);
        System.out.println(endMatchDisplay.getHeight());
        endMatchDisplay.setLayoutX((Constants.STAGE_W/2) - 240);
        //endMatchDisplay.setMinWidth(Constants.STAGE_W);
        endMatchDisplay.setLayoutY((Constants.STAGE_H/2) - 140);
        root.getChildren().add(endMatchDisplay);

    }
    
    
    private void startMatch() {
        
        /**
         * Adding event Filter(s) for mouse control
         */
        if (leftPaddle.isHuman) scene.addEventFilter(MouseEvent.MOUSE_MOVED, leftPaddle.mouseHandler);
        if (rightPaddle.isHuman) scene.addEventFilter(MouseEvent.MOUSE_MOVED, rightPaddle.mouseHandler);
        
        // start game
        gameLoop = new AnimationTimer() {
            
            @Override
            public void handle(long now) {
                
                /**
                 * Check for collisions and update velocity
                 */
                if(xCollision()) {
                    ball.reverseXVelocity();
                    blip1.play();     
                }
                if(yCollision()) {
                    ball.reverseYVelocity();
                    blip2.play();
                }
                
                
                if (checkForScore(leftPaddle)) {
                    
                    /**
                     * check for paddle 1 score
                     */
                    blipPoint.play();
                    leftPaddle.points += 1;
                    p1ScoreText.setText(Integer.toString(leftPaddle.points));
                    root.getChildren().remove(ball);
                    if (leftPaddle.points >= winningScore) {
                        if (leftPaddle.isHuman) scene.removeEventFilter(MouseEvent.MOUSE_MOVED, leftPaddle.mouseHandler);
                        if (rightPaddle.isHuman) scene.removeEventFilter(MouseEvent.MOUSE_MOVED, rightPaddle.mouseHandler);
                        endMatchMessage("Left Paddle Wins!");
                        blipWin.play();
                        this.stop();
                        
                    } else {
                        resetBall();
                    }   
                }
                
                
                else if (checkForScore(rightPaddle)) {
                    
                    /**
                     * check for paddle 2 score
                     */
                    blipPoint.play();
                    rightPaddle.points += 1;
                    p2ScoreText.setText(Integer.toString(rightPaddle.points));
                    root.getChildren().remove(ball);
                    if (rightPaddle.points >= winningScore) {
                        if (leftPaddle.isHuman) scene.removeEventFilter(MouseEvent.MOUSE_MOVED, leftPaddle.mouseHandler);
                        if (rightPaddle.isHuman) scene.removeEventFilter(MouseEvent.MOUSE_MOVED, rightPaddle.mouseHandler);
                        endMatchMessage("Right Paddle Wins!");
                        blipWin.play();
                        this.stop();
                        
                    } else {
                        resetBall();
                    }
                }
                
                else {
                    
                    /**
                    * automate cpu paddle(s)
                    */
                    
                    if (!leftPaddle.isHuman) cpuPaddleControl(leftPaddle);
                    if (!rightPaddle.isHuman) cpuPaddleControl(rightPaddle);
                    
                    /**
                    * move ball
                    */
                    ball.incrementXVelocity();
                    ball.incrementYVelocity();
                }
                
            }

         
        };
        
        gameLoop.start();
        
    }
    
    
    private void resetBall() {
        
        ball = new Ball(Constants.STAGE_W/2, randomDouble(0, Constants.STAGE_H), 6, randomDouble(3.0, 4.0), Constants.BALL_RADIUS, Color.WHITE);
        root.getChildren().add(ball);
    }
    
    
    private static double randomDouble(double min, double max) {
        Random r = new Random();
        return min + (max - min) * r.nextDouble();
    }
    
}
