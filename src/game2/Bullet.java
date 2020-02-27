package game2;

import utilities.Vector2D;
import java.awt.*;
import java.util.Timer;
import java.util.TimerTask;

public class Bullet extends GameObject {

    public final double MUZZLE_VELOCITY = 600 * Constants.DT;
    private final static int RADIUS = 3;

    public Vector2D direction;

    public Bullet(Vector2D pos, Vector2D vel) {
        super(pos, vel, RADIUS);
        direction = new Vector2D(position).rotate(position.angle());
    }

    @Override
    public void update() {
        super.update();
        direction.set(position).rotate(position.angle());

        //        Timer for bullet
        Timer bulletTimer = new Timer();
        bulletTimer.schedule(new TimerTask() {
            @Override
            public void run() {
//                If 2 seconds has passed since bullet has been spawned
                Bullet.super.dead = true;
            }
        }, 4000);
        super.hit();
    }

    @Override
    public void draw(Graphics2D g2) {
        g2.setColor(Color.green);
//        g2.fillRect((int) position.x, (int) position.y, 5, 10);
        g2.fillOval((int) position.x, (int) position.y, 10, 10);
    }
}
