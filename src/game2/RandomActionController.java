package game2;

public class RandomActionController implements Controller {
    Action action = new Action();

    @Override
    public Action action() {
        action.shoot = Math.random() * 10 <= 5;
        action.thrust = Math.random() * 10 <= 5 ? 0 : 1;
        action.turn = Math.random() * 10 <= 5 ? -1 : Math.random() * 10 == 5 ? 0 : 1;
        return action;
    }
}
