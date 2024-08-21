package soulsvania.utils;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;


public class LoadSave {


    public static BufferedImage setup(String imageName) {
        BufferedImage image = null;

        try {

            image = ImageIO.read(LoadSave.class.getClassLoader().getResourceAsStream(imageName + ".png"));


        }catch(IOException e) {
            e.printStackTrace();
        }
        return image;
    }



    //this function will be used to draw out the map data for level data we will be using a color image file to represent each different tile based on color.
    public static BufferedImage[] GetAllLevels() {

        BufferedImage image = setup("soulsvania/levels/1");
        BufferedImage image2 = setup("soulsvania/levels/2");
        BufferedImage image3 = setup("soulsvania/levels/3");

        BufferedImage[] imgs = new BufferedImage[3];

        imgs[0] = image;
        imgs[1] = image2;
        imgs[2] = image3;

        return imgs;
    }

}
