package game1;

import utilities.Vector2D;

import static game1.Constants.DT;
import static game1.Constants.FRAME_HEIGHT;
import static game1.Constants.FRAME_WIDTH;
import java.awt.Color;
import java.awt.Graphics2D;

public class BasicAsteroid {
    public static final int RADIUS = 10;
    public static final double MAX_SPEED = 100;

    private Vector2D position = new Vector2D();
    private Vector2D velocity = new Vector2D();

    public BasicAsteroid(double x, double y, double vx, double vy) {
        this.position.x = x;
        this.position.y = y;
        this.velocity.x = vx;
        this.velocity.y = vy;
    }

	public static BasicAsteroid makeRandomAsteroid() {
        double x = Math.random() * FRAME_WIDTH;
        double y = Math.random() * FRAME_HEIGHT;
        double vx = Math.random() * MAX_SPEED;
        double vy = Math.random() * MAX_SPEED;

        return new BasicAsteroid(x, y, vx, vy);
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
