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

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

class Paddle extends Rectangle {
    
    static final int PADDLE_H = 100;
    static final int PADDLE_W = 8;
    public boolean isHuman;
    public char side;
    public double xScoreBoundary;
    
    // to store points scored by this paddle
    public int points;
    
    
    public Paddle(double x, double y, Color color, boolean isHuman, char side) {
        this.isHuman = isHuman;
        setFill(color);
        setStroke(color);
        setStrokeWidth(1);
        setHeight(PADDLE_H);
        setWidth(PADDLE_W);
        setX(x);
        setY(y);
        
        /**
         * The boundary on the x axis for this paddle to score a point
         */
        if (side == 'r') xScoreBoundary = 0;
        else xScoreBoundary = Constants.STAGE_W;
    }
    
    /**
     * Get the x coordinate range of the game facing edge of the paddle
     * @return 
     */
    public double[] getXEdgeRange() {

        double[] coordinateRange = new double[2];
        if (side == 'l') {
            coordinateRange[0] = getX() + PADDLE_W;
            coordinateRange[1] = (getX() - PADDLE_H) + PADDLE_W;
        } else { 
            coordinateRange[0] = getX();
            coordinateRange[1] = getX() - PADDLE_H;
        }
        return coordinateRange;
        
    }
    
    
    public double getYEdge() {
        
        if (side == 'l') 
            return getY() + PADDLE_W;
        else 
            return getY();
        
    }
    
}
