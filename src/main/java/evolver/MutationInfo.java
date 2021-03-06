package evolver;

import java.time.Duration;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

class MutationInfo {

  private long calculatedDifference;
  private long successfulMutations;
  private long totNumberOfMutations;

  private LocalTime startTime;
  private LocalTime middleTime;
  private Duration firstDurationTot = Duration.ofSeconds(0);
  private Duration secondDurationTot = Duration.ofSeconds(0);
  private Duration totMeasuredDuration = Duration.ofSeconds(0);

  MutationInfo() {
    this.successfulMutations = 0;
    this.totNumberOfMutations = 0;
    this.calculatedDifference = TargetImage.getMaximumDifference();
  }

  MutationInfo(MutationInfo mutationInfo) {
    this.successfulMutations = mutationInfo.successfulMutations;
    this.totNumberOfMutations = mutationInfo.totNumberOfMutations;
    this.calculatedDifference = mutationInfo.calculatedDifference;
  }

  void startTime() {
    startTime = LocalTime.now();
  }

  void middleTime() {
    middleTime = LocalTime.now();
  }

  void stopTime() {
    LocalTime stopTime = LocalTime.now();
    this.firstDurationTot = this.firstDurationTot.plus(Duration.between(startTime, middleTime));
    this.secondDurationTot = this.secondDurationTot.plus(Duration.between(middleTime, stopTime));
    this.totMeasuredDuration = this.firstDurationTot.plus(this.secondDurationTot);

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

  void upTotNumberOfMutations() {
    this.totNumberOfMutations++;
  }

  float getFitnessPercentage() {
    return (1 - (float) this.calculatedDifference / TargetImage.getMaximumDifference()) * 100;
  }

  String getFitnessPercentageString() {
    float percentage = getFitnessPercentage();
    return String.format(Locale.ROOT, "%.2f", percentage);

  }

  @Override
  public String toString() {
    LocalTime lt = LocalTime.now();
    DateTimeFormatter mydtf2 = DateTimeFormatter.ofPattern("HH:mm");
    String timeStamp = lt.format(mydtf2);

    return "(" + timeStamp + ") "
        + "Fitness: " + getFitnessPercentageString() + "% "
        + String.format("%10d", totNumberOfMutations)

        + " mutations in "
        + totMeasuredDuration.getSeconds() + "s "

        + "("
        + String.format("%.1f", (float) totNumberOfMutations / totMeasuredDuration.getSeconds())
        + "/s) "
        + String.format("%20s", "[" + successfulMutations + " successful] ")
        + "Spent "
        + firstDurationTot.getSeconds()
        + "s redrawing and "
        + secondDurationTot.getSeconds() + "s comparing.";
  }

}