package evolver;

import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

class Differ {

  public static void main(String[] args) throws IOException {
    BufferedImage in1 = ImageIO.read(new File("160px-Mona_Lisa.PNG"));
    BufferedImage in2 = ImageIO.read(new File("result_96.37_1a.png"));
    createDeltaImage2(in1, in2, "diff4.png");
  }

  static long totalImageColorDifference(BufferedImage image1, BufferedImage image2) {
    //inspiration från
    // https://stackoverflow.com/questions/37598977/how-to-compare-two-images-using-selenium
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

  private static int colorChannelDifference(int value1, int value2) {
    return Math.abs((value1 & 0xff) - (value2 & 0xff));
  }

  static void createDeltaImage(BufferedImage image1, BufferedImage image2, String pathToNewFile)
      throws IOException {
    BufferedImage newImage = new BufferedImage(image1.getWidth(), image1.getHeight(),
        image1.getType());
    //inspiration från
    // https://stackoverflow.com/questions/37598977/how-to-compare-two-images-using-selenium
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
    ImageIO.write(newImage, "png", new File(pathToNewFile));
  }


  static void createDeltaImage2(BufferedImage image1, BufferedImage image2, String pathToNewFile)
      throws IOException {
    int width = image1.getWidth();
    int height = image1.getHeight();
    BufferedImage newImage = new BufferedImage(width, height, image1.getType());
    int maxDiff = 1;
    for (int column = 0; column < width; column++) {
      for (int row = 0; row < height; row++) {
        int color1 = image1.getRGB(column, row);
        int color2 = image2.getRGB(column, row);
        int absoluteDiff = getAbsoluteColorDifference(color1, color2);
        if (absoluteDiff > maxDiff) {
          maxDiff = absoluteDiff;
        }
      }
    }
    //TODO: inte beräkna absoluteDiff två gånger
    for (int column = 0; column < width; column++) {
      for (int row = 0; row < height; row++) {
        int color1 = image1.getRGB(column, row);
        int color2 = image2.getRGB(column, row);
        int absoluteDiff = getAbsoluteColorDifference(color1, color2);
        int newAlpha = (int) ((double) absoluteDiff / maxDiff * 255);
        int newColorAndAlpha = rgbBytesToColorInt(getRed(color1), getGreen(color1), getBlue(color1),
            newAlpha);
        newImage.setRGB(column, row, newColorAndAlpha);
      }
    }
    ImageIO.write(newImage, "png", new File(pathToNewFile));
  }

  private static int rgbBytesToColorInt(int red, int green, int blue, int alpha) {
    return (alpha << 24) | (red << 16) | (green << 8) | blue;
  }

  private static int getAbsoluteColorDifference(int colorValue1, int colorValue2) {
    int redDiff = colorChannelDifference(getRed(colorValue1), getRed(colorValue2));
    int greenDiff = colorChannelDifference(getGreen(colorValue1), getGreen(colorValue2));
    int blueDiff = colorChannelDifference(getBlue(colorValue1), getBlue(colorValue2));
    return (int) Math
        .floor(Math.sqrt(
            (Math.pow(redDiff, 2) + Math.pow(greenDiff, 2) + Math.pow(blueDiff, 2)) / 3));
  }

  private static int getColorDifference(int colorInt1, int colorInt2) {
    int redDiff = colorChannelDifference(getRed(colorInt1), getRed(colorInt2));
    int greenDiff = colorChannelDifference(getGreen(colorInt1), getGreen(colorInt2));
    int blueDiff = colorChannelDifference(getBlue(colorInt1), getBlue(colorInt2));
    return rgbBytesToInvertedColorInt(redDiff, greenDiff, blueDiff, 255);
  }

  private static int rgbBytesToInvertedColorInt(int red, int green, int blue, int alpha) {
    //TODO: ska det verkligen inverteras??
    return (alpha << 24) | ~((red << 16) | (green << 8) | blue);
  }

  private static int getRed(int colorInt) {
    return ((colorInt >> 16) & 0xFF);
  }

  private static int getGreen(int colorInt) {
    return ((colorInt >> 8) & 0xFF);
  }

  private static int getBlue(int colorInt) {
    return (colorInt & 0xFF);
  }


}
