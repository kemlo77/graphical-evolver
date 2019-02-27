package evolver;

import java.util.Locale;

class MutationInfo {

  private long calculatedDifference;
  private long successfulMutations;
  private long totNumberOfMutations;
  private long maximumDifference;

  MutationInfo(int width, int height) {
    this.maximumDifference = width * height * 3 * 255;
    this.successfulMutations = 0;
    this.totNumberOfMutations = 0;
    this.calculatedDifference = maximumDifference;
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

}
