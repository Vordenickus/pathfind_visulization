package model;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class GamePanel extends JPanel implements Runnable, KeyListener {

    public static final int WIDTH=1000, HEIGHT=1000;

    private Thread mainThread;
    private boolean isRunning=false;

    private final int FPS=600;
    private long targetTime=1000/FPS;

    private GameStateManager gsm;


    public GamePanel() {
        setPreferredSize(new Dimension(WIDTH,HEIGHT));

        addKeyListener(this);
        setFocusable(true);

        start();
    }

    public void start() {
        isRunning = true;
        mainThread = new Thread(this);
        mainThread.start();
    }


    @Override
    public void run() {

        long start, elapsed, wait;

        gsm = new GameStateManager();

        while (isRunning) {

            start = System.nanoTime();

            tick();
            repaint();

            elapsed = System.nanoTime()-start;
            wait = targetTime - elapsed/1_000_000;

            if (wait<0) {
                wait=5;
            }

            try {

                Thread.sleep(wait);

            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }

    }

    @Override
    public void paintComponent(Graphics g) {
        if (gsm!=null) gsm.draw(g);
    }

    private void tick() {
        gsm.tick();
    }

    @Override
    public void keyTyped(KeyEvent e) {
        gsm.keyTyped(e);
    }

    @Override
    public void keyPressed(KeyEvent e) {
        gsm.keyPressed(e);
    }

    @Override
    public void keyReleased(KeyEvent e) {
        gsm.keyReleased(e);
    }
}
