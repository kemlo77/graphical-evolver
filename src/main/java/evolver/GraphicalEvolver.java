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

    if (args.length < 4) {
      System.out.println("file-name mutations rounds file-suffix");
      System.exit(1);
    }
    String fileName = args[0];
    int numberOfMutations = Integer.parseInt(args[1]);
    int numberOfRounds = Integer.parseInt(args[2]);
    String fileSuffix = args[3];

    try {
      targetImage = new TargetImage(new File(fileName));

      for (int k = 0; k < numberOfRounds; k++) {
        Candidate candidate = new Candidate(30);

        for (int i = 0; i < numberOfMutations; i++) {
          candidate.evolve(1 - ((float) i / numberOfMutations));
        }

        System.out.println(candidate.getMutationInfo());
        candidate.saveToFile(
            "result_"
                + candidate.getMutationInfo().getFitnessPercentageString() + "_" + (k + 1)
                + fileSuffix
                + ".png", true);
      }

      //System.out.println(candidate.toSvg());

    } catch (IOException e) {
      System.out.println("Could not load file specified by user.");
      e.printStackTrace();
    }


  }


}