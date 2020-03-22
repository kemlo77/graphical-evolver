package evolver;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.nio.file.Path;
import java.util.stream.Stream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.io.TempDir;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class DifferUtilTest {

  private static Stream<Arguments> coloredImages() {
    //BufferedImage red = TestUtils.createBufferedImage(255,0,0);
    BufferedImage red = TestUtils.createBufferedImage(Color.red);
    BufferedImage black = TestUtils.createBufferedImage(Color.black);
    BufferedImage white = TestUtils.createBufferedImage(Color.white);
    BufferedImage green = TestUtils.createBufferedImage(Color.green);
    BufferedImage yellow = TestUtils.createBufferedImage(Color.yellow);
    BufferedImage blue = TestUtils.createBufferedImage(Color.blue);
    BufferedImage cyan = TestUtils.createBufferedImage(Color.cyan);
    return Stream.of(
        Arguments.of(black, red, cyan, 255 * 100, "Black vs Red"),
        Arguments.of(red, black, cyan, 255 * 100, "Red vs black"),
        Arguments.of(white, black, black, 255 * 3 * 100, "White vs Black"),
        Arguments.of(red, green, blue, 255 * 2 * 100, "Red vs Green"),
        Arguments.of(red, blue, green, 255 * 2 * 100, "Red vs Blue"),
        Arguments.of(blue, red, green, 255 * 2 * 100, "Blue vs Red"),
        Arguments.of(blue, green, red, 255 * 2 * 100, "Blue vs Green"),
        Arguments.of(green, blue, red, 255 * 2 * 100, "Green vs Blue"),
        Arguments.of(green, red, blue, 255 * 2 * 100, "Green vs Red")
    );
  }

  @TempDir
  Path tempDir;


  @ParameterizedTest(name = "{index} - ''{4}''")
  @MethodSource("coloredImages")
  @DisplayName("Verify calculation using different colored images")
  void totalImageColorDifferenceTest(BufferedImage b1, BufferedImage b2, BufferedImage diff,
      long expectedDiff, String description) {
    assertEquals(expectedDiff, DifferUtil.totalImageColorDifference(b1, b2));
  }


  @ParameterizedTest(name = "{index} - ''{4}''")
  @MethodSource("coloredImages")
  @DisplayName("Verify delta image creation using different colored images")
  void createDeltaImageTest(BufferedImage bufferedImage1, BufferedImage bufferedImage2,
      BufferedImage expectedImage, int a, String message) {

    BufferedImage outputBufferedImage= DifferUtil.createDeltaImage(bufferedImage1, bufferedImage2);
    assertEquals(expectedImage.getRGB(1, 1), outputBufferedImage.getRGB(1, 1));

  }

}
