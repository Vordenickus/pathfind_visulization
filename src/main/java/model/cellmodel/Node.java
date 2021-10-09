package model.cellmodel;

import lombok.Getter;
import model.MainState;

import java.util.ArrayList;

public class Node {

    @Getter
    private final int x,y;

    @Getter
    private final Node parentNode;

    @Getter
    private final int distance;

    @Getter
    private float price;

    private final PathFindTree tree;

    public Node(int x, int y, int distance, Node parentNode, PathFindTree tree) {
        this.x = x;
        this.y = y;
        this.distance=distance;
        this.parentNode = parentNode;
        this.tree = tree;
    }


    public ArrayList<Node> initializeChildren() {

        ArrayList<Node> children = new ArrayList<>();
        
        if (x>0) {
            if (!tree.getCell(x,y).isLeftWall()) {
                Node node = new Node(x-1,y,distance+1,this,tree);
                children.add(node);
            }
        }
        
        if (x<MainState.SIZE-1) {
            if (!tree.getCell(x,y).isRightWall()) {
                Node node = new Node(x+1,y,distance+1,this,tree);
                children.add(node);
            }
        }
        
        if (y>0) {
            if (!tree.getCell(x,y).isTopWall()) {
                Node node = new Node(x,y-1,distance+1,this,tree);
                children.add(node);
            }
        }
        
        if (y<MainState.SIZE-1) {
            if (!tree.getCell(x,y).isBottomWall()) {
                Node node = new Node(x,y+1,distance+1,this,tree);
                children.add(node);
            }
        }
        
        return children;
    }
}
