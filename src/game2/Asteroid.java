package game2;

import utilities.Vector2D;

import java.awt.*;

import static game1.Constants.*;

public class Asteroid extends GameObject {
    public static final int RADIUS = 10;
    public static final double MAX_SPEED = 100;

    public Asteroid(double x, double y, double vx, double vy) {
        super(new Vector2D(x, y), new Vector2D(vx, vy), RADIUS);
    }

	public static Asteroid makeRandomAsteroid() {
        double x = Math.random() * FRAME_WIDTH;
        double y = Math.random() * FRAME_HEIGHT;
        double vx = Math.random() * MAX_SPEED;
        double vy = Math.random() * MAX_SPEED;

        return new Asteroid(x, y, vx, vy);
    }

    public void update() {
        position.addScaled(velocity, DT);
        position.set((position.x + FRAME_WIDTH) % FRAME_WIDTH, (position.y + FRAME_HEIGHT) % FRAME_HEIGHT);
    }

    public void draw(Graphics2D g) {
        g.setColor(Color.red);
        g.fillOval((int) position.x - RADIUS, (int) position.y - RADIUS, 2 * RADIUS, 2 * RADIUS);
    }
}
