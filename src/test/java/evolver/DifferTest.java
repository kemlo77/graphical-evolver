package evolver;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.awt.image.BufferedImage;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class DifferTest {


  @ParameterizedTest(name = "{index} => message=''{7}''")
  @CsvSource({
      "0,0,0,255,0,0,25500,Black vs Red",
      "255,0,0,0,0,0,25500,Red vs Black",
      "255,255,255,0,0,0,76500,White vs Black"

  })
  @DisplayName("Compare different colors")
  void imageDifferenceTest(int r1, int g1, int b1, int r2, int g2, int b2, long expected,
      String compared) {
    BufferedImage bi1 = TestUtils.createBufferedImage(10, 10, r1, g1, b1);
    BufferedImage bi2 = TestUtils.createBufferedImage(10, 10, r2, g2, b2);
    assertEquals(expected, Differ.imageDifference(bi1, bi2));
  }


}
