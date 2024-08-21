package soulsvania.utils;

import java.awt.*;
import java.awt.image.BufferedImage;

public class UtilityTool {

    /*
     * This class is utilized to handle improved rendering times.
     */
    //Scales image to better rendering times
    public BufferedImage scaleImage(BufferedImage original, int width , int height) {

        BufferedImage scaledImage = new BufferedImage(width, height, original.getType());
        Graphics2D g2 = scaledImage.createGraphics();
        g2.drawImage(original, 0, 0, width, height, null);

        return scaledImage;
    }
}
