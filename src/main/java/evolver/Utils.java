package evolver;

import java.util.Random;

public class Utils {

  /**
   * Returns a random int within the given interval (minIncl - maxIncl) A spread ratio close to 1.0
   * will let the new random int generate within the whole interval. A spread ratio close to 0.01
   * will have the new random int generate close to the current value. This method will not return a
   * value equal to the currentVal
   *
   * @param minIncl smallest tolerated value to be returned
   * @param maxIncl largest tolerated value to be returned
   * @param currentVal the current value.
   * @param spreadRatio The spread ratio (between 0.01 and 1.0).
   * @return a new random number
   */
  public static int mutateInInterval(int minIncl, int maxIncl, int currentVal, float spreadRatio) {
    if (spreadRatio < 0.01f || spreadRatio > 1) {
      throw new IllegalArgumentException(
          "maxStepPerc must be larger than 0.01 and not larger than 1.");
    }
    if (minIncl == maxIncl) {
      throw new IllegalArgumentException("minIncl cannot be equal to maxIncl.");
    }
    if (minIncl > maxIncl) {
      throw new IllegalArgumentException("minIncl cannot be greater than maxIncl.");
    }
    if (minIncl < 0 || maxIncl < 0 || currentVal < 0) {
      throw new IllegalArgumentException("Parameters must be a positive number or zero.");
    }
    if (currentVal < minIncl || currentVal > maxIncl) {
      throw new IllegalArgumentException("currentVal must be between minIncl and maxIncl");
    }

    int intervalWidth = maxIncl - minIncl;
    int newMaxIncl = (int) (currentVal + intervalWidth * spreadRatio);
    if (newMaxIncl > maxIncl) {
      newMaxIncl = maxIncl;
    }
    int newMinIncl = (int) (currentVal - intervalWidth * spreadRatio);
    if (newMinIncl < minIncl) {
      newMinIncl = minIncl;
    }
    int newIntervalWidth = newMaxIncl - newMinIncl;

    int returnVal = currentVal;
    Random rand = new Random();
    do {
      returnVal = rand.nextInt(newIntervalWidth + 1) + newMinIncl;
    } while (returnVal == currentVal);

    return returnVal;
  }

  public static void main(String[] args) {
    for (int i = 0; i < 40; i++) {
      int randomInt = mutateInInterval(0, 20, 15, 0.5f);
      System.out.print(randomInt + "  ");
    }
  }


  /**
   * Returns a random int in the given interval (including max/min value).
   *
   * @param minInclusive minimum value
   * @param maxInclusive maximum value
   * @return a random int int the given interval
   */
  static int mutateInWholeInterval(int minInclusive, int maxInclusive) {
    if (minInclusive < 0 || maxInclusive < 0) {
      throw new IllegalArgumentException("Parameters must be a positive number or zero.");
    }
    if (minInclusive == maxInclusive) {
      throw new IllegalArgumentException("minInclusive cannot be equal to maxInclusive.");
    }
    if (minInclusive > maxInclusive) {
      throw new IllegalArgumentException("minInclusive cannot be greater than maxInclusive.");
    }

    Random rand = new Random();
    return (rand.nextInt(maxInclusive - minInclusive + 1) + minInclusive);
  }


}
