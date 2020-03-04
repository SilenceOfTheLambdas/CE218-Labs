package game2;

import utilities.ImageManager;

import java.awt.*;
import java.io.IOException;

public class Constants {
    public static final int FRAME_HEIGHT = 480;
    public static final int FRAME_WIDTH = 640;
    public static final Dimension FRAME_SIZE = new Dimension(
            Constants.FRAME_WIDTH, Constants.FRAME_HEIGHT);
    public static final int DELAY = 20; // in milliseconds
    public static final double DT = DELAY / 1000.0; // in seconds

//    Images
    public static Image ASTEROID1, MILKYWAY1 , MILKYWAY2;
    static {
        try {
            ASTEROID1 = ImageManager.loadImage("asteroid1");
            MILKYWAY1 = ImageManager.loadImage("milkyway1");
            MILKYWAY2 = ImageManager.loadImage("milkyway2");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
