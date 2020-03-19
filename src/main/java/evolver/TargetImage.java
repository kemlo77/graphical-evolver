package evolver;

import evolver.traits.Trait;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;
import javax.imageio.ImageIO;

public class TargetImage {

  private static int width;
  private static int height;
  private static int type;
  private static BufferedImage originalBufferedImage;
  private static long maximumDifference;
  private static BufferedImage candidateBufferedImage;
  private static Graphics2D candidateGraphics2d;


  public static int getImageWidth() {
    return width;
  }

  public static int getImageHeight() {
    return height;
  }

  static int getImageType() {
    return type;
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
    return Differ.totalImageColorDifference(originalBufferedImage, candidateBufferedImage);
  }

  static void redrawCandidate(List<Trait> traitsList, int skipTraitNr) {
    candidateGraphics2d.setBackground(Color.WHITE);
    candidateGraphics2d.clearRect(0, 0, width, height);
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

    BufferedImage fancyRender = new BufferedImage(width, height,
        type);
    Graphics2D g2d = fancyRender.createGraphics();
    g2d.setClip(0, 0, width, height);

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
    g2d.clearRect(0, 0, width, height);
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

    width = originalBufferedImage.getWidth();
    height = originalBufferedImage.getHeight();
    type = originalBufferedImage.getType();

    maximumDifference = width * height * 3 * 255;
    //TODO: skapa en ny "original buffered image" som garanterat har alpha channel
    //TODO: candidateBufferedImage ska ha alpha channel, dvs välj typ
    candidateBufferedImage = new BufferedImage(width, height, type);

    candidateGraphics2d = candidateBufferedImage.createGraphics();
    candidateGraphics2d.setClip(0, 0, width, height);

  }
}
