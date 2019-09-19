package evolver;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.BlockingDeque;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.atomic.AtomicBoolean;

public class Application {

  private static BlockingDeque<Candidate> candidateBlockingDeque = new LinkedBlockingDeque<>(10);

  /**
   * Main method that kick-starts the simulation using threads.
   *
   * @param args file-name traits mutations rounds file-suffix
   */
  public static void main(String[] args) throws InterruptedException {

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
      TargetImage.setTargetImage(new File(fileName));
    } catch (IOException e) {
      System.out.println("Could not load file specified by user.\nExiting..");
      System.exit(1);
    }

    Candidate candidate = new Candidate(numberOfTraits);
    candidateBlockingDeque.offerFirst(candidate);

    Evolver evolverRunnable = new Evolver(candidateBlockingDeque);
    Thread evolverThread = new Thread(evolverRunnable);
    ProgressPrinter progressPrinterRunnable = new ProgressPrinter(candidateBlockingDeque, 1000);
    Thread progressPrinterThread = new Thread(progressPrinterRunnable);

    evolverThread.start();
    progressPrinterThread.start();

    evolverThread.join();
    progressPrinterRunnable.stop();

    System.out.println(candidate.getMutationInfo());
    candidateBlockingDeque.peekFirst().saveToFile(
        "result_"
            + candidate.getMutationInfo().getFitnessPercentageString() + "_"
            + fileSuffix
            + ".png");

  }
}

class Evolver implements Runnable {

  private BlockingDeque<Candidate> candidateBlockingDeque;
  private Candidate candidate;

  public Evolver(BlockingDeque<Candidate> candidateBlockingDeque) {
    this.candidateBlockingDeque = candidateBlockingDeque;
  }

  @Override
  public void run() {
    System.out.println("hejpa");
    this.candidate = candidateBlockingDeque.peekFirst();
    int numberOfMutations = 5000;
    for (int i = 0; i < numberOfMutations; i++) {

      //evolving in a progressively decreasing manner
      candidate.evolve(1 - ((float) i / numberOfMutations));
    }
  }
}

class ProgressPrinter implements Runnable {

  private BlockingDeque<Candidate> candidateBlockingDeque;
  private final AtomicBoolean running = new AtomicBoolean(false);
  private int interval;

  public ProgressPrinter(BlockingDeque<Candidate> candidateBlockingDeque, int sleepInterval) {
    this.candidateBlockingDeque = candidateBlockingDeque;
    this.interval = sleepInterval;
  }

  public void stop() {
    running.set(false);
  }

  @Override
  public void run() {
    running.set(true);
    while (running.get()) {
      try {
        System.out.println(candidateBlockingDeque.peekFirst().getMutationInfo());
        Thread.sleep(interval);
      } catch (InterruptedException e) {
        Thread.currentThread().interrupt();
        System.out.println("progress printer thread was interrupted");
      }
    }

  }
}