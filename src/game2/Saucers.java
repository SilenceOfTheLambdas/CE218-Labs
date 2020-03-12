package game2;

import utilities.Vector2D;
import java.awt.*;

public class Saucers extends Ship {

    Controller ctr;
    PlayerShip playerShip;

    private double shootRange = 500;

    public Saucers(Controller ctrl, PlayerShip playerShip) {
        super(new Vector2D(300, 40), new Vector2D(0, 0), new Vector2D(0, 1), ctrl, 8);
        this.ctr = ctrl;
        this.playerShip = playerShip;
    }

    @Override
    public void hit() {
        if (Game.lives == 1) {
            super.hit();
        }
        Game.lives -= 1;
    }

    @Override
    public void update() {
        super.update();
        direction.rotate(STEER_RATE * ctrl.action().turn * Constants.DT);
        velocity.addScaled(direction, ctrl.action().thrust * MAG_ACC * Constants.DT);
        velocity.mult(1 - (DRAG * Constants.DT));
        if (ctrl.action().shoot && time <= 0) {
            mkBullet();
            ctrl.action().shoot = false;
            time += (int) fireDelay;
        }

        if (inRange()) {
            double angle = position.angle(playerShip.position);
            direction.rotate(angle);
            ctrl.action().thrust = 0;
            ctrl.action().turn = 0;
            ctrl.action().shoot = true;
        }
        System.out.println(inRange());
    }

    private boolean inRange() {
        // Is the ship within the saucers range?
        if (this.position.dist(playerShip.position) <= shootRange) {
            return true;
        }
        return false;
    }

    @Override
    Bullet mkBullet() {
        return super.mkBullet();
    }

    @Override
    public void draw(Graphics2D g2) {
        g2.fillOval((int)position.x, (int) position.y, (int) radius, (int) radius);
        g2.drawOval((int) position.x, (int) position.y, (int) shootRange, (int) shootRange);
    }
}
