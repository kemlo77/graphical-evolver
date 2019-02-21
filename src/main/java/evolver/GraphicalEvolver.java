package evolver;

import java.io.File;
import java.io.IOException;
import java.time.Duration;
import java.time.LocalTime;

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
      targetImage = new TargetImage(new File(System.getProperty("user.dir") + "/mona_small.png"));
    } catch (IOException e) {
      System.out.println("Could not load file specified by user.");
      e.printStackTrace();

    }

    Candidate candidate = new Candidate(40);
    LocalTime startTime = LocalTime.now();

    int antalUpprepningar = 200;
    for (int i = 0; i < antalUpprepningar; i++) {
      System.out.print(i + " ");
      candidate.evolve();
    }
    candidate.saveToFile("result.png");

    LocalTime endTime = LocalTime.now();
    Duration duration = Duration.between(startTime, endTime);
    System.out
        .println(antalUpprepningar + " upprepningar tog " + duration.getSeconds() + " sekunder.");
    System.out
        .println("det blir " + (float) antalUpprepningar / duration.getSeconds() + " per sekund");


  }


}
