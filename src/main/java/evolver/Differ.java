package evolver;

import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

class Differ {


  static void skapaDiffBild(BufferedImage image1, BufferedImage image2, String pathToNewFile)
      throws IOException {
    BufferedImage newImage = new BufferedImage(image1.getWidth(), image1.getHeight(),
        image1.getType());
    //inspiration från https://stackoverflow.com/questions/37598977/how-to-compare-two-images-using-selenium
    //TODO: skriva om så att den använder en effektivare java-kod
    //TODO: hantera om bilderna är olika stora
    //se denna https://stackoverflow.com/questions/6524196/java-get-pixel-array-from-image
    for (int x = 0; x < image1.getWidth(); x++) {
      for (int y = 0; y < image1.getHeight(); y++) {
        int argb1 = image1.getRGB(x, y);
        int argb2 = image2.getRGB(x, y);
        int r0 = (argb1 >> 16) & 0xFF;
        int g0 = (argb1 >> 8) & 0xFF;
        int b0 = (argb1) & 0xFF;
        int r1 = (argb2 >> 16) & 0xFF;
        int g1 = (argb2 >> 8) & 0xFF;
        int b1 = (argb2) & 0xFF;
        int redDiff = Math.abs(r1 - r0);
        int greenDiff = Math.abs(g1 - g0);
        int blueDiff = Math.abs(b1 - b0);

        int imageDiff =
            (255 << 24) | ((~redDiff & 0xff) << 16) | ((~greenDiff & 0xff) << 8) | (~blueDiff
                & 0xff);
        newImage.setRGB(x, y, imageDiff);
      }
    }

    System.out.println("Sparar diffad bild till: " + pathToNewFile);
    ImageIO.write(newImage, "png", new File(pathToNewFile));
  }


  static long imageDifference(BufferedImage image1, BufferedImage image2) {
    //inspiration från https://stackoverflow.com/questions/37598977/how-to-compare-two-images-using-selenium
    //se denna https://stackoverflow.com/questions/6524196/java-get-pixel-array-from-image
    //TODO: testa att det fungerar med bild utan alpha-channel
    //TODO: hantera om ena bilden har alpha-channel, men inte den andra?
    //TODO: hantera om bilderna är olika stora
    long bildSkillnad = Long.MAX_VALUE;
    long totDiff = 0L;

    final byte[] pixels1 = ((DataBufferByte) image1.getRaster().getDataBuffer()).getData();
    final int width1 = image1.getWidth();
    final int height1 = image1.getHeight();
    final boolean hasAlphaChannel1 = image1.getAlphaRaster() != null;

    final byte[] pixels2 = ((DataBufferByte) image2.getRaster().getDataBuffer()).getData();
    final int width2 = image2.getWidth();
    final int height2 = image2.getHeight();
    final boolean hasAlphaChannel2 = image2.getAlphaRaster() != null;

    if (width1 != width2 || height1 != height2) {
      return bildSkillnad;
    }

    int pixelLength = hasAlphaChannel1 ? 4 : 3;
    int startPixel = hasAlphaChannel1 ? 1 : 0;

    for (int pixel = startPixel; pixel < pixels1.length; pixel += pixelLength) {
      totDiff += Math.abs((pixels1[pixel] & 0xff) - (pixels2[pixel] & 0xff));
      totDiff += Math.abs((pixels1[pixel + 1] & 0xff) - (pixels2[pixel + 1] & 0xff));
      totDiff += Math.abs((pixels1[pixel + 2] & 0xff) - (pixels2[pixel + 2] & 0xff));
    }

    return totDiff;
  }


}
