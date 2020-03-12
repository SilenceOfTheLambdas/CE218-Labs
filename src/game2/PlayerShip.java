package game2;

import utilities.Vector2D;

import java.awt.*;
import java.awt.geom.AffineTransform;

public class PlayerShip extends Ship {
    public static final int RADIUS = 6;

    public static final Color COLOR = Color.cyan;


    public PlayerShip(Controller ctrl) {
        super(new Vector2D(Constants.FRAME_WIDTH / 2, Constants.FRAME_HEIGHT / 2), new Vector2D(0, 0), new Vector2D(0, -1), ctrl, RADIUS);
    }

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
    }

    @Override
    public void hit() {
        if (Game.lives == 1) {
            super.hit();
        }
        Game.lives -= 1;
    }

    @Override
    Bullet mkBullet() {
        return super.mkBullet();
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
