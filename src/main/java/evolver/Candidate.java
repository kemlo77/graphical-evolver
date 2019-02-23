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

  //TODO: fler konstruktorer med andra alternativ?

  private static TargetImage targetImage = GraphicalEvolver.getTargetImage();

  private List<Trait> traitsList = new ArrayList<>();

  private long calculatedDifference;
  private long successfulMutations;
  private long maximumDifference;
  private int width;
  private int height;
  private Graphics2D candidateGraphics2D;
  private BufferedImage candidateBI;


  Candidate(int numberOfTraits) {
    width = targetImage.getImageWidth();
    height = targetImage.getImageHeight();
    maximumDifference = width * height * 3 * 255;

    traitsList.add(new Background(width, height));

    for (int i = 0; i < numberOfTraits; i++) {
      traitsList.add(new Polygon(6, width, height));
    }

    for (int j = 0; j < 10; j++) {
      traitsList.add(new Circle(width, height));
    }

    for (int k = 0; k < 20; k++) {
      traitsList.add(new Line(width, height));
    }

    candidateBI = new BufferedImage(width, height, targetImage.getImageType());
    candidateGraphics2D = candidateBI.createGraphics();

    redrawTraits();

    calculatedDifference = Differ.imageDifference(targetImage.getBufferedImage(), candidateBI);
    saveToFile("candidateInitialBI.png");
    System.out.println("Skillnaden är: " + calculatedDifference);


  }

  void redrawTraits() {
    candidateGraphics2D.setBackground(Color.WHITE);
    candidateGraphics2D.clearRect(0, 0, width, height);
    for (Trait trait : traitsList) {
      trait.draw(candidateGraphics2D);
    }
  }


  void evolve() {
    Random rand = new Random();
    int randomTraitNr = rand.nextInt(traitsList.size());

    System.out.print(" #" + randomTraitNr + " ");
    Trait randomTrait = traitsList.get(randomTraitNr);

    //Mutating
    randomTrait.mutateShape();
    randomTrait.mutateColor();

    //Drawing
    redrawTraits();

    //Comparing
    long differenceAfterMutation = Differ
        .imageDifference(targetImage.getBufferedImage(), candidateBI);

    System.out
        .print("efter: " + differenceAfterMutation + " before: " + calculatedDifference + " ");
    if (differenceAfterMutation > calculatedDifference) {
      System.out.print("(throw)");
      randomTrait.removeLastShapeMutation();
      randomTrait.removeLastColorMutation();
    } else {
      System.out.print("(keep)");
      calculatedDifference = differenceAfterMutation;
      successfulMutations++;
    }

    System.out.print(" Successful mutations:" + successfulMutations + " ");
    System.out
        .printf("Fitness: %.2f", (1 - (float) calculatedDifference / maximumDifference) * 100);
    System.out.println();


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


}
