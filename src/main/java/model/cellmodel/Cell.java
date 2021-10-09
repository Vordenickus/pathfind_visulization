package model.cellmodel;


import lombok.Getter;
import lombok.Setter;
import model.GamePanel;
import model.MainState;

import java.awt.*;

public class Cell {

    protected int x,y;

    protected int width, height;

    protected static final int HORIZONTAL_CHANCE=30, VERTICAL_CHANCE=15;

    protected int distance;

    @Getter @Setter
    protected boolean leftWall, rightWall, topWall, bottomWall;

    protected boolean visited=false;

    @Getter @Setter
    protected boolean partOfThePath=false;

    @Getter @Setter
    protected boolean passThrough = false;

    public Cell(int x, int y) {
        this.x=x;
        this.y=y;
        initializeWall();
        width = GamePanel.WIDTH/MainState.SIZE;
        height=width;
    }

    private void initializeWall() {
        leftWall=passCheck(HORIZONTAL_CHANCE);
        rightWall=passCheck(HORIZONTAL_CHANCE);
        topWall=passCheck(VERTICAL_CHANCE);
        bottomWall=passCheck(VERTICAL_CHANCE);
    }

    public void draw(Graphics g) {

        g.setColor(Color.WHITE);
        if (leftWall) g.fillRect(width*x,height*y,width/10,height);
        if (rightWall) g.fillRect(width*x+width/10*9,height*y,width/10,height);
        g.setColor(Color.GREEN);

        g.setColor(Color.BLACK);
        if (partOfThePath) g.setColor(Color.YELLOW);
        if (passThrough) g.setColor(Color.GREEN);
        g.fillRect(width*x+width/10,height*y+height/10,width/10*8,height/10*8);

        g.setColor(Color.WHITE);


        if(topWall) g.fillRect(width*x,height*y,width,height/10);
        if (bottomWall) g.fillRect(width*x,height*y+height/10*9,width,height/10);
        //g.drawString(Integer.toString(y),width*x,height*y+height/2);

    }

    protected boolean passCheck(int chance) {
        return getRandom() < chance;
    }

    protected int getRandom() {
        return (int) (Math.random()*100);
    }

}
