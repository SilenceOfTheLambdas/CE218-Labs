package game1;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class BasicKeys extends KeyAdapter implements BasicController {
    Action action;
    public BasicKeys() {
        action = new Action();
    }

    public Action action() {
        // this is defined to comply with the standard interface
        return action;
    }

    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();
        switch (key) {
            case KeyEvent.VK_UP:
                action.thrust = 1;
                System.out.println("Thrust: " + action.thrust);
                break;
            case KeyEvent.VK_LEFT:
                action.turn = -1;
                System.out.println("Turn: " + action.turn);
                break;
            case KeyEvent.VK_RIGHT:
                action.turn = +1;
                System.out.println("Turn: " + action.turn);
                break;
            case KeyEvent.VK_SPACE:
                action.shoot = true;
                break;
        }
    }

    public void keyReleased(KeyEvent e) {
        // please add appropriate event handling code
        int key = e.getKeyCode();
        switch (key) {
            case KeyEvent.VK_UP:
                action.thrust = 0;
                System.out.println("Thrust: " + action.thrust);
                break;
            case KeyEvent.VK_LEFT:
            case KeyEvent.VK_RIGHT:
                action.turn = 0;
                System.out.println("Turn: " + action.turn);
                break;
            case KeyEvent.VK_SPACE:
                action.shoot = false;
                break;
        }
    }
}
