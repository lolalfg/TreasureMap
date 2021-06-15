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
public class Treasure {
    int horizontalX;
    int verticalY;
    int treasureCount;

    public int getHorizontalX() {
        return horizontalX;
    }

    public int getVerticalY() {
        return verticalY;
    }

    public int getTreasureCount() {
        return treasureCount;
    }

    public void setHorizontalX(int horizontalX) {
        this.horizontalX = horizontalX;
    }

    public void setVerticalY(int verticalY) {
        this.verticalY = verticalY;
    }

    public void setTreasureCount(int treasureCount) {
        this.treasureCount = treasureCount;
    }

    public Treasure(int horizontalX, int verticalY, int treasureCount) {
        this.horizontalX = horizontalX;
        this.verticalY = verticalY;
        this.treasureCount = treasureCount;
    }
    
    public void TakeOffTreasure(){
        this.setTreasureCount(this.getTreasureCount() - 1);
    }
    
    
}
