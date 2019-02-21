package evolver;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class UtilsTest {


  @ParameterizedTest
  @DisplayName("Check that IllegalArgumentException is thrown.")
  @CsvSource({
      "-1,10,3",
      "-3,-1,-2"
  })
  void throwsExceptionWhenBadArguments1(int minValue, int maxValue, int current) {
    Exception exception = assertThrows(IllegalArgumentException.class, () ->
        Utils.mutateInInterval(minValue, maxValue, current, 0.5f));
    assertEquals("Parameters must be a positive number or zero.", exception.getMessage());
  }

  @Test
  @DisplayName("min value equals max value")
  void throwsExceptionMaxEqualsMin1() {
    Exception exception = assertThrows(IllegalArgumentException.class, () ->
        Utils.mutateInInterval(3, 3, 3, 0.5f));
    assertEquals("minIncl cannot be equal to maxIncl.", exception.getMessage());
  }

  @Test
  @DisplayName("min value equals max value")
  void throwsExceptionMaxLessThanMin1() {
    Exception exception = assertThrows(IllegalArgumentException.class, () ->
        Utils.mutateInInterval(5, 3, 4, 0.5f));
    assertEquals("minIncl cannot be greater than maxIncl.", exception.getMessage());
  }

  @Test
  @DisplayName("min value equals max value")
  void throwsExceptionSpreadRatioTooSmall() {
    Exception exception = assertThrows(IllegalArgumentException.class, () ->
        Utils.mutateInInterval(5, 3, 4, 0f));
    assertEquals("maxStepPerc must be larger than 0.01 and not larger than 1.",
        exception.getMessage());
  }

  @Test
  @DisplayName("min value equals max value")
  void throwsExceptionSpreadRatioTooLarge() {
    Exception exception = assertThrows(IllegalArgumentException.class, () ->
        Utils.mutateInInterval(5, 3, 4, 1.001f));
    assertEquals("maxStepPerc must be larger than 0.01 and not larger than 1.",
        exception.getMessage());
  }


  @RepeatedTest(20)
  @DisplayName("Check that random numbers are within interval. Not equal current value.")
  void mutateInIntervalTest1() {
    int minValue = 0;
    int maxValue = 20;
    int currentValue = 10;
    float intervalWidthPerc = 0.2f;

    int wholeInterval = maxValue - minValue;
    int maxExpectedValue = (int) (currentValue + wholeInterval * intervalWidthPerc);
    if (maxExpectedValue > maxValue) {
      maxExpectedValue = maxValue;
    }
    int minExpectedValue = (int) (currentValue - wholeInterval * intervalWidthPerc);
    if (minExpectedValue < minValue) {
      minExpectedValue = minValue;
    }

    int returnedValue = Utils.mutateInInterval(minValue, maxValue, currentValue, intervalWidthPerc);
    assertNotEquals(returnedValue, currentValue, "Returned value equals current value.");
    assertTrue(returnedValue <= maxExpectedValue,
        "Returned value too large (" + returnedValue + ").");
    assertTrue(returnedValue >= minExpectedValue,
        "Returned value too small (" + returnedValue + ")");
  }


  @RepeatedTest(20)
  @DisplayName("Check that random numbers are within interval")
  void mutateInWholeIntervalTest1() {
    int minValue = 3;
    int maxValue = 8;
    int returnedValue = Utils.mutateInWholeInterval(minValue, maxValue);
    assertTrue(minValue <= returnedValue);
    assertTrue(maxValue >= returnedValue);
  }


  @ParameterizedTest
  @DisplayName("Check that IllegalArgumentException is thrown.")
  @CsvSource({
      "-1,10",
      "10,-1",
      "-1,-2"
  })
  void throwsExceptionWhenBadArguments(int minValue, int maxValue) {
    Exception exception = assertThrows(IllegalArgumentException.class, () ->
        Utils.mutateInWholeInterval(minValue, maxValue));
    assertEquals("Parameters must be a positive number or zero.", exception.getMessage());
  }

  @Test
  @DisplayName("min value equals max value")
  void throwsExceptionMaxEqualsMin() {
    Exception exception = assertThrows(IllegalArgumentException.class, () ->
        Utils.mutateInWholeInterval(3, 3));
    assertEquals("minInclusive cannot be equal to maxInclusive.", exception.getMessage());
  }

  @Test
  @DisplayName("min value equals max value")
  void throwsExceptionMaxLessThanMin() {
    Exception exception = assertThrows(IllegalArgumentException.class, () ->
        Utils.mutateInWholeInterval(4, 3));
    assertEquals("minInclusive cannot be greater than maxInclusive.", exception.getMessage());
  }


}
