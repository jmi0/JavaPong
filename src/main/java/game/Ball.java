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
    public double xVelocity = 0;
    public double yVelocity = 0;
    
    public Ball(double x, double y, Color color) {
        setCenterX(x);
        setCenterY(y);
        setRadius(RADIUS);
        setFill(color);
        setStroke(color);
        setStrokeWidth(1);   
    }
    
    public void accelerate() {
        this.acceleration += .1;
    }
    
    public void deccelerate() {
        this.acceleration -= .1;
    }
    
    public void changeXVelocity(double xVelocity) {
        this.xVelocity = xVelocity;
        setCenterX(xVelocity);
    }
    
    public void changeYVelocity(double yVelocity) {
        this.yVelocity = yVelocity;
        setCenterY(yVelocity);
    }
}
