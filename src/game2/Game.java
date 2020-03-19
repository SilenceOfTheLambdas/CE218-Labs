package game2;

import utilities.JEasyFrameFull;
import utilities.Keys;
import utilities.View;

import javax.swing.*;
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
     * To obtain an extra life a total of: (N_INITIAL_ASTEROIDS) points is needed
     */
    public static int gameScore;
    public int level; // The current level
    public static int lives = 2; // This is the number of lives the player has
    public int killCount = 0; // The number of asteroids the player has killed
    public static boolean canStart = false; // This will dictate whether the player damage system starts

    static JEasyFrameFull frame;

    public PlayerShip playerShip;
    public Keys ctrl;
    public Saucers saucer;

    public static int N_INITIAL_ASTEROIDS = 10;
    public List<GameObject> objects;
    public List<GameObject> alive;

    public Game() {
        objects = new ArrayList<>();
        for (int i = 0; i < N_INITIAL_ASTEROIDS; i++) {
            objects.add(Asteroid.makeRandomAsteroid());
        }
        ctrl = new Keys();
        playerShip = new PlayerShip(ctrl);
        objects.add(playerShip);
        Asteroid.setGame(this);
        saucer = new Saucers(new PointNShootController(), playerShip);
        objects.add(saucer);
    }

    public static void main(String[] args) throws Exception {
        Game game = new Game();
        View view = new View(game);
        frame = new JEasyFrameFull(view);
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

    public static void confirmExit() {
        int dialogResult = JOptionPane.showConfirmDialog (frame, "Are you sure you want to quit?");
        if (dialogResult == JOptionPane.YES_OPTION) {
            System.exit(1);
        }
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

        if (playerShip.bullet != null) {
            alive.add(playerShip.bullet); // Only add objects that are not dead
            playerShip.bullet = null; // Then set ship.bullet to NULL
        }

        if (saucer.bullet != null) {
            alive.add(saucer.bullet);
            saucer.bullet = null;
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
