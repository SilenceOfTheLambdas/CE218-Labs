package game2;

import utilities.Action;

public class AimNShoot implements Controller {

    public static final double SHOOTING_DISTANCE = (Math.PI * 2) * 40; // The distance in which the saucer can shoot
    public static final double SHOOTING_ANGLE = 130; // The maximum angle the saucer can shoot
    Game game;
    Action action;
    PlayerShip playerShip;

     public AimNShoot(Game g, Action a, PlayerShip player) {
         this.game = g;
         this.action = a;
         this.playerShip = player;
     }

    //    Find the closest target. Uses canHit() to ignore non-target objects
    public GameObject findTarget(GameObject ship, Iterable<GameObject> gameObjects) {
        return null;
    }

//    Compute turn value required to rotate ship towards target
    public int aim(GameObject ship, GameObject target) {
         return 1;
    }

    @Override
    public Action action() {
        return action;
    }
}
