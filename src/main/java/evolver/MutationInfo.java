package evolver;

import java.time.Duration;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

class MutationInfo {

  private long calculatedDifference;
  private long successfulMutations;
  private long totNumberOfMutations;
  private long maximumDifference;
  private LocalTime startTime;
  private LocalTime middleTime;
  private LocalTime stopTime;
  private Duration firstDurationTot = Duration.ofSeconds(0);
  private Duration secondDurationTot = Duration.ofSeconds(0);
  private Duration totMeasuredDuration = Duration.ofSeconds(0);

  MutationInfo(int width, int height) {
    this.maximumDifference = width * height * 3 * 255;
    this.successfulMutations = 0;
    this.totNumberOfMutations = 0;
    this.calculatedDifference = maximumDifference;
  }

  void startTime() {
    startTime = LocalTime.now();
  }

  void middleTime() {
    middleTime = LocalTime.now();
  }

  void stopTime() {
    stopTime = LocalTime.now();
    this.firstDurationTot = this.firstDurationTot.plus(Duration.between(startTime, middleTime));
    this.secondDurationTot = this.secondDurationTot.plus(Duration.between(middleTime, stopTime));
    this.totMeasuredDuration = this.firstDurationTot.plus(this.secondDurationTot);

  }

  long getMaximumDifference() {
    return this.maximumDifference;
  }

  void setCalculatedDifference(long difference) {
    this.calculatedDifference = difference;
  }

  long getCalculatedDifference() {
    return calculatedDifference;
  }

  void upSuccessfulMutations() {
    this.successfulMutations++;
  }

  public long getSuccessfulMutations() {
    return this.successfulMutations;
  }

  void upTotNumberOfMutations() {
    this.totNumberOfMutations++;
  }

  public long getTotNumberOfMutations() {
    return this.totNumberOfMutations;
  }

  float getFitnessPercentage() {
    return (1 - (float) this.calculatedDifference / this.maximumDifference) * 100;
  }

  String getFitnessPercentageString() {
    float percentage = getFitnessPercentage();
    return String.format(Locale.ROOT, "%.2f", percentage);

  }

  //TODO: lägga in mer att skriva ut här
  @Override
  public String toString() {
    LocalTime lt = LocalTime.now();
    DateTimeFormatter mydtf2 = DateTimeFormatter.ofPattern("HH:mm");
    String timeStamp = lt.format(mydtf2);

    return "(" + timeStamp + ") "
        + "Fitness: " + getFitnessPercentageString() + "% "
        + totNumberOfMutations + " (" + successfulMutations + ") mutations in "
        + totMeasuredDuration + " "
        + "(" + ((float) totNumberOfMutations / totMeasuredDuration.getSeconds()) + "/s) "
        + firstDurationTot.getSeconds() + "s och " + secondDurationTot.getSeconds() + "s";
  }

}