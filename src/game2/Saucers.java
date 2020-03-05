package game2;

import utilities.Vector2D;
import java.awt.*;

public class Saucers extends Ship {

    public Saucers(Controller ctrl) {
        super(new Vector2D(300, 40), new Vector2D(0, 0), new Vector2D(0, 1), ctrl, 8);
    }

    @Override
    public void hit() {
        
    }

    @Override
    public void draw(Graphics2D g2) {
        g2.fillOval((int)position.x, (int) position.y, (int) radius, (int) radius);
    }
}
