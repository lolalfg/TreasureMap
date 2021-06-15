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
public class Adventurer {

    String name;
    int horizontalX;
    int verticalY;
    String orientation;
    String motionSequence;
    String endSequence;
    int treasureCounter;

    public int getTreasureCounter() {
        return treasureCounter;
    }

    public void setTreasureCounter(int treasureCounter) {
        this.treasureCounter = treasureCounter;
    }

    public Adventurer(String name, int horizontalX, int verticalY, String orientation, String motionSequence, String endSequence, int treasureCounter) {
        this.name = name;
        this.horizontalX = horizontalX;
        this.verticalY = verticalY;
        this.orientation = orientation;
        this.motionSequence = motionSequence;
        this.endSequence = endSequence;
        this.treasureCounter = treasureCounter;
    }
    
    public String getName() {
        return name;
    }

    public int getHorizontalX() {
        return horizontalX;
    }

    public int getVerticalY() {
        return verticalY;
    }

    public String getOrientation() {
        return orientation;
    }

    public String getMotionSequence() {
        return motionSequence;
    }

    public String getEndSequence() {
        return endSequence;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setHorizontalX(int horizontalX) {
        this.horizontalX = horizontalX;
    }

    public void setVerticalY(int verticalY) {
        this.verticalY = verticalY;
    }

    public void setOrientation(String orientation) {
        this.orientation = orientation;
    }

    public void setMotionSequence(String motionSequence) {
        this.motionSequence = motionSequence;
    }

    public void setEndSequence(String endSequence) {
        this.endSequence = endSequence;
    }

    //Permet de récupérer la prochaine action à réaliser
    public String GetNextAction() {
        try {
            return this.getMotionSequence().substring(this.getEndSequence().length(), this.getEndSequence().length() + 1);
        } catch (Exception ex) {
            return "";
        }
    }

    public void Move() {
        String orientation = this.getOrientation();

        switch (orientation) {
            case "N":
                this.setVerticalY(this.getVerticalY() - 1);
                break;
            case "S":
                this.setVerticalY(this.getVerticalY() + 1);
                break;
            case "E":
                this.setHorizontalX(this.getHorizontalX() + 1);
                break;
            case "O":
                this.setHorizontalX(this.getHorizontalX() - 1);
                break;
        }
    }

    public int[] GetNextPosition() {
        String orientation = this.getOrientation();
        int[] nextPosition = new int[2];

        switch (orientation) {
            case "N":
                nextPosition[0] = this.getVerticalY() - 1;
                nextPosition[1] = this.getHorizontalX();

                break;
            case "S":
                nextPosition[0] = this.getVerticalY() + 1;
                nextPosition[1] = this.getHorizontalX();
                break;
            case "E":
                nextPosition[0] = this.getVerticalY();
                nextPosition[1] = this.getHorizontalX() + 1;
                break;
            case "O":
                nextPosition[0] = this.getVerticalY();
                nextPosition[1] = this.getHorizontalX() - 1;
                break;
        }
        
        return nextPosition;
    }

    public void Rotation(boolean isRight) {
        String orientation = this.getOrientation();

        switch (orientation) {
            case "N":
                this.setOrientation(isRight ? "E" : "O");
                break;
            case "S":
                this.setOrientation(isRight ? "O" : "E");
                break;
            case "E":
                this.setOrientation(isRight ? "S" : "N");
                break;
            case "O":
                this.setOrientation(isRight ? "N" : "S");
                break;
        }
    }
    
    public void AddTreasure(){
        this.setTreasureCounter(this.getTreasureCounter() + 1);
    }
}
