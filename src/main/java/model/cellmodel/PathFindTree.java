package model.cellmodel;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import model.MainState;

import java.util.ArrayList;

public class PathFindTree {

    private final Cell[][] area;

    private final ArrayList<Node> path = new ArrayList<>();
    private final Node start;
    private ArrayList<Node> cur = new ArrayList<>();
    private final ArrayList<Node> prev = new ArrayList<>();


    private final int startX, startY, toX, toY;

    private int index;
    private VirtualCell[][] cells;

    @Getter @Setter
    private boolean updating;

    @Getter
    private final MainState mainState;


    public PathFindTree(Cell[][] area, int startX, int startY, int toX, int toY, MainState mainState) {
        this.area = area;
        this.startX = startX;
        this.startY = startY;
        this.toX = toX;
        this.toY = toY;
        this.mainState=mainState;
        index=0;
        cells=getCells();
        updating=false;
        start = new Node(startX,startY,0,null,this);
        cells[startY][startX].setVisited(true);
        cells[startY][startX].setDistance(0);
        path.add(start);
    }



    public void update() {

        if (updating) {
            Node temp = path.get(index);
            prev.add(temp);
            mainState.area[temp.getY()][temp.getX()].setPassThrough(true);
            if (index>0) mainState.area[start.getY()][start.getX()].setPassThrough(false);
            //for (Node node:prev) {
                //mainState.area[node.getY()][node.getX()].setPassThrough(false);
            //}
            //prev.clear();
            //prev.trimToSize();

            if (temp.getX()==toX&&temp.getY()==toY) {
                mainState.setSolution(temp);
                updating=false;
            }
            if (updating) {
            ArrayList<Node> children = temp.initializeChildren();

            for (int i=0; i<children.size();i++) {
                Node child = children.get(i);
                if (!cells[child.getY()][child.getX()].isVisited()||
                        (child.getDistance()<cells[child.getY()][child.getX()].getDistance())) {
                    path.add(child);
                    prev.add(child);
                    cells[child.getY()][child.getX()].setDistance(child.getDistance());
                    cells[child.getY()][child.getX()].setVisited(true);
                }
            }

            for (Node node:prev) {
                mainState.area[node.getY()][node.getX()].setPassThrough(true);
            }

            index++;
            }
        }

    }

    private VirtualCell[][] getCells() {
        VirtualCell[][] cells = new VirtualCell[area.length][area[0].length];
        for (int i=0;i<area.length;i++) {
            for (int x=0; x<area[i].length; x++) {
                cells[i][x] = new VirtualCell(x,i,0,false);
            }
        }
        return cells;
    }

    public boolean pathExist() {

        ArrayList<Node> path = new ArrayList<>();

        VirtualCell[][] nodes = getCells();

        Node start = new Node(startX,startY,0,null,this);
        nodes[startY][startX].setVisited(true);
        nodes[startY][startX].setDistance(0);


        int ind=0;

        path.add(start);

        while (ind!=path.size()) {

            Node temp = path.get(ind);

            if (temp.getX()==toX&&temp.getY()==toY) {
                return true;
            }

            ArrayList<Node> children = temp.initializeChildren();

            for (int z=0; z<children.size();z++) {
                Node child = children.get(z);
                if (!nodes[child.getY()][child.getX()].isVisited()||
                        (child.getDistance()<nodes[child.getY()][child.getX()].getDistance())) {
                    path.add(child);
                    nodes[child.getY()][child.getX()].setVisited(true);
                    nodes[child.getY()][child.getX()].setDistance(child.getDistance());
                }
            }
            ind++;
        }
        return false;
    }


    public Cell getCell(int x, int y) {
        return area[y][x];
    }

    @AllArgsConstructor @EqualsAndHashCode
    private static class VirtualCell {
        @Getter
        private final int x;
        @Getter
        private final int y;
        @Getter @Setter
        private int distance;
        @Getter @Setter
        private boolean visited;
    }

}
