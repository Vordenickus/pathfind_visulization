package model;

import lombok.AllArgsConstructor;

import java.awt.*;
import java.awt.event.KeyEvent;

@AllArgsConstructor
public abstract class GameState {

    protected GameStateManager gsm;

    public abstract void init();
    public abstract void tick();
    public abstract void draw(Graphics g);
    public abstract void keyTyped(KeyEvent e);
    public abstract void keyPressed(KeyEvent e);
    public abstract void keyReleased(KeyEvent e);

}
