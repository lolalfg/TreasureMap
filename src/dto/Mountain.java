/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dto;

/**
 *
 * @author LLAFAGE
 */
public class Mountain {
    int horizontalX;
    int verticalY;

    public int getHorizontalX() {
        return horizontalX;
    }

    public int getVerticalY() {
        return verticalY;
    }

    public void setHorizontalX(int horizontalX) {
        this.horizontalX = horizontalX;
    }

    public void setVerticalY(int verticalY) {
        this.verticalY = verticalY;
    }

    public Mountain(int horizontalX, int verticalY) {
        this.horizontalX = horizontalX;
        this.verticalY = verticalY;
    }
    
    
    
}
