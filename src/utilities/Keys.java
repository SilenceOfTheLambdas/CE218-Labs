package utilities;

import game2.Controller;
import game2.Game;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class Keys extends KeyAdapter implements Controller {
    Action action;
    public Keys() {
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
                SoundManager.startThrust();
                action.thrust = 1;
                break;
            case KeyEvent.VK_LEFT:
                SoundManager.startThrust();
                action.turn = -1;
                break;
            case KeyEvent.VK_RIGHT:
                SoundManager.startThrust();
                action.turn = +1;
                break;
            case KeyEvent.VK_SPACE:
                action.shoot = true;
                break;
            case KeyEvent.VK_ESCAPE:
                Game.confirmExit();
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
