package model;

import lombok.Setter;
import model.cellmodel.Cell;
import model.cellmodel.Node;
import model.cellmodel.PathFindTree;

import java.awt.*;
import java.awt.event.KeyEvent;


public class MainState extends GameState{

    public static int SIZE=100;

    private PathFindTree pathFindTree;

    @Setter
    private Node solution;

    public Node start = new Node(0,0,null,null);
    public Node end = new Node(SIZE-1,SIZE-1,null,null);

    private boolean update=true;

    public int count=0;


    public Cell[][] area;


    public MainState(GameStateManager gsm) {
        super(gsm);
        init();
    }

    @Override
    public void init() {

        area = initializeArea();
        pathFindTree=new PathFindTree(area,start.getX(),start.getY(),end.getX(),end.getY(),this);


    }

    private Cell[][] initializeArea() {

        Cell[][] target = new Cell[SIZE][SIZE];

        for (int i=0; i<SIZE;i++) {
            for (int x=0; x<SIZE;x++) {
                target[i][x]=new Cell(x,i);
            }
        }
        correctHorizontally(target);
        correctVertically(target);
        PathFindTree pathFindTree = new PathFindTree(target,start.getX(),start.getY(),end.getX(),end.getY(), this);
        if (pathFindTree.pathExist()) return target;
        return initializeArea();

    }

    private void correctHorizontally(Cell[][] area) {

        for (int i=0; i<area.length;i++) {
            for(int x=0;x<area[i].length;x++) {

                if (x==0) {
                    Cell leftCell = area[i][x];
                    Cell rightCell = area[i][x+1];
                    if (leftCell.isRightWall()) rightCell.setLeftWall(true);
                    if (rightCell.isLeftWall()) leftCell.setRightWall(true);

                } else if (x==SIZE-1) {
                    Cell leftCell = area[i][x-1];
                    Cell rightCell = area[i][x];
                    if (leftCell.isRightWall()) rightCell.setLeftWall(true);
                    if (rightCell.isLeftWall()) leftCell.setRightWall(true);

                } else {
                    Cell leftCell = area[i][x-1];
                    Cell centreCell = area[i][x];
                    Cell rightCell = area[i][x+1];

                    if (leftCell.isRightWall()) centreCell.setLeftWall(true);
                    if (centreCell.isLeftWall()) leftCell.setRightWall(true);
                    if (centreCell.isRightWall()) rightCell.setLeftWall(true);
                    if (rightCell.isLeftWall()) centreCell.setRightWall(true);

                }
            }
        }

    }

    private void correctVertically(Cell[][] area) {

        for (int x=0; x<area.length;x++) {
            for (int y=0; y<area[x].length; y++) {

                if (y==0) {

                    Cell topCell = area[y][x];
                    Cell bottomCell = area[y+1][x];

                    if (topCell.isBottomWall()) bottomCell.setTopWall(true);
                    if (bottomCell.isTopWall()) topCell.setBottomWall(true);

                } else if (y==SIZE-1) {

                    Cell bottomCell = area[y][x];
                    Cell topCell = area[y-1][x];

                    if (topCell.isBottomWall()) bottomCell.setTopWall(true);
                    if (bottomCell.isTopWall()) topCell.setBottomWall(true);

                } else {

                    Cell topCell = area[y-1][x];
                    Cell centreCell = area[y][x];
                    Cell bottomCell = area[y+1][x];

                    if (topCell.isBottomWall()) centreCell.setTopWall(true);
                    if (centreCell.isTopWall()) topCell.setBottomWall(true);
                    if (centreCell.isBottomWall()) bottomCell.setTopWall(true);
                    if (bottomCell.isTopWall()) centreCell.setBottomWall(true);

                }

            }
        }

    }

    @Override
    public void tick() {
        if (pathFindTree.isUpdating()) pathFindTree.update();
        if(solution!=null) {
            if (update) {
                for (int i = 0; i < area.length; i++) {
                    for (int x = 0; x < area[i].length; x++) {
                        area[i][x].setPassThrough(false);
                        //System.out.println(i+" "+x);
                    }
                }

                area[solution.getY()][solution.getX()].setPartOfThePath(true);
                Node temp = solution.getParentNode();

                while (temp != null) {
                    //System.out.println("here");
                    area[temp.getY()][temp.getX()].setPartOfThePath(true);
                    temp = temp.getParentNode();
                }
                update=false;
            } else {
                count++;
                if (count==1000) {
                    area=initializeArea();
                    pathFindTree=new PathFindTree(area,start.getX(),start.getY(),end.getX(),end.getY(),this);
                    solution=null;
                    update=true;
                    count=0;
                    pathFindTree.setUpdating(true);
                }
            }
        }
    }

    @Override
    public void draw(Graphics g) {
        g.setColor(Color.black);
        g.fillRect(0,0,GamePanel.WIDTH,GamePanel.HEIGHT);

        for (int i=0; i<area.length;i++) {
            for (int x=0; x<area[i].length;x++) {
                area[i][x].draw(g);
            }
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();
        if (key==KeyEvent.VK_SPACE) {
            area=initializeArea();
            pathFindTree=new PathFindTree(area,start.getX(),start.getY(),end.getX(),end.getY(),this);
            solution=null;
            update=true;
        }
        if (key==KeyEvent.VK_ENTER) {
            pathFindTree.setUpdating(true);
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }

}
