/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package game;

import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

/**
 *
 * @author josephiannone
 */
public class Ball extends Circle {
    
    static final int RADIUS = 8;
    public double acceleration = 0;
    public double xVelocity;
    public double yVelocity;
    public boolean xVelocityIncreasing;
    public boolean yVelocityIncreasing;

    public Ball(double x, double y, Color color) {
        
        setCenterX(x);
        setCenterY(y);
        
        this.xVelocity = x;
        this.yVelocity = y;
        
        this.yVelocityIncreasing = true;
        this.xVelocityIncreasing = false;
        
        setRadius(RADIUS);
        setFill(color);
        setStroke(color);
        setStrokeWidth(1);   
    }
    
    public void changeXVelocity() {
        if (this.xVelocityIncreasing) this.xVelocityIncreasing = false;
        else this.xVelocityIncreasing = true;
    }
    
    public void changeYVelocity() {
        if (this.yVelocityIncreasing) this.yVelocityIncreasing = false;
        else this.yVelocityIncreasing = true;
    }
    
    public void accelerate() {
        this.acceleration += .1;
    }
    
    public void deccelerate() {
        this.acceleration -= .1;
    }
    
    public void updateXVelocity(double xVelocity) {
        if (this.xVelocityIncreasing) this.xVelocity += xVelocity;
        else this.xVelocity -= xVelocity;
        setCenterX(this.xVelocity);
    }
    
    public void updateYVelocity(double yVelocity) {
        if (this.yVelocityIncreasing) this.yVelocity += yVelocity;
        else this.yVelocity -= yVelocity;
        setCenterY(this.yVelocity);
    }
    
    /**
     * Get the coordinates of each Y edge of the ball
     * @return 
     */
    public double[] getYEdgeCoordinates() {
        double[] coordinates = { getCenterY() - RADIUS, getCenterY() + RADIUS };
        return coordinates;
    }
    
    /**
     * Get the coordinates of each X edge of the ball
     * @return 
     */
    public double[] getXEdgeCoordinates() {
        double[] coordinates = { getCenterX() - RADIUS, getCenterX() + RADIUS,};
        return coordinates;
    }
}
