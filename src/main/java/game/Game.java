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
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;



public class Game {
    
    private AnimationTimer gameLoop;
    private Ball ball;
    private Scene scene;
    private Paddle leftPaddle;
    private Paddle rightPaddle;
    private Group root;
    private Stage primaryStage;
    
    private Text p1ScoreText;
    private Text p2ScoreText;
    private Text endMatchMessage;
    private Text menuReplayMessage;
    private VBox endMatchDisplay;
    
    /**
     * initialize all audio clips
     */
    private final AudioClip blip1 = new AudioClip(
            new File(getClass().getResource("/sounds/pongblip1.wav").getPath()).toURI().toString());
    private final AudioClip blip2 = new AudioClip(
            new File(getClass().getResource("/sounds/pongblip2.wav").getPath()).toURI().toString());
    private final AudioClip blipPoint = new AudioClip(
            new File(getClass().getResource("/sounds/score.wav").getPath()).toURI().toString());
    private final AudioClip blipWin = new AudioClip(
            new File(getClass().getResource("/sounds/win.wav").getPath()).toURI().toString());
    
    
    public static final int BALL_RADIUS = 8;
    public boolean inPlay = false;
    public int winningScore = 5;
    public static double STAGE_H;
    public static double STAGE_W;
    
    
    public Game(Stage primaryStage, char paddleSelection, int difficultySelection) {
        
        STAGE_H = primaryStage.getHeight();
        STAGE_W = primaryStage.getWidth();
        
        root = new Group();
        scene = new Scene(root, STAGE_W, STAGE_H);
        scene.setFill(Color.rgb(13, 104, 77));
        
        /**
         * Main menu message
         */
        Text MainMenuMessage = new Text("Press \"q\" at any time to return to main menu");
        MainMenuMessage.setFont(Font.font(12));
        MainMenuMessage.setFill(Color.ANTIQUEWHITE);
        MainMenuMessage.setX(0);
        MainMenuMessage.setY(STAGE_H - 4);
        
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
        
        leftPaddle = new Paddle(20, STAGE_H/2 - Paddle.HEIGHT/2, Color.WHITE, STAGE_W, leftPaddleIsHuman);
        rightPaddle = new Paddle((scene.getWidth() - 20 - Paddle.WIDTH), STAGE_H/2 - Paddle.HEIGHT/2, Color.WHITE, 0, rightPaddleIsHuman);
        
        
        
        /**
         * Create text display for score
         */
        p1ScoreText = new Text(STAGE_W/4, STAGE_H/6, Integer.toString(leftPaddle.points));
        p2ScoreText = new Text((STAGE_W/4)*3, STAGE_H/6, Integer.toString(rightPaddle.points));
        p1ScoreText.setFill(Color.ANTIQUEWHITE);
        p1ScoreText.setFont(Font.font (STAGE_H/8));
        p2ScoreText.setFill(Color.ANTIQUEWHITE);
        p2ScoreText.setFont(Font.font (STAGE_H/8));
        
     
        
        /**
         * Create ball
         */
        ball = new Ball(STAGE_W/2, randomDouble(0 + Paddle.HEIGHT, STAGE_H - Paddle.HEIGHT), 6, randomDouble(3.0, 3.4), BALL_RADIUS, Color.WHITE);
        
        
        
        
        /**
         * Listen for q and r keys
         */
        primaryStage.addEventFilter(KeyEvent.KEY_PRESSED, new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                /**
                 * reset all necessary game values if Q is pressed.
                 * 
                 */
                if (event.getCode() == KeyCode.Q) {
                    gameLoop.stop();
                    root.getChildren().remove(ball);
                    ball = null;
                    leftPaddle.points = 0;
                    rightPaddle.points = 0;
                }
                
                else if (event.getCode() == KeyCode.R) {
                    /**
                     * Replay (reset all necessary values for match)
                     */
                    root.getChildren().remove(ball);
                    leftPaddle.points = 0;
                    p1ScoreText.setText(Integer.toString(leftPaddle.points));
                    rightPaddle.points = 0;
                    p2ScoreText.setText(Integer.toString(rightPaddle.points));
                    root.getChildren().remove(endMatchDisplay);
                    resetBall();
                    if (!inPlay) {
                        if (leftPaddle.isHuman) scene.addEventFilter(MouseEvent.MOUSE_MOVED, leftPaddle.mouseHandler);
                        if (rightPaddle.isHuman) scene.addEventFilter(MouseEvent.MOUSE_MOVED, rightPaddle.mouseHandler);
                        gameLoop.start();  
                    }
                    
                    
                }
            }
        });
        
        
        
        /**
         * Add all objects to group/scene
         */
        this.root.getChildren().addAll(leftPaddle, rightPaddle, ball, courtDivider, p1ScoreText, p2ScoreText, MainMenuMessage);
        primaryStage.setScene(scene);
                
        
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
     * 
     * @return 
     */
    private boolean yCollision() {
        
        if (ball.getCenterY() + ball.getRadius() >= scene.getHeight()) return true;
        
        else if (ball.getCenterY() - ball.getRadius() <= 0) return true;
      
        return false;
        
    }
    
    
    /**
     * Check if ball has gone beyond the given paddles score boundary
     * 
     * @param paddle
     * @return 
     */
    private boolean checkForScore(Paddle paddle) {
        
      if (paddle.xScoreBoundary == 0 && ball.getCenterX() <= paddle.xScoreBoundary) return true;
      
      else if (paddle.xScoreBoundary > 0 && ball.getCenterX() >= paddle.xScoreBoundary) return true;
    
      return false;
      
    }
    
    /**
     * A method to handle automated paddle control
     * 
     * @param paddle 
     */
    private void cpuPaddleControl(Paddle paddle) {
        
        /**
         * Only if the ball is moving toward the paddle
         */
        if ((paddle.getX() < scene.getWidth() / 2 && !ball.xVelocityIncreasing) || (paddle.getX() > scene.getWidth() / 2 && ball.xVelocityIncreasing)) {
            /**
             * move paddle down if ball is moving down and paddle is above ball
             */
            if (ball.yVelocityIncreasing && paddle.getY() < STAGE_H && paddle.getY() < ball.getCenterY()) {
                /**
                 * keep paddle in bounds of stage
                 */
                if (paddle.getY() <= STAGE_H - Paddle.HEIGHT) 
                    paddle.setY(paddle.getY() + 4);
            }
            /**
             * move paddle up if ball is moving up and paddle is below ball
             */
            else if (paddle.getY() > 0 && paddle.getY() > ball.getCenterY()) {
                /**
                 * keep paddle in bounds of stage
                 */
                if (paddle.getY() >= 0)
                    paddle.setY(paddle.getY() - 4);
            }
        }
        
    }
    
    
    /**
     * A method to display the match results and menu/control options
     * 
     * @param message 
     */
    private void endMatchMessage(String message) {
        
        // Create text objects
        endMatchMessage = new Text(STAGE_W/2, STAGE_H/2, message);
        endMatchMessage.setFill(Color.ANTIQUEWHITE);
        endMatchMessage.setFont(Font.font (STAGE_H/12));
        endMatchMessage.setTextAlignment(TextAlignment.CENTER);
        menuReplayMessage = new Text("Press \"q\" to return to the main menu or \"r\" to replay.");
        menuReplayMessage.setFill(Color.ANTIQUEWHITE);
        menuReplayMessage.setFont(Font.font (STAGE_H/32));
        menuReplayMessage.setTextAlignment(TextAlignment.CENTER);
        
        // put text objects in VBox
        endMatchDisplay = new VBox(endMatchMessage, menuReplayMessage);
        
        // Set positioning of vBox
        endMatchDisplay.setLayoutX((STAGE_W/2) - 240);
        endMatchDisplay.setLayoutY((STAGE_H/2) - 140);
        
        // display
        root.getChildren().add(endMatchDisplay);

    }
    
    
    /**
     * Initialize mouse event filter and begin game loop
     */
    public void startMatch() {

        /**
         * Adding event Filter(s) for mouse control
         */
        if (leftPaddle.isHuman) scene.addEventFilter(MouseEvent.MOUSE_MOVED, leftPaddle.mouseHandler);
        if (rightPaddle.isHuman) scene.addEventFilter(MouseEvent.MOUSE_MOVED, rightPaddle.mouseHandler);
        
        // start game
        this.gameLoop = new AnimationTimer() {
            
            
            
            @Override
            public void handle(long now) {
                
                inPlay = true;
                
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
                     * check for left paddle score
                     */
                    blipPoint.play();
                    leftPaddle.points += 1;
                    p1ScoreText.setText(Integer.toString(leftPaddle.points));
                    root.getChildren().remove(ball);
                    if (leftPaddle.points >= winningScore) {
                        /**
                         * Check if left paddle won
                         */
                        
                        // remove mouse even filter
                        if (leftPaddle.isHuman) scene.removeEventFilter(MouseEvent.MOUSE_MOVED, leftPaddle.mouseHandler);
                        if (rightPaddle.isHuman) scene.removeEventFilter(MouseEvent.MOUSE_MOVED, rightPaddle.mouseHandler);
                        
                        // display end of match message and end game loop
                        endMatchMessage("Left Paddle Wins!");
                        blipWin.play();
                        this.stop();
                        inPlay = false;
                        
                    } else {
                        // reset ball for next "serve"
                        resetBall();
                    }   
                }
                
                else if (checkForScore(rightPaddle)) {
                    /**
                     * check for right paddle score
                     */
                    blipPoint.play();
                    rightPaddle.points += 1;
                    p2ScoreText.setText(Integer.toString(rightPaddle.points));
                    root.getChildren().remove(ball);
                    if (rightPaddle.points >= winningScore) {
                        /**
                         * Check if right paddle won
                         */
                        
                        // remove mouse even filter
                        if (leftPaddle.isHuman) scene.removeEventFilter(MouseEvent.MOUSE_MOVED, leftPaddle.mouseHandler);
                        if (rightPaddle.isHuman) scene.removeEventFilter(MouseEvent.MOUSE_MOVED, rightPaddle.mouseHandler);
                        
                        // display end of match message and end game loop
                        endMatchMessage("Right Paddle Wins!");
                        blipWin.play();
                        this.stop();
                        inPlay = false;
                        
                    } else {
                        // reset ball for next "serve"
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
        
        // start game loop
        gameLoop.start();
                
    }
    
    
    /**
     * Create new ball and add to root
     */
    private void resetBall() {
        ball = new Ball(STAGE_W/2, randomDouble(0 + Paddle.HEIGHT, STAGE_H - Paddle.HEIGHT), 6, randomDouble(3.0, 3.4), BALL_RADIUS, Color.WHITE);
        root.getChildren().add(ball);
    }
    
    
    /**
     * Get a random double
     * 
     * @param min
     * @param max
     * @return 
     */
    private static double randomDouble(double min, double max) {
        Random r = new Random();
        return min + (max - min) * r.nextDouble();
    }
    
    
}
