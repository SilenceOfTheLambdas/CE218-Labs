package game2;

import utilities.Vector2D;

import java.awt.*;
import java.awt.geom.AffineTransform;

public class Ship {
    public static final int RADIUS = 8;

    // rotation velocity in radians per second
    public static final double STEER_RATE = 2* Math.PI;

    // acceleration when thrust is applied
    public static final double MAG_ACC = 0.00000001;

    // constant speed loss factor
    public static final double DRAG = 0.01;

    public static final Color COLOR = Color.cyan;

    public Vector2D position; // on frame
    public Vector2D velocity; // per second
    // direction in which the nose of the ship is pointing
    // this will be the direction in which thrust is applied
    // it is a unit vector representing the angle by which the ship has rotated
    public Vector2D direction;

    // controller which provides an Action object in each frame
    private Controller ctrl;

    public Ship(Controller ctrl) {
        this.ctrl = ctrl;
        position = new Vector2D(320, 320);
        velocity = new Vector2D(0, 0).mult(10);
        direction = new Vector2D(320, 320);
    }

    public Ship() {
        position = new Vector2D().set(Constants.FRAME_HEIGHT/2, Constants.FRAME_WIDTH/2);
        velocity = new Vector2D().mult(10);
        direction = new Vector2D(0, 0);
    }

    public void update() {
        ctrl.action();
        System.out.println("Direction: " + direction.toString() + " " + "Velocity: "  + velocity.toString() + " " + "Position: " + position.toString());

        if (ctrl.action().turn == 1) {
            direction.rotate((1 * STEER_RATE) / Constants.DT);
            System.out.println("Direction: " + direction.toString());
        } else if (ctrl.action().turn == -1) {
            direction.rotate((-STEER_RATE) / Constants.DT);
            System.out.println("Direction: " + direction.toString());
        }

        if (ctrl.action().thrust == 0) velocity.set(0, 0);

        if (ctrl.action().thrust == 1) {
            velocity.addScaled(direction, ((MAG_ACC * 1) / Constants.DT) - DRAG);
            System.out.println("Velocity: " + velocity.toString());
        }

        position.addScaled(velocity, Constants.DT);
    }

    public void draw(Graphics2D g) {
        AffineTransform at = g.getTransform();
        g.translate(position.x, position.y);
        double rot = direction.angle() + Math.PI / 2;
        g.rotate(rot);
        g.scale(10, 10);
        g.setColor(COLOR);
        g.fillPolygon(XP, YP, XP.length);
        if (thrusting) {
            g.setColor(Color.red);
            g.fillPolygon(XPTHRUST, YPTHRUST, XPTHRUST.length);
        }
        g.setTransform(at);
    }
}
