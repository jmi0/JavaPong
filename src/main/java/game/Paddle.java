package game;


/**
 *
 * @author josephiannone
 */

import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

class Paddle extends Rectangle {
    
    static final int HEIGHT = 60;
    static final int WIDTH = 8;
    
    public boolean isHuman;
    public double xScoreBoundary;
    public EventHandler<MouseEvent> mouseHandler;
    
    /**
     * to store points scored by this paddle
     */
    public int points;
    
    
    public Paddle(double x, double y, Color color, double xScoreBoundary, boolean isHuman) {
        
        setFill(color);
        setStroke(color);
        setStrokeWidth(1);
        
        setHeight(HEIGHT);
        setWidth(WIDTH);
        setX(x);
        setY(y);
        
        this.isHuman = isHuman;
        
        /**
         * the x coordinate the ball needs to pass for this paddle to score
         */
        this.xScoreBoundary = xScoreBoundary;
        
        /**
         * initialize a mouse handler for this paddle
         */
        this.mouseHandler = new EventHandler<MouseEvent>() { 
            @Override 
            public void handle(MouseEvent e) { 
                
                /**
                 * limit paddle from going off screen
                 */
                if ((e.getY() <= (Game.STAGE_H - HEIGHT/2)) && (e.getY() >= HEIGHT/2))
                    setY(e.getY() - HEIGHT/2);
               
            }
        };   
            
        
        
    }
    
    
    
    
    
    
    
}
