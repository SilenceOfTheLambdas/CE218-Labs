package utilities;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class Keys extends KeyAdapter implements Controller {

    Action action;
    public Keys() {
        action = new Action();
    }

    @Override
    public Action action() {
        return action;
    }

    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();
        switch (key) {
            case KeyEvent.VK_UP:
                action.thrust = +1;
                System.out.println("Adding thrust of " + action.thrust);
                break;
            case KeyEvent.VK_LEFT:
                action.turn = -1;
                System.out.println("Turning " + action.turn);
                break;
            case KeyEvent.VK_RIGHT:
                action.turn = +1;
                System.out.println("Turning " + action.turn);
                break;
            case KeyEvent.VK_DOWN:
                action.thrust = -1;
                System.out.println("Thrust " + action.thrust);
                break;
            case KeyEvent.VK_SPACE:
                action.shoot = true;
                System.out.println("Shooting.....");
                break;
        }
    }

    public void keyReleased(KeyEvent e) {
        int key = e.getKeyCode();
        switch (key) {
            case KeyEvent.VK_UP:
                action.thrust = 0;
                break;
            case KeyEvent.VK_LEFT:
            case KeyEvent.VK_RIGHT:
                action.turn = 0;
                break;
            case KeyEvent.VK_SPACE:
                action.shoot = false;
                break;
        }
    }
}
