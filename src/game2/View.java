package game2;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.AffineTransform;

public class View extends JComponent {
    private Game game;

//    Sprite stuffs
    Image im = Constants.MILKYWAY1;
    AffineTransform bgTrans;

    public View(Game game) {
        this.game = game;

        double imWidth = im.getWidth(null);
        double imHeight = im.getHeight(null);
        double stretchX = (imWidth > Constants.FRAME_WIDTH ? 1 : Constants.FRAME_WIDTH / imWidth);
        double stretchY = (imHeight > Constants.FRAME_HEIGHT ? 1 : Constants.FRAME_HEIGHT / imHeight);

        bgTrans = new AffineTransform();
        bgTrans.scale(stretchX, stretchY);
    }

    @Override
    public void paintComponent(Graphics g0) {
        Graphics2D g = (Graphics2D) g0;
        // paint the background
        g.drawImage(im, bgTrans, null);

        synchronized (Game.class) {
            for (GameObject obj : game.objects) {
                obj.draw(g);
            }
        }
    }

    @Override
    public Dimension getPreferredSize() {
        return Constants.FRAME_SIZE;
    }
}
