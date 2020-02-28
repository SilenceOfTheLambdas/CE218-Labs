package game2;

import utilities.JEasyFrame;

import java.util.ArrayList;
import java.util.List;

import static game2.Constants.DELAY;

public class Game {
    public Ship ship;
    public Keys ctrl;
    public static final int N_INITIAL_ASTEROIDS = 20;
    public List<GameObject> objects;

    public Game() {
        objects = new ArrayList<>();
        for (int i = 0; i < N_INITIAL_ASTEROIDS; i++) {
            objects.add(Asteroid.makeRandomAsteroid());
        }

        ctrl = new Keys();
        ship = new Ship(ctrl);
        objects.add(ship);
    }

    public static void main(String[] args) throws Exception {
        Game game = new Game();
        View view = new View(game);
        new JEasyFrame(view, "Asteroid Game").addKeyListener(game.ctrl);
        //noinspection InfiniteLoopStatement
        while (true) {
            game.update();
            view.repaint();
            Thread.sleep(DELAY);
        }
    }

    public void update() {
        List<GameObject> alive = new ArrayList<>();

        for (GameObject a : objects) {
            if (!a.dead) {
                alive.add(a);
            }
        }
        if (ship.bullet != null && !ship.bullet.dead) {
            alive.add(ship.bullet); // Only add objects that are not dead
            ship.bullet = null; // Then set ship.bullet to NULL
        }
        synchronized (Game.class) {
            objects.clear();
            objects.addAll(alive);
        }

        for (GameObject a : alive) a.update(); // For all alive objects; update them
    }
}
