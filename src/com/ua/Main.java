package com.ua;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.List;

public class Main implements KeyListener, ActionListener {

    public static final int FPS = 60;
    public static final int WIDTH = 640;
    public static final int HEIGHT = 480;

    private JFrame frame;
    private JPanel panel;
    private Bird bird;
    private List<Rectangle> rectangles;
    private boolean paused;
    private int time, scroll;
    private Timer timer;



    public static void main(String[] args) {
        new Main().go();
    }

    public void go() {
        frame = new JFrame("Flappy Bird");
        bird = new Bird();
        rectangles = new ArrayList<>();
        panel = new GamePanel(this, bird, rectangles);
        frame.add(panel);

        frame.setSize(WIDTH, HEIGHT);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
        frame.addKeyListener(this);

        paused = true;
        //frames per second to game
        timer = new Timer(1000 / FPS, this);
        timer.start();
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    //key listener
    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_UP) {
            bird.jump();
        } else if (e.getKeyCode() == KeyEvent.VK_SPACE) {
            paused = false;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }

    //call this method each 1000/FPS times. for example each 16 ms
    @Override
    public void actionPerformed(ActionEvent e) {
        panel.repaint();
        if (!paused) {
            bird.physics();
            //scroll % 90 frequency of pipes. 90 easy. 40 normal. 10 hard
            if (scroll % 10 == 0) {
                //generate random height of pipe
                Rectangle rectangle = new Rectangle(WIDTH, 0, GamePanel.PIPE_WEIGHT, getRandomHeight());
                int height2 = getRandomHeight();
                Rectangle rectangle2 = new Rectangle(WIDTH, HEIGHT - height2, GamePanel.PIPE_WEIGHT, height2);

                rectangles.add(rectangle);
                rectangles.add(rectangle2);
            }

            List<Rectangle> toRemove = new ArrayList<>();
            boolean game = true;
            for (Rectangle rectangle: rectangles) {
                //speed of bird. 5 easy. 10 normal.  11+ hard
                rectangle.x -= 5;
                if (rectangle.x + rectangle.width <= 0) {
                    toRemove.add(rectangle);
                }
                if (rectangle.contains(bird.x, bird.y)) {
                    JOptionPane.showMessageDialog(frame, "You lose");
                    game = false;
                }
            }

            rectangles.removeAll(toRemove);
            time++;
            scroll++;

            if (bird.y > HEIGHT || bird.y + Bird.RAD < 0) {
                game = false;
            }

            if (!game) {
                rectangles.clear();
                bird.reset();
                time = 0;
                scroll = 0;
                paused = true;
            }
        }
    }

    private int getRandomHeight() {
        return (int) ((Math.random() * HEIGHT) / 5f + (0.2f) * HEIGHT);
    }

    public boolean paused() {
        return paused;
    }

    public int getScore() {
        return time;
    }
}
