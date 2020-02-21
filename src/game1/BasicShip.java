package game1;

import utilities.BasicController;
import utilities.Vector2D;
import java.awt.*;
import java.sql.Time;

public class BasicShip {

    public static final int RADIUS = 8;

    // rotation velocity in radians per second
    public static final double STEER_RATE = 2* Math.PI;

    // acceleration when thrust is applied
    public static final double MAG_ACC = 1;

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
    private BasicController ctrl;

    public BasicShip() {
//        Setup a new ship in the standard starting position
        position = new Vector2D(Constants.FRAME_WIDTH / 2, Constants.FRAME_HEIGHT / 2);
    }

    public BasicShip(BasicController ctrl) {
        this.ctrl = ctrl;
        position = new Vector2D(320, 240);
        velocity = new Vector2D();
        direction = new Vector2D(0, 0);
    }

    public void update() {
        if (ctrl.action().thrust > 0) {
            velocity.addScaled(position, MAG_ACC + ctrl.action().thrust - DRAG);
            position.addScaled(velocity, Constants.DT).wrap(Constants.FRAME_WIDTH, Constants.FRAME_HEIGHT);
        }
        if (ctrl.action().thrust < 0) {
            position.subtract(velocity).wrap(Constants.FRAME_WIDTH, Constants.FRAME_HEIGHT);
        }
        if (ctrl.action().turn > 0) {
            direction.rotate(ctrl.action().turn + STEER_RATE + Constants.DT);
        }

        if (ctrl.action().turn == 0 || ctrl.action().thrust == 0) velocity.x = 0; velocity.y = 0; direction.x = 0; direction.y = 0;
    }

    public void draw(Graphics2D ship) {
        ship.setColor(COLOR);
        ship.fillOval((int)position.x - RADIUS, (int)position.y - RADIUS, 2 * RADIUS, 2 * RADIUS);
        ship.fillRect((int)position.x, RADIUS * 3, 20, 50);
    }
}
