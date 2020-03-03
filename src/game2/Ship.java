package game2;

import utilities.Vector2D;

import java.awt.*;
import java.awt.geom.AffineTransform;

public class Ship extends GameObject {
    public static final int RADIUS = 6;

    // rotation velocity in radians per second
    public static final double STEER_RATE = 2* Math.PI;

    // acceleration when thrust is applied
    public static final double MAG_ACC = 400;

    // constant speed loss factor
    public static final double DRAG = 0.01;

    public static final Color COLOR = Color.cyan;

    // direction in which the nose of the ship is pointing
    // this will be the direction in which thrust is applied
    // it is a unit vector representing the angle by which the ship has rotated
    public Vector2D direction;

    // controller which provides an Action object in each frame
    private Controller ctrl;

    //    The bullet object
    Bullet bullet = null;

    //    The fire-rate
    double fireDelay = 700; // 700 = 0.7 seconds

    private static int time = 0;

    public Ship(Controller ctrl) {
        super(new Vector2D(Constants.FRAME_WIDTH / 2, Constants.FRAME_HEIGHT / 2), new Vector2D(0, 0), RADIUS);
        direction = new Vector2D(position).rotate(-90);
        this.ctrl = ctrl;
    }

    public void update() {
        time -= deltaTime;
        if (ctrl.action().shoot && time <= 0) {
            mkBullet();
            ctrl.action().shoot = false;
            time += (int) fireDelay;
        }

        direction.rotate(STEER_RATE * ctrl.action().turn * Constants.DT);
        velocity.addScaled(direction, ctrl.action().thrust * MAG_ACC * Constants.DT);
        velocity.mult((1 - DRAG) * Constants.DT);
        super.update();
    }

    private void mkBullet() {
//        FIXME: bullets are spawning in odd locations
        bullet = new Bullet(new Vector2D(position).add(0, -13), new Vector2D(velocity));
        bullet.velocity.addScaled(direction, bullet.MUZZLE_VELOCITY);
        bullet.velocity.mult((1 - DRAG) * Constants.DT);
        super.update();
    }

    @Override
    public void hit() {
        if (Game.lives == 1) {
            super.hit();
        }
        Game.lives -= 1;
    }

    public void draw(Graphics2D g) {
//        Positions for the new ship
        int[] XP = new int[]{0, -1, 0, 1};
        int[] YP = new int[]{-1, 2, 1, 2};

        int[] XPTHRUST = new int[]{-1, 1, 0, 0};
        int[] YPTHRUST = new int[]{3, 3, 4, 4};

        AffineTransform at = g.getTransform();
        g.translate(position.x, position.y);
        double rot = direction.angle() + Math.PI / 2;
        g.rotate(rot);
        g.scale(10, 10);
        g.setColor(COLOR);
        g.fillPolygon(XP, YP, XP.length);
        if (ctrl.action().thrust > 0) {
            g.setColor(Color.orange); // Colour of the 'jet-stream'
            g.fillPolygon(XPTHRUST, YPTHRUST, XPTHRUST.length);
        }
        g.setTransform(at);
    }
}
