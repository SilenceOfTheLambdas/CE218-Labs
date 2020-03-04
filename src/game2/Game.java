package game2;

import utilities.JEasyFrame;
import utilities.SoundManager;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import static game2.Constants.DELAY;

public class Game {

    /**
     * This is the player's overall score for all levels
     * It is calculated as follows:
     * Large asteroid = 5 points
     * Medium asteroid = 10 points
     * Small asteroid = 15 points
     *
     * To obtain an extra life a total of: (N_INITIAL_ASTEROIDS * 10) points is needed
     */
    public static int gameScore;
    public int level; // The current level
    public static int lives = 2; // This is the number of lives the player has
    public int killCount = 0; // The number of asteroids the player has killed
    public static boolean canStart = false; // This will dictate whether the player damage system starts

    public Ship ship;
    public Keys ctrl;
    public static int N_INITIAL_ASTEROIDS = 10;
    public List<GameObject> objects;
    public List<GameObject> alive;

    public Game() {
        objects = new ArrayList<>();
        for (int i = 0; i < N_INITIAL_ASTEROIDS; i++) {
            objects.add(Asteroid.makeRandomAsteroid());
        }
        ctrl = new Keys();
        ship = new Ship(ctrl);
        objects.add(ship);
        Asteroid.setGame(this);
    }

    public static void main(String[] args) throws Exception {
        Game game = new Game();
        View view = new View(game);
        JEasyFrame frame = new JEasyFrame(view, "Asteroid Game");
        frame.addKeyListener(game.ctrl);
        LevelHandler levelHandler = new LevelHandler(game);

//        Scoring UI
        JLabel scoreLabel = new JLabel("Score: ");
        scoreLabel.setForeground(Color.black);
        JLabel livesLabel = new JLabel("Lives: ");
        JLabel levelLabel = new JLabel("Level: ");
        JPanel scorePanel = new JPanel();
        scorePanel.add(scoreLabel, BorderLayout.WEST);
        scorePanel.add(livesLabel, BorderLayout.CENTER);
        scorePanel.add(levelLabel, BorderLayout.EAST);
        frame.add(scorePanel, BorderLayout.NORTH);

//        Timer for player start
        Timer startTimer = new Timer();
        startTimer.schedule(new TimerTask() {
            @Override
            public void run() {
//                If 2 seconds has passed since bullet has been spawned
                Game.canStart = true;
            }
        }, 2000);

        //noinspection InfiniteLoopStatement
        while (true) {
            game.update();
            levelHandler.update();
            scoreLabel.setText("Score: " + Game.getGameScore());
            livesLabel.setText("Lives: " + lives);
            levelLabel.setText("Level: " + game.level);
            view.repaint();
            Thread.sleep(DELAY);
        }
    }

    public static void incScore(int amount) {
        gameScore += amount;
    }

    public static int getGameScore() {
        return gameScore;
    }

    public static void addScore() {
//        This will handle logic relating to the players score
        if (gameScore == (N_INITIAL_ASTEROIDS * 10)) {
            lives++;
        }
    }

    public void update() {
        alive = new ArrayList<>();
        for (GameObject a : objects) {
            if (!a.dead) {
                alive.add(a);
            }
        }

        if (Asteroid.spawnedAsteroids != null) {
            alive.addAll(Asteroid.spawnedAsteroids);
            Asteroid.spawnedAsteroids = null;
        }

        if (ship.bullet != null && !ship.bullet.dead) {
            alive.add(ship.bullet); // Only add objects that are not dead
            ship.bullet = null; // Then set ship.bullet to NULL
        }

        synchronized (Game.class) {
            objects.clear();
            objects.addAll(alive);
        }
        for (int i = 0; i < alive.size(); i++) {
            GameObject g = alive.get(i);
            for (int j = i + 1; j < alive.size(); j++) {
                g.collisionHandling(alive.get(j));
            }
        }
        for (GameObject a : alive) {
            a.update(); // For all alive objects; update them
        }
    }
}
