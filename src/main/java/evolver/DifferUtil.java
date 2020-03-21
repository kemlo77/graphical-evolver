package evolver;

import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

class DifferUtil {

  public static void main(String[] args) throws IOException {
    BufferedImage in1 = ImageIO.read(new File("160px-Mona_Lisa.png"));
    BufferedImage in2 = ImageIO.read(new File("result_96.37_1a.png"));
    saveDeltaImage2(in1, in2, "diff5.png");
  }

  static BufferedImage cloneToSpecificImageType(BufferedImage image, int imageType) {
    int width = image.getWidth();
    int height = image.getHeight();
    BufferedImage newImage = new BufferedImage(width, height, imageType);

    for (int column = 0; column < width; column++) {
      for (int row = 0; row < height; row++) {
        int color = image.getRGB(column, row);
        newImage.setRGB(column, row, color | (0xff << 24));


      }
    }
    return newImage;
  }

  static long totalImageColorDifference(BufferedImage image1, BufferedImage image2) {
    checkImageSizesMatch(image1, image2);
    final byte[] pixelsRgb1 = getPixelsRgb(image1);
    final byte[] pixelsRgb2 = getPixelsRgb(image2);
    long totDiff = 0L;
    for (int pixel = 0; pixel < pixelsRgb1.length; pixel += 4) {
      totDiff += colorChannelDifference(pixelsRgb1[pixel + 1], pixelsRgb2[pixel + 1]); //blue
      totDiff += colorChannelDifference(pixelsRgb1[pixel + 2], pixelsRgb2[pixel + 2]); //green
      totDiff += colorChannelDifference(pixelsRgb1[pixel + 3], pixelsRgb2[pixel + 3]); //red
    }
    return totDiff;
  }

  private static void checkImageSizesMatch(BufferedImage image1, BufferedImage image2) {
    if ((image1.getWidth() != image2.getWidth()) || image1.getHeight() != image2.getHeight()) {
      throw new IllegalArgumentException("Images have different size.");
    }
  }

  private static byte[] getPixelsRgb(BufferedImage image) {
    return ((DataBufferByte) image.getRaster().getDataBuffer()).getData();
  }

  private static int colorChannelDifference(int value1, int value2) {
    return Math.abs((value1 & 0xff) - (value2 & 0xff));
  }

  static void saveDeltaImage(BufferedImage image1, BufferedImage image2, String pathToNewFile)
      throws IOException {
    checkImageSizesMatch(image1, image2);
    BufferedImage delta = createDeltaImage(image1, image2);
    ImageIO.write(delta, "png", new File(pathToNewFile));
  }

  private static BufferedImage createDeltaImage(BufferedImage image1, BufferedImage image2) {
    checkImageSizesMatch(image1, image2);
    BufferedImage newImage = getCleanImage(image1);
    for (int column = 0; column < image1.getWidth(); column++) {
      for (int row = 0; row < image1.getHeight(); row++) {
        int color1 = image1.getRGB(column, row);
        int color2 = image2.getRGB(column, row);
        int imageDiff = getColorDifference(color1, color2);
        newImage.setRGB(column, row, imageDiff);
      }
    }
    return newImage;
  }

  private static BufferedImage getCleanImage(BufferedImage image1) {
    return new BufferedImage(image1.getWidth(), image1.getHeight(), image1.getType());
    //return new BufferedImage(image1.getWidth(), image1.getHeight(), 5);
  }

  static void saveDeltaImage2(BufferedImage image1, BufferedImage image2, String pathToNewFile)
      throws IOException {
    checkImageSizesMatch(image1, image2);
    BufferedImage newImage = createDeltaImage2(image1, image2);
    ImageIO.write(newImage, "png", new File(pathToNewFile));
  }

  private static BufferedImage createDeltaImage2(BufferedImage image1, BufferedImage image2) {
    BufferedImage newImage = getCleanImage(image1);
    int maxDiff = 1;
    for (int column = 0; column < image1.getWidth(); column++) {
      for (int row = 0; row < image1.getHeight(); row++) {
        int color1 = image1.getRGB(column, row);
        int color2 = image2.getRGB(column, row);
        int absoluteDiff = getAbsoluteColorDifference(color1, color2);
        if (absoluteDiff > maxDiff) {
          maxDiff = absoluteDiff;
        }
      }
    }
    //TODO: inte beräkna absoluteDiff två gånger
    for (int column = 0; column < image1.getWidth(); column++) {
      for (int row = 0; row < image1.getHeight(); row++) {
        int color1 = image1.getRGB(column, row);
        int color2 = image2.getRGB(column, row);
        int absoluteDiff = getAbsoluteColorDifference(color1, color2);
        int newAlpha = (int) ((double) absoluteDiff / maxDiff * 255);
        int newColorAndAlpha = rgbBytesToColorInt(getRed(color1), getGreen(color1), getBlue(color1),
            newAlpha);
        newImage.setRGB(column, row, newColorAndAlpha);
      }
    }
    return newImage;
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
