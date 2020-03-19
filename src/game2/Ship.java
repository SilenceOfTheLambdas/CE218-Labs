package game2;

import utilities.SoundManager;
import utilities.Vector2D;

public abstract class Ship extends GameObject {

    // direction in which the nose of the ship is pointing
    // this will be the direction in which thrust is applied
    // it is a unit vector representing the angle by which the ship has rotated
    public Vector2D direction;

    // rotation velocity in radians per second
    public static final double STEER_RATE = 2* Math.PI;

    // acceleration when thrust is applied
    public static final double MAG_ACC = 80;

    // constant speed loss factor
    public static final double DRAG = 0.2;

    // controller which provides an Action object in each frame
    public Controller ctrl;

    //    The bullet object
    public Bullet bullet = null;

    //    The fire-rate
    double fireDelay = 700; // 700 = 0.7 seconds

    static int time = 0;

    public Ship(Vector2D position, Vector2D velocity, Vector2D direction, Controller ctrl, double radius) {
        super(position, velocity, radius);
        this.direction = direction;
        this.ctrl = ctrl;
    }

    public void update() {
        time -= deltaTime;
        if (ctrl.action().thrust == 0) {
            SoundManager.stopThrust();
        }
        if (ctrl.action().shoot && time <= 0) {
            mkBullet();
            ctrl.action().shoot = false;
            time += (int) fireDelay;
        }
        super.update();
    }

    Bullet mkBullet() {
        this.bullet = new Bullet(new Vector2D(position).addScaled(direction, 40), new Vector2D(velocity));
        bullet.velocity.addScaled(direction, bullet.MUZZLE_VELOCITY);
        SoundManager.fire();

        return bullet;
    }

}
