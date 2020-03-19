package game;

import utilities.Vector2D;

import java.awt.*;
import java.util.Timer;
import java.util.TimerTask;

public class Bullet extends GameObject {

    public final double MUZZLE_VELOCITY = 150; // 150 seems like a nice value
    private final static int RADIUS = 10;

    public Vector2D direction;

    public Bullet(Vector2D pos, Vector2D vel) {
        super(pos, vel, RADIUS);
        this.direction = new Vector2D(pos).rotate(pos.angle());
    }

    @Override
    public void update() {
        super.update();
        this.direction.rotate(position.angle());
        //        Timer for bullet
        Timer bulletTimer = new Timer();
        bulletTimer.schedule(new TimerTask() {
            @Override
            public void run() {
//                If 2 seconds has passed since bullet has been spawned
                Bullet.super.dead = true;
            }
        }, 2000);
    }

    @Override
    public void draw(Graphics2D g2) {
        g2.setColor(Color.green);
        g2.fillOval((int) position.x - RADIUS, (int) position.y - RADIUS, RADIUS, RADIUS);
    }
}
