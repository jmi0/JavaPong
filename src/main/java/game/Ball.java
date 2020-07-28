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
        
        // need to observe these for testing
        //System.out.println(yAcceleration);
        //System.out.println(yPos);
        
        setCenterX(xPos);
        setCenterY(yPos);
        
        this.xVelocity = xPos;
        this.yVelocity = yPos;
        
        this.xAcceleration = xAcceleration;
        this.yAcceleration = yAcceleration;
        
        /**
         * initialize velocity directions with random booleans
         */
        Random random = new Random();
        this.yVelocityIncreasing = random.nextBoolean();
        this.xVelocityIncreasing = random.nextBoolean();

        
        setRadius(radius);
        setFill(color);
        setStroke(color);
        setStrokeWidth(1);
    }
    
    /**
     * reverse the x velocity direction
     */
    public void reverseXVelocity() {
        /**
         * use this boolean to denote increasing or decreasing
         */
        if (this.xVelocityIncreasing) this.xVelocityIncreasing = false;
        else this.xVelocityIncreasing = true;
    }
    
    /**
     * reverse the y velocity direction
     */
    public void reverseYVelocity() {
        /**
         * use this boolean to denote increasing or decreasing
         */
        if (this.yVelocityIncreasing) this.yVelocityIncreasing = false;
        else this.yVelocityIncreasing = true;
    }
    
    /**
     * increment the x velocity
     */
    public void incrementXVelocity() {
        /**
         * increment or decrement depending on boolean value
         */
        if (this.xVelocityIncreasing) this.xVelocity += this.xAcceleration;
        else this.xVelocity -= this.xAcceleration;
        setCenterX(this.xVelocity);
    }
    
    /**
     * increment the y velocity
     */
    public void incrementYVelocity() {
        /**
         * increment or decrement depending on boolean value
         */
        if (this.yVelocityIncreasing) this.yVelocity += this.yAcceleration;
        else this.yVelocity -= this.yAcceleration;
        setCenterY(this.yVelocity);
    }
    
}
