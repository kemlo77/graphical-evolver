package evolver;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

class TestUtils {


  /**
   * Creates a PNG image file given dimensions and color in RGB.
   *
   * @param width width of image
   * @param height height of image
   * @param r red (0-255)
   * @param g green (0-255)
   * @param b blue (0-255)
   * @param pathToNewFile the path to the new file including file name.
   */
  static void createPngFile(int width, int height, int r, int g, int b, String pathToNewFile) {
    BufferedImage newImage = new BufferedImage(width, height, BufferedImage.TYPE_4BYTE_ABGR);

    for (int x = 0; x < width; x++) {
      for (int y = 0; y < height; y++) {

        int newColor = (255 << 24) | (r << 16) | (g << 8) | b;
        newImage.setRGB(x, y, newColor);
      }
    }

    System.out.println("Sparar diffad bild till: " + pathToNewFile);
    try {
      ImageIO.write(newImage, "png", new File(pathToNewFile));
    } catch (IOException e) {
      System.out.println("Kunde inte spara bildfilen till: " + pathToNewFile);
      e.printStackTrace();
    }


  }

  static BufferedImage createBufferedImage(int width, int height, int r, int g, int b) {
    //TODO: r,g,b borde vara av typen byte
    BufferedImage bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_4BYTE_ABGR);
    int newColor = (255 << 24) | (r << 16) | (g << 8) | b;
    for (int x = 0; x < width; x++) {
      for (int y = 0; y < height; y++) {
        bufferedImage.setRGB(x, y, newColor);
      }
    }
    return bufferedImage;
  }

  static BufferedImage createBufferedImage(int r, int g, int b) {
    return createBufferedImage(10,10,r,g,b);
  }

  static BufferedImage createBufferedImage(Color color) {
    return createBufferedImage(10,10,color.getRed(),color.getGreen(),color.getBlue());
  }


}
