package game;


/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author josephiannone
 */

import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

class Paddle extends Rectangle {
    
    static final int PADDLE_H = 60;
    static final int PADDLE_W = 8;
    
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
        setHeight(PADDLE_H);
        setWidth(PADDLE_W);
        setX(x);
        setY(y);
        
        this.isHuman = isHuman;
        
        /**
         * the x coordinate the ball needs to pass for this paddle to score
         */
        this.xScoreBoundary = xScoreBoundary;
        
        /**
         * create mouse handler
         */
        this.mouseHandler = new EventHandler<MouseEvent>() { 
            @Override 
            public void handle(MouseEvent e) { 
                setY(e.getY() - PADDLE_H/2);
            }
        };   
            
        
        
    }
    
    
    
    
    
    
    
}
