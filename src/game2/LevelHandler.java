package game2;

import static game2.Game.N_INITIAL_ASTEROIDS;

public class LevelHandler {

    private Game game;

    public LevelHandler(Game game) {
        this.game = game;
    }

    public void update() {
        if (game.killCount == N_INITIAL_ASTEROIDS) {
            game.level += 1;
            game.killCount = 0;
//                if no asteroids exist in the level
            N_INITIAL_ASTEROIDS += 5; // Increase the number of asteroids in the level
            for (int i = 0; i < N_INITIAL_ASTEROIDS; i++) {
//                For each one; spawn a new asteroid
                game.objects.add(Asteroid.makeRandomAsteroid());
            }
        }
    }

}
