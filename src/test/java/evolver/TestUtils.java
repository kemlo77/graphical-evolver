package evolver;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class TestUtils {


    static void skapaBildMedFarg(int width, int height, int r, int g, int b, String pathToNewFile) {
        BufferedImage newImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);

        for (int x = 0; x < width; x++)
            for (int y = 0; y < height; y++) {


                int newColor = (255 << 24) | (r << 16) | (g << 8) | b;
                newImage.setRGB(x, y, newColor);
            }

        System.out.println("Sparar diffad bild till: " + pathToNewFile);
        try{
            ImageIO.write(newImage, "png", new File(pathToNewFile));
        }catch(IOException e){
            System.out.println("Kunde inte spara bildfilen till: " +pathToNewFile);
            e.printStackTrace();
        }


    }

    public static void main(String[] args) {
        skapaBildMedFarg(10,20,255,0,0,"red.png");
    }

}
