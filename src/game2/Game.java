package game2;

import utilities.JEasyFrame;

import java.util.ArrayList;
import java.util.List;

import static game1.Constants.DELAY;

public class Game {
    public Ship ship;
    public Keys ctrl;
    public static final int N_INITIAL_ASTEROIDS = 20;
    public List<Asteroid> asteroids;

    public Game() {
        asteroids = new ArrayList<>();
        for (int i = 0; i < N_INITIAL_ASTEROIDS; i++) {
            asteroids.add(Asteroid.makeRandomAsteroid());
        }

        ctrl = new Keys();
        ship = new Ship(ctrl);
    }

    public static void main(String[] args) throws Exception {
        Game game = new Game();
        View view = new View(game);
        new JEasyFrame(view, "Asteroid Game").addKeyListener(game.ctrl);
        while (true) {
            game.update();
            view.repaint();
            Thread.sleep(DELAY);
        }
    }

    public void update() {
        for (Asteroid a : asteroids) {
            a.update();
        }
        ship.update();
    }
}
