package evolver;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.*;

class UtilsTest {

    @RepeatedTest(20)
    @DisplayName("Check that numbers are within interval")
    void mutateInWholeIntervalTest1() {
        int minValue = 3;
        int maxValue = 8;
        int returnedValue = Utils.mutateInWholeInterval(minValue, maxValue);
        assertTrue(minValue <= returnedValue);
        assertTrue(maxValue >= returnedValue);
    }

    @ParameterizedTest
    @CsvSource({
            "0",
            "10"
    })
    @DisplayName("Check that numbers returned are within given tight interval")
    void mutateInWholeIntervalTest2(int sameValue) {
        int returnedValue = Utils.mutateInWholeInterval(sameValue, sameValue);
        assertTrue(sameValue <= returnedValue);
        assertTrue(sameValue >= returnedValue);
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


}
