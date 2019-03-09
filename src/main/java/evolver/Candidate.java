package evolver;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import javax.imageio.ImageIO;


class Candidate {


  private static TargetImage targetImage = GraphicalEvolver.getTargetImage();

  private List<Trait> traitsList = new ArrayList<>();
  private MutationInfo mutationInfo;

  private int width;
  private int height;
  private Graphics2D candidateGraphics2D;
  private BufferedImage candidateBI;


  Candidate(int numberOfTraits) {
    width = targetImage.getImageWidth();
    height = targetImage.getImageHeight();
    mutationInfo = new MutationInfo(width, height);

    traitsList.add(new Background(width, height));

    addRandomTraits(numberOfTraits);

    candidateBI = new BufferedImage(width, height, targetImage.getImageType());
    candidateGraphics2D = candidateBI.createGraphics();

    redrawAllTraits();
    mutationInfo.setCalculatedDifference(calculateDifference());
  }


  private void redrawTraitsExceptThisOne(int skipThis) {
    candidateGraphics2D.setBackground(Color.WHITE);
    candidateGraphics2D.clearRect(0, 0, width, height);
    for (int i = 0; i < traitsList.size(); i++) {
      if (i == skipThis) {
        continue;
      }
      traitsList.get(i).draw(candidateGraphics2D);
    }
  }


  private void redrawAllTraits() {
    redrawTraitsExceptThisOne(-1);
  }


  private long calculateDifference() {
    return Differ.imageDifference(targetImage.getBufferedImage(), candidateBI);
  }


  MutationInfo getMutationInfo() {
    return this.mutationInfo;
  }


  int removeTraitsThatContributeUnderLimit(float contributionLimit) {
    float currentFitness = mutationInfo.getFitnessPercentage();
    int antalTraits = traitsList.size();
    for (int i = 0; i < antalTraits; i++) {

      redrawTraitsExceptThisOne(i);
      long calculatedDifference = calculateDifference();
      float newCalculatedFitness =
          (1 - (float) calculatedDifference / mutationInfo.getMaximumDifference()) * 100;

      System.out.println(""
          + currentFitness + " - "
          + newCalculatedFitness + " = "
          + (currentFitness - newCalculatedFitness));

      if ((currentFitness - newCalculatedFitness) < contributionLimit) {
        traitsList.get(i).setDead();
      }

    }
    traitsList.removeIf(Trait::isDead);
    int numberOfTraitsRemoved = (antalTraits - traitsList.size());
    System.out.println("Removed " + numberOfTraitsRemoved + " traits.");
    redrawAllTraits();
    mutationInfo.setCalculatedDifference(calculateDifference());

    return numberOfTraitsRemoved;
  }


  void addRandomTraits(int numberOfNewTraits) {
    Random rand = new Random();
    for (int i = 0; i < numberOfNewTraits; i++) {
      int randomInt = rand.nextInt(3);
      if (randomInt == 0) {
        traitsList.add(new Polygon(6, width, height));
      }
      if (randomInt == 1) {
        traitsList.add(new Circle(width, height));
      }
      if (randomInt == 2) {
        traitsList.add(new Line(width, height));
      }
    }
    System.out.println("Added " + numberOfNewTraits + " new traits.");
  }


  void evolve(float degree) {
    mutationInfo.startTime();
    Random rand = new Random();
    int randomTraitNr = rand.nextInt(traitsList.size());

    Trait randomTrait = traitsList.get(randomTraitNr);

    //Mutating
    //TODO: Ändra hur/vad som muteras beroende på degree. T.ex bara color eller bara shape
    randomTrait.mutateColor(degree);
    randomTrait.mutateShape(degree);

    //Drawing
    redrawAllTraits();

    mutationInfo.middleTime();
    //Comparing
    long differenceAfterMutation = calculateDifference();

    if (differenceAfterMutation > mutationInfo.getCalculatedDifference()) {
      randomTrait.removeLastShapeMutation();
      randomTrait.removeLastColorMutation();
    } else {
      mutationInfo.setCalculatedDifference(differenceAfterMutation);
      mutationInfo.upSuccessfulMutations();
    }
    mutationInfo.upTotNumberOfMutations();
    mutationInfo.stopTime();
  }


  void saveToFile(String fileName, boolean inHighQuality) {
    BufferedImage fancyRender = new BufferedImage(width, height, targetImage.getImageType());
    Graphics2D g2d = fancyRender.createGraphics();

    if (inHighQuality) {
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
    }

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


  String toSvg() {
    StringBuilder sb = new StringBuilder();
    sb
        .append("<svg ")
        .append("width=\"").append(width).append("\" ")
        .append("height=\"").append(height).append("\">\n");
    for (Trait trait : traitsList) {
      sb.append("  ").append(trait.toSvg()).append("\n");
    }
    sb.append("</svg>");
    return sb.toString();
  }

}