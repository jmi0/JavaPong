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
    
    
    public Paddle(double x, double y, Color color, boolean isHuman) {
        setFill(color);
        setStroke(color);
        setStrokeWidth(1);
        setHeight(PADDLE_H);
        setWidth(PADDLE_W);
        setX(x);
        setY(y);
    }
    
}
