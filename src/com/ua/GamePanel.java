package com.ua;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.io.File;
import java.io.IOException;
import java.util.List;

public class GamePanel extends JPanel {

    public static final int PIPE_WEIGHT = 50;
    public static final int PIPE_HEIGHT = 30;

    private Main main;
    private Bird bird;
    private List<Rectangle> rects;
    private Font scoreFont;
    private Font pauseFont;
    private Image pipeHead, pipeLength;

    public GamePanel(Main main, Bird bird, List<Rectangle> rects) {
        this.main = main;
        this.bird = bird;
        this.rects = rects;

        scoreFont = new Font("Comic Sans MS", Font.BOLD, 18);
        pauseFont = new Font("Arial", Font.BOLD, 48);

        try {
            pipeHead = ImageIO.read(new File("head.png"));
            pipeLength = ImageIO.read(new File("part.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void paintComponent(Graphics graphics) {
        graphics.setColor(Color.CYAN);
        graphics.fillRect(0, 0, Main.WIDTH, Main.HEIGHT);
        bird.update(graphics);

        for (Rectangle rectangle: rects) {
            Graphics2D g2d = (Graphics2D) graphics;
            g2d.setColor(Color.GREEN);
            g2d.fillRect(rectangle.x, rectangle.y, rectangle.width, rectangle.height);
            AffineTransform old = g2d.getTransform();
            g2d.translate(rectangle.x + PIPE_WEIGHT / 2, rectangle.y + PIPE_HEIGHT / 2);
            if (rectangle.y < Main.HEIGHT / 2) {
                g2d.translate(0, rectangle.height);
                g2d.rotate(Math.PI);
            }
//            g2d.fillRect( -PIPE_WEIGHT / 2, -PIPE_HEIGHT / 2, PIPE_WEIGHT, PIPE_HEIGHT);
//            g2d.fillRect( -PIPE_WEIGHT / 2, -PIPE_HEIGHT / 2, PIPE_WEIGHT, rectangle.height);
            g2d.drawImage(pipeHead,-PIPE_WEIGHT / 2, -PIPE_HEIGHT / 2, PIPE_WEIGHT, PIPE_HEIGHT, null);
            g2d.drawImage(pipeLength,-PIPE_WEIGHT / 2, -PIPE_HEIGHT / 2, PIPE_WEIGHT, rectangle.height + 50, null);
            g2d.setTransform(old);

        }

        graphics.setFont(scoreFont);
        graphics.setColor(Color.BLACK);
        graphics.drawString("Score: " + main.getScore(), 10, 20);

        if (main.paused()) {
            graphics.setFont(pauseFont);
            graphics.setColor(Color.MAGENTA);
            graphics.drawString("PAUSED", Main.WIDTH / 2 - 100, Main.HEIGHT /  2 - 100);
        }


    }
}
