/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package game;

import java.util.Random;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

/**
 *
 * @author josephiannone
 */
public class Ball extends Circle {
    
    
    public double xVelocity;
    public double yVelocity;
    public double xAcceleration;
    public double yAcceleration;
    public boolean xVelocityIncreasing;
    public boolean yVelocityIncreasing;
    

    public Ball(double xPos, double yPos, double xAcceleration, double yAcceleration, int radius, Color color) {
        
        setCenterX(xPos);
        setCenterY(yPos);
        
        this.xVelocity = xPos;
        this.yVelocity = yPos;
        
        this.xAcceleration = xAcceleration;
        this.yAcceleration = yAcceleration;
        
        Random random = new Random();
        
        this.yVelocityIncreasing = random.nextBoolean();
        this.xVelocityIncreasing = random.nextBoolean();

        
        setRadius(radius);
        setFill(color);
        setStroke(color);
        setStrokeWidth(1);   
    }
    
    public void reverseXVelocity() {
        if (this.xVelocityIncreasing) this.xVelocityIncreasing = false;
        else this.xVelocityIncreasing = true;
    }
    
    public void reverseYVelocity() {
        if (this.yVelocityIncreasing) this.yVelocityIncreasing = false;
        else this.yVelocityIncreasing = true;
    }
    
    public void incrementXVelocity() {
        if (this.xVelocityIncreasing) this.xVelocity += this.xAcceleration;
        else this.xVelocity -= this.xAcceleration;
        setCenterX(this.xVelocity);
    }
    
    public void incrementYVelocity() {
        if (this.yVelocityIncreasing) this.yVelocity += this.yAcceleration;
        else this.yVelocity -= this.yAcceleration;
        setCenterY(this.yVelocity);
    }
    
    public void moveBall() {
        
    }
    
}
