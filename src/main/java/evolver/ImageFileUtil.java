package evolver;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class ImageFileUtil {

  static int calculateMaxDifference(BufferedImage image) {
    return image.getWidth() * image.getHeight() * 3 * 255;
  }

  static BufferedImage createFourChannelImageClone(File file) throws IOException {
    BufferedImage originalFormatImage = ImageIO.read(file);
    return cloneToSpecificImageType(originalFormatImage);
  }

  static BufferedImage cloneToSpecificImageType(BufferedImage image) {
    int width = image.getWidth();
    int height = image.getHeight();
    int type = BufferedImage.TYPE_4BYTE_ABGR;
    BufferedImage newImage = new BufferedImage(width, height, type);

    Graphics2D g = newImage.createGraphics();
    g.drawImage(image, 0, 0, null);
    g.dispose();
    return newImage;
  }

  static BufferedImage createFourChannelBlankImage(BufferedImage image) {
    return new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TYPE_4BYTE_ABGR);
  }

  static void saveBufferedImageToFile(BufferedImage image, File file) throws IOException {
    ImageIO.write(image, "png", file);
  }

  static void saveBufferedImageToFile(BufferedImage image, String pathToNewFile) throws IOException {
    ImageIO.write(image, "png", new File(pathToNewFile));
  }

}
