package game2;

import utilities.SoundManager;
import utilities.Sprite;
import utilities.Vector2D;
import java.awt.*;
import java.util.ArrayList;
import static game2.Constants.*;

public class Asteroid extends GameObject {
    public static final int RADIUS = 15;
    public static final double MAX_SPEED = 100;
    public boolean hit = false;

    public static Game game;

    boolean hitMed = false;
    boolean hitSmall = false;

    Image im = ASTEROID1;
    Sprite asteroidSprite;

//    This will store any fragments of an asteroid; after it has been hit
    public static ArrayList<Asteroid> spawnedAsteroids = null;

    public Asteroid(double x, double y, double vx, double vy, int rad) {
        super(new Vector2D(x, y), new Vector2D(vx, vy), rad);

        asteroidSprite = new Sprite(im, this.position, new Vector2D(0, 0), RADIUS, RADIUS);
    }

    public static void setGame(Game game) {
        Asteroid.game = game;
    }

    public static Asteroid makeRandomAsteroid() {
        double x = Math.random() * FRAME_WIDTH;
        double y = Math.random() * FRAME_HEIGHT;
        double vx = Math.random() * MAX_SPEED;
        double vy = Math.random() * MAX_SPEED;

        return new Asteroid(x, y, vx, vy, RADIUS);
    }

    @Override
    public void hit() {
//        Play sound when asteroid is hit
        SoundManager.play(SoundManager.bangMedium);

        super.hit();
        Game.incScore(20);
        Game.addScore();
        game.killCount += 1;
//        FIXME Figure out a way to keep track of when a medium asteroid is made
//        if (super.hitCount == 1) genMediumAsteroids();
//        if (super.hitCount == 2) genSmallAsteroids();
    }

    public void genMediumAsteroids() {
//        This method will generate medium sized asteroids when a large one is hit
        hitMed = true;
        spawnedAsteroids = new ArrayList<>();
        spawnedAsteroids.add(new Asteroid(position.x + 2, position.y + 2, Math.random() * MAX_SPEED, Math.random() * MAX_SPEED, 10));
        spawnedAsteroids.add(new Asteroid(position.x + 2, position.y + 2, Math.random() * MAX_SPEED, Math.random() * MAX_SPEED, 10));
    }

    public void genSmallAsteroids() {
        hitMed = false;
        hitSmall = true;
        spawnedAsteroids = new ArrayList<>();
        spawnedAsteroids.add(new Asteroid(position.x + 2, position.y + 2, Math.random() * MAX_SPEED, Math.random() * MAX_SPEED, 7));
        spawnedAsteroids.add(new Asteroid(position.x + 2, position.y + 2, Math.random() * MAX_SPEED, Math.random() * MAX_SPEED, 7));
        super.hitCount = 0;
    }

    public void update() {
        position.addScaled(velocity, DT);
        position.set((position.x + FRAME_WIDTH) % FRAME_WIDTH, (position.y + FRAME_HEIGHT) % FRAME_HEIGHT);
        super.update();
    }

    public void draw(Graphics2D g) {
//        g.setColor(Color.red);
//        g.fillOval((int) ((int) position.x - super.radius), (int) ((int) position.y - super.radius), (int) (2 * super.radius), (int) (2 * super.radius));
        asteroidSprite.draw(g);
    }
}
