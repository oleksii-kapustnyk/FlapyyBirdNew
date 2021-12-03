package com.ua;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class Bird {
    public float x, y, vx, vy;
    public static final int RAD = 25;
    private Image birdImage;

    public Bird() {
        x = Main.WIDTH / 2;
        y = Main.HEIGHT / 2;

        try {
            birdImage = ImageIO.read(new File("bird.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void update(Graphics graphics) {
        graphics.setColor(Color.BLACK);
//        graphics.drawRect(Math.round(x - RAD), Math.round(y - RAD), 2 * RAD, 2 * RAD);
        graphics.drawImage(birdImage, Math.round(x - RAD), Math.round(y - RAD), 2 * RAD, 2 * RAD, null);
    }

    public void jump() {
        //height of jump. -8 easy. -6 normal. -4 hard
        vy = -8;
    }

    public void physics() {
        x += vx;
        y += vy;
        //speed of falling. 0.5 easy. 1+ hard
        vy += 0.5f;
    }

    public void reset() {
        x = Main.WIDTH / 2;
        y = Main.WIDTH / 2;
        vx = 0;
        vy = 0;
    }
}
