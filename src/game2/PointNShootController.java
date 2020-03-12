package game2;

public class PointNShootController implements Controller {
    Action action = new Action();

    @Override
    public Action action() {
        return action;
    }
}
