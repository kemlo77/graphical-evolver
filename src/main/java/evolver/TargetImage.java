package evolver;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;
import javax.imageio.ImageIO;

class TargetImage {

  private static int originalImageWidth;
  private static int originalImageHeight;
  private static int originalImageType;
  private static BufferedImage originalBufferedImage;
  private static long maximumDifference;
  private static BufferedImage candidateBufferedImage;
  private static Graphics2D candidateGraphics2d;


  static int getImageWidth() {
    return originalImageWidth;
  }

  static int getImageHeight() {
    return originalImageHeight;
  }

  static int getImageType() {
    return originalImageType;
  }

  static BufferedImage getOriginalBufferedImage() {
    return originalBufferedImage;
  }

  static long getMaximumDifference() {
    return maximumDifference;
  }

  public static Graphics2D getGraphics2d() {
    return candidateGraphics2d;
  }

  static long calculateDifference() {
    return Differ.imageDifference(originalBufferedImage, candidateBufferedImage);
  }

  static void redrawCandidate(List<Trait> traitsList, int skipTraitNr) {
    candidateGraphics2d.setBackground(Color.WHITE);
    candidateGraphics2d.clearRect(0, 0, originalImageWidth, originalImageHeight);
    for (int i = 0; i < traitsList.size(); i++) {
      if (i == skipTraitNr) {
        continue;
      }
      traitsList.get(i).draw(candidateGraphics2d);
    }
  }


  static void redrawCandidate(List<Trait> traitList) {
    redrawCandidate(traitList, -1);
  }


  static void saveToFile(List<Trait> traitsList, String fileName) {

    BufferedImage fancyRender = new BufferedImage(originalImageWidth, originalImageHeight,
        originalImageType);
    Graphics2D g2d = fancyRender.createGraphics();
    g2d.setClip(0, 0, originalImageWidth, originalImageHeight);

    g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
        RenderingHints.VALUE_ANTIALIAS_ON);
    g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION,
        RenderingHints.VALUE_INTERPOLATION_BICUBIC);
    g2d.setRenderingHint(RenderingHints.KEY_ALPHA_INTERPOLATION,
        RenderingHints.VALUE_ALPHA_INTERPOLATION_QUALITY);
    g2d.setRenderingHint(RenderingHints.KEY_COLOR_RENDERING,
        RenderingHints.VALUE_COLOR_RENDER_QUALITY);
    g2d.setRenderingHint(RenderingHints.KEY_DITHERING,
        RenderingHints.VALUE_DITHER_ENABLE);
    g2d.setRenderingHint(RenderingHints.KEY_RENDERING,
        RenderingHints.VALUE_RENDER_QUALITY);

    g2d.setBackground(Color.WHITE);
    g2d.clearRect(0, 0, originalImageWidth, originalImageHeight);
    for (Trait trait : traitsList) {
      trait.draw(g2d);
    }

    String pathToNewFile = System.getProperty("user.dir") + File.separator + fileName;
    System.out.println("Saving candidate to: " + pathToNewFile);
    try {
      ImageIO.write(fancyRender, "png", new File(pathToNewFile));
    } catch (IOException e) {
      System.out.println("Could not save candidate image file to: " + pathToNewFile);
      e.printStackTrace();
    }
  }


  static void setTargetImage(File file) throws IOException {
    originalBufferedImage = ImageIO.read(file);
    originalImageWidth = originalBufferedImage.getWidth();
    originalImageHeight = originalBufferedImage.getHeight();

    originalImageType = originalBufferedImage.getType();
    maximumDifference = originalImageWidth * originalImageHeight * 3 * 255;

    candidateBufferedImage = new BufferedImage(originalImageWidth, originalImageHeight,
        originalImageType);
    candidateGraphics2d = candidateBufferedImage.createGraphics();
    candidateGraphics2d.setClip(0, 0, originalImageWidth, originalImageHeight);

  }
}
