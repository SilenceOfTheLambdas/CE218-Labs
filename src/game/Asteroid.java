package game;

import utilities.SoundManager;
import utilities.Sprite;
import utilities.Vector2D;
import java.awt.*;
import java.util.ArrayList;

import static game.Constants.*;

public class Asteroid extends GameObject {
    public static int RADIUS = 30;
    public static final double MAX_SPEED = 100;
    int time;
    public static Game game;
    boolean isFinal = false;

    Image im = ASTEROID1;
    Sprite asteroidSprite;

//    This will store any fragments of an asteroid; after it has been hit
    public static ArrayList<Asteroid> spawnedAsteroids = null;

    public Asteroid(double x, double y, double vx, double vy, int rad, boolean isFinal) {
        super(new Vector2D(x, y), new Vector2D(vx, vy), rad);
        RADIUS = rad;
        this.isFinal = isFinal;
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

        return new Asteroid(x, y, vx, vy, RADIUS, false);
    }

    @Override
    public void hit() {
//        Play sound when asteroid is hit
        SoundManager.play(SoundManager.bangMedium);
        super.hit();

        Game.incScore(20);
        Game.addScore();

        game.killCount += 1;
        if (!isFinal) {
            genMediumAsteroids();
            Game.incScore(30);
            Game.addScore();
        }
    }

    public void genMediumAsteroids() {
//        This method will generate medium sized asteroids when a large one is hit
        spawnedAsteroids = new ArrayList<>();
        spawnedAsteroids.add(new Asteroid(position.x + 2, position.y + 2, Math.random() * MAX_SPEED,
                Math.random() * MAX_SPEED, 20, true));
        spawnedAsteroids.add(new Asteroid(position.x + 2, position.y + 2, Math.random() * MAX_SPEED,
                Math.random() * MAX_SPEED, 20, true));
    }

    public void update() {
        time -= deltaTime;
        position.addScaled(velocity, DT);
        position.set((position.x + FRAME_WIDTH) % FRAME_WIDTH, (position.y + FRAME_HEIGHT) % FRAME_HEIGHT);

        if (isFinal && time <= 0) {
            // If this asteroid is a medium asteroid
            hit();
            time += 700;
        }
        super.update();
    }

    public void draw(Graphics2D g) {
        asteroidSprite.draw(g);
    }
}
