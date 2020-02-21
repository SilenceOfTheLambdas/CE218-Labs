package game1;

import utilities.JEasyFrame;

import java.util.ArrayList;
import java.util.List;

import static game1.Constants.DELAY;

public class BasicGame {
    public BasicShip ship;
    public BasicKeys ctrl;
    public static final int N_INITIAL_ASTEROIDS = 20;
    public List<BasicAsteroid> asteroids;

    public BasicGame() {
        asteroids = new ArrayList<>();
        for (int i = 0; i < N_INITIAL_ASTEROIDS; i++) {
            asteroids.add(BasicAsteroid.makeRandomAsteroid());
        }

        ctrl = new BasicKeys();
        ship = new BasicShip(ctrl);
    }

    public static void main(String[] args) throws Exception {
        BasicGame game = new BasicGame();
        BasicView view = new BasicView(game);
        new JEasyFrame(view, "Asteroid Game").addKeyListener(game.ctrl);
        while (true) {
            game.update();
            view.repaint();
            Thread.sleep(DELAY);
        }
    }

    public void update() {
        for (BasicAsteroid a : asteroids) {
            a.update();
        }
        ship.update();
    }
}
