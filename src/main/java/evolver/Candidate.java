package evolver;

import java.awt.Color;
import java.awt.Graphics2D;
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

    Random rand = new Random();
    for (int i = 0; i < numberOfTraits; i++) {
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

    candidateBI = new BufferedImage(width, height, targetImage.getImageType());
    candidateGraphics2D = candidateBI.createGraphics();

    redrawTraits();
    mutationInfo.setCalculatedDifference(
        Differ.imageDifference(targetImage.getBufferedImage(), candidateBI));

    System.out.println("Skillnaden är: " + mutationInfo.getCalculatedDifference());


  }

  void redrawTraits() {
    candidateGraphics2D.setBackground(Color.WHITE);
    candidateGraphics2D.clearRect(0, 0, width, height);
    for (Trait trait : traitsList) {
      trait.draw(candidateGraphics2D);
    }
  }

  MutationInfo getMutationInfo() {
    return this.mutationInfo;
  }

  void evolve(float degree) {
    Random rand = new Random();
    int randomTraitNr = rand.nextInt(traitsList.size());

    Trait randomTrait = traitsList.get(randomTraitNr);

    //Mutating
    randomTrait.mutateShape(degree);
    randomTrait.mutateColor(degree);

    //Drawing
    redrawTraits();

    //Comparing
    long differenceAfterMutation = Differ
        .imageDifference(targetImage.getBufferedImage(), candidateBI);

    if (differenceAfterMutation > mutationInfo.getCalculatedDifference()) {
      randomTrait.removeLastShapeMutation();
      randomTrait.removeLastColorMutation();
    } else {
      mutationInfo.setCalculatedDifference(differenceAfterMutation);
      mutationInfo.upSuccessfulMutations();
    }
    mutationInfo.upTotNumberOfMutations();


  }

  void saveToFile(String fileName) {
    String pathToNewFile = System.getProperty("user.dir") + "/" + fileName;
    System.out.println("Saving candidate to: " + pathToNewFile);
    try {
      ImageIO.write(candidateBI, "png", new File(pathToNewFile));
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