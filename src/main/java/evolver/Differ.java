package evolver;

import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

class Differ {

  public static void main(String[] args) throws IOException {

    BufferedImage in1 = ImageIO.read(new File("mona_small.png"));
    BufferedImage in2 = ImageIO.read(new File("result_95.96_1f.png"));
    createDeltaImage(in1, in2, "diff.png");
  }




  static long totalImageColorDifference(BufferedImage image1, BufferedImage image2) {
    //inspiration från https://stackoverflow.com/questions/37598977/how-to-compare-two-images-using-selenium
    //se denna https://stackoverflow.com/questions/6524196/java-get-pixel-array-from-image
    //TODO: testa att det fungerar med bild utan alpha-channel
    //TODO: hantera om ena bilden har alpha-channel, men inte den andra?
    //TODO: hantera om bilderna är olika stora
    long maximumImageDifference = Long.MAX_VALUE;
    if (image1.getWidth() != image2.getWidth() || image1.getHeight() != image2.getHeight()) {
      return maximumImageDifference;
    }

    boolean hasAlphaChannel = imageHasAlphaChannel(image1);
    final byte[] pixelsRgb1 = getPixelsRgb(image1);
    final byte[] pixelsRgb2 = getPixelsRgb(image2);

    long totDiff = 0L;

    for (int pixelChannel = 0; pixelChannel < pixelsRgb1.length; pixelChannel++) {
      if (hasAlphaChannel) {
        if (pixelChannel % 4 == 0) {
          continue;
        }
      }
      totDiff += colorChannelDifference(pixelsRgb1[pixelChannel], pixelsRgb2[pixelChannel]);
    }

    return totDiff;
  }

  private static boolean imageHasAlphaChannel(BufferedImage image1) {
    return image1.getAlphaRaster() != null;
  }

  private static byte[] getPixelsRgb(BufferedImage image1) {
    return ((DataBufferByte) image1.getRaster().getDataBuffer()).getData();
  }

  private static int colorChannelDifference(byte value1, byte value2) {
    return Math.abs((value1 & 0xff) - (value2 & 0xff));
  }

  static void createDeltaImage(BufferedImage image1, BufferedImage image2, String pathToNewFile)
      throws IOException {
    BufferedImage newImage = new BufferedImage(image1.getWidth(), image1.getHeight(),
        image1.getType());
    //inspiration från https://stackoverflow.com/questions/37598977/how-to-compare-two-images-using-selenium
    //TODO: skriva om så att den använder en effektivare java-kod
    //TODO: hantera om bilderna är olika stora
    //TODO: pathToNewFile borde kanske vara en Path eller File
    //se denna https://stackoverflow.com/questions/6524196/java-get-pixel-array-from-image
    for (int column = 0; column < image1.getWidth(); column++) {
      for (int row = 0; row < image1.getHeight(); row++) {
        int colorValue1 = image1.getRGB(column, row);
        int colorValue2 = image2.getRGB(column, row);
        int imageDiff = getColorDifference(colorValue1, colorValue2);
        newImage.setRGB(column, row, imageDiff);
      }
    }

    System.out.println("Sparar diffad bild till: " + pathToNewFile);
    ImageIO.write(newImage, "png", new File(pathToNewFile));
  }

  private static int getColorDifference(int colorInt1, int colorInt2) {
    int redDiff = colorChannelDifference(getRedByte(colorInt1), getRedByte(colorInt2));
    int greenDiff = colorChannelDifference(getGreenByte(colorInt1), getGreenByte(colorInt2));
    int blueDiff = colorChannelDifference(getBlueByte(colorInt1), getBlueByte(colorInt2));
    return getColorInt(redDiff, greenDiff, blueDiff);
  }

  private static int getColorInt(int redDiff, int greenDiff, int blueDiff) {
    return (255 << 24) | ~((redDiff << 16) | (greenDiff << 8) | blueDiff);
  }

  private static byte getRedByte(int colorInt) {
    return (byte) ((colorInt >> 16) & 0xFF);
  }

  private static byte getGreenByte(int colorInt) {
    return (byte) ((colorInt >> 8) & 0xFF);
  }

  private static byte getBlueByte(int colorInt) {
    return (byte) (colorInt & 0xFF);
  }









}
