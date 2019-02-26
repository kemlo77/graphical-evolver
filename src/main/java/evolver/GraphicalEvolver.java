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
      targetImage = new TargetImage(
          //new File(System.getProperty("user.dir") + "/160px-Mona_Lisa.PNG")
          new File(System.getProperty("user.dir") + "/Mona_Lisa.png")
      //new File("c:\\temp\\evolver\\160px-Mona_Lisa.png")
      );

    } catch (IOException e) {
      System.out.println("Could not load file specified by user.");
      e.printStackTrace();

    }

    Candidate candidate = new Candidate(30);
    final LocalTime startTime = LocalTime.now();

    int antalUpprepningar = 2000;
    for (int i = 0; i < antalUpprepningar; i++) {
      System.out.print(i + " ");
      candidate.evolve(1 - ((float) i / antalUpprepningar) * 0.98f);
    }

    candidate.redrawTraits();
    candidate.saveToFile("result.png");

    LocalTime endTime = LocalTime.now();
    Duration duration = Duration.between(startTime, endTime);
    System.out
        .println(antalUpprepningar + " upprepningar tog " + duration.toString() + " sekunder.");
    System.out
        .println("det blir " + (float) antalUpprepningar / duration.getSeconds() + " per sekund");

    System.out.println(candidate.toSvg());


  }


}
