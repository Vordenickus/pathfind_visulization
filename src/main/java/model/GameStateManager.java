package model;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.Stack;

public class GameStateManager {

    public Stack<GameState> states;

    public GameStateManager() {
        states = new Stack<>();

        states.push(new MainState(this));
    }

    public void tick() {
        states.peek().tick();
    }

    public void draw(Graphics g) {
        states.peek().draw(g);
    }

    public void keyTyped(KeyEvent e) {
        states.peek().keyTyped(e);
    }


    public void keyPressed(KeyEvent e) {
        states.peek().keyPressed(e);
    }


    public void keyReleased(KeyEvent e) {
        states.peek().keyReleased(e);
    }


}
