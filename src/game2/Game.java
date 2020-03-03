package game2;

import utilities.JEasyFrame;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

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
    public static int lives = 2; // This is the number of lives the player has

    public Ship ship;
    public Keys ctrl;
    public static final int N_INITIAL_ASTEROIDS = 10;
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

//        Scoring UI
        JLabel scoreLabel = new JLabel("Score: ");
        scoreLabel.setForeground(Color.black);
        JLabel livesLabel = new JLabel("Lives: ");
        JPanel scorePanel = new JPanel();
        scorePanel.add(scoreLabel, BorderLayout.WEST);
        scorePanel.add(livesLabel, BorderLayout.EAST);
        frame.add(scorePanel, BorderLayout.NORTH);

        //noinspection InfiniteLoopStatement
        while (true) {
            game.update();
            scoreLabel.setText("Score: " + gameScore);
            livesLabel.setText("Lives: " + lives);
            view.repaint();
            Thread.sleep(DELAY);
        }
    }

    public void incScore(int amount) {
        gameScore += amount;
    }

    public int getGameScore() {
        return gameScore;
    }

    public static void addScore() {
//        This will handle logic relating to the players score
        if (gameScore >= (N_INITIAL_ASTEROIDS * 10)) {
            lives += 1;
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
        for (GameObject a : alive) a.update(); // For all alive objects; update them

//        Update the scoring system
        addScore();
    }
}
