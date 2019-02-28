package evolver;

import java.io.File;
import java.io.IOException;

public class GraphicalEvolver {


  private static TargetImage targetImage;

  static TargetImage getTargetImage() {
    return targetImage;
  }


  /**
   * Main method that kick starts the simulation.
   *
   * @param args Not used.
   */
  public static void main(String[] args) {

    try {
      targetImage = new TargetImage(
          //    new File(System.getProperty("user.dir") + "/321px-Mona_Lisa.PNG")
          //new File(System.getProperty("user.dir") + "/Mona_Lisa.png")
          new File("c:\\temp\\evolver\\160px-Mona_Lisa.png")
      );

    } catch (IOException e) {
      System.out.println("Could not load file specified by user.");
      e.printStackTrace();

    }

    for (int k = 0; k < 10; k++) {
      Candidate candidate = new Candidate(30);

      int antalUpprepningar = 20_000;
      for (int i = 0; i < antalUpprepningar; i++) {
        candidate.evolve(1 - ((float) i / antalUpprepningar));
      }

      //TODO: här borde jag göra en "snygg" redraw
      candidate.redrawTraits();
      candidate.saveToFile(
          "result_" + k + "_"
              + candidate.getMutationInfo().getFitnessPercentageString() + ".png");

      System.out.println(candidate.getMutationInfo());

    }

    //System.out.println(candidate.toSvg());

  }


}