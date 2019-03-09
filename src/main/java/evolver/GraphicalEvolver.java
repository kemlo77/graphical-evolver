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
      System.out.println("file-name traits mutations rounds file-suffix");
      System.exit(1);
    }
    String fileName = args[0];
    int numberOfTraits = Integer.parseInt(args[1]);
    int numberOfMutations = Integer.parseInt(args[2]);
    int numberOfRounds = Integer.parseInt(args[3]);
    String fileSuffix = args[4];

    //    String fileName = "c:\\temp\\evolver\\160px-Mona_Lisa.png";
    //    int numberOfMutations = 5_000;
    //    int numberOfRounds = 1;
    //    String fileSuffix = "d";
    //    int numberOfTraits = 20;

    try {
      targetImage = new TargetImage(new File(fileName));

      for (int k = 0; k < numberOfRounds; k++) {

        Candidate candidate = new Candidate(numberOfTraits);
        for (int i = 0; i < numberOfMutations; i++) {

          //printing information every nth step
          int numberOfSteps = 50;
          if (i > 0 && 0 == i % Math.floor((float) numberOfMutations / numberOfSteps)) {
            System.out.println(candidate.getMutationInfo());
          }

          //replacing dead traits half way
          if (i == Math.floor((float) numberOfMutations / 2)) {
            int removedTraits = candidate.removeTraitsThatContributeUnderLimit(0.04f);
            candidate.addRandomTraits(removedTraits);
          }

          //evolving in a progressively decreasing manner
          candidate.evolve(1 - ((float) i / numberOfMutations));
        }

        System.out.println(candidate.getMutationInfo());
        candidate.saveToFile(
            "result_"
                + candidate.getMutationInfo().getFitnessPercentageString() + "_" + (k + 1)
                + fileSuffix
                + ".png", true);

        //System.out.println(candidate.toSvg());
      }


    } catch (IOException e) {
      System.out.println("Could not load file specified by user.");
      e.printStackTrace();
    }


  }


}