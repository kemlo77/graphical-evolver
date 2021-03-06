package evolver.traits;

import evolver.TargetImage;
import evolver.Utils;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.util.Locale;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public abstract class Trait {

  private Color color;
  private Color oldColor;
  private Point pointThatWasMutated;
  private int deltaX = 0;
  private int deltaY = 0;
  private boolean dead = false;

  Trait() {
    int r = ThreadLocalRandom.current().nextInt(0, 255);
    int g = ThreadLocalRandom.current().nextInt(0, 255);
    int b = ThreadLocalRandom.current().nextInt(0, 255);

    this.color = new Color(r, g, b, 10);
  }

  public abstract void mutateShape(float degree);

  public abstract void removeLastShapeMutation();

  public abstract void draw(Graphics2D graphics);

  public abstract String toSvg();

  public void setDead() {
    this.dead = true;
  }

  public boolean isDead() {
    return this.dead;
  }

  Color getColor() {
    return this.color;
  }

  void setColor(Color color) {
    this.color = color;
  }

  /**
   * A method that will mutate the color.
   *
   * @param degree The degree of mutation.
   */
  public void mutateColor(float degree) {
    oldColor = color;

    Random rand = new Random();
    final int slumpNr = rand.nextInt(5);

    int newR = color.getRed();
    int newG = color.getGreen();
    int newB = color.getBlue();
    int newAlpha = color.getAlpha();

    switch (slumpNr) {
      case 0:
        newR = Utils.mutateInInterval(0, 255, color.getRed(), degree);
        break;
      case 1:
        newG = Utils.mutateInInterval(0, 255, color.getGreen(), degree);
        break;
      case 2:
        newB = Utils.mutateInInterval(0, 255, color.getBlue(), degree);
        break;
      case 3:
        newAlpha = Utils.mutateInInterval(0, 255, color.getAlpha(), degree);
        break;
      case 4:
        newR = Utils.mutateInInterval(0, 255, color.getRed(), degree);
        newG = Utils.mutateInInterval(0, 255, color.getGreen(), degree);
        newB = Utils.mutateInInterval(0, 255, color.getBlue(), degree);
        newAlpha = Utils.mutateInInterval(0, 255, color.getAlpha(), degree);
        break;
      default:
        break;
    }

    color = new Color(newR, newG, newB, newAlpha);
  }

  /**
   * Removes the mutation of the color.
   */
  public void removeLastColorMutation() {
    if (oldColor != null) {
      color = oldColor;
      oldColor = null;
    }
  }

  Point generateRandomPoint() {
    int x = ThreadLocalRandom.current().nextInt(0, TargetImage.getImageWidth() + 1);
    int y = ThreadLocalRandom.current().nextInt(0, TargetImage.getImageHeight() + 1);
    return new Point(x, y);
  }

  void mutatePoint(Point pointToBeMutated, float degree) {
    int currentX = (int) pointToBeMutated.getX();
    int currentY = (int) pointToBeMutated.getY();

    //TODO: skriv om detta så att det är tillåtet att deltaX xor deltaY slumpas till 0
    //ok för koordinater, men inte för färger
    //alltså kanske skriva om mutateInInterval
    deltaX = Utils.mutateInInterval(0, TargetImage.getImageWidth(), currentX, degree) - currentX;
    deltaY = Utils.mutateInInterval(0, TargetImage.getImageHeight(), currentY, degree) - currentY;
    pointToBeMutated.translate(deltaX, deltaY);
    pointThatWasMutated = pointToBeMutated;
  }

  void removeLastPointMutation() {
    if (pointThatWasMutated != null) {
      pointThatWasMutated.translate(-deltaX, -deltaY);
      deltaX = 0;
      deltaY = 0;
    }
  }


  String svgColorInfo() {
    return
        "\"rgb("
            + color.getRed() + ","
            + color.getGreen() + ","
            + color.getBlue() + ")\" "
            + "opacity=\""
            + String.format(Locale.ROOT, "%.3f", color.getAlpha() / 255f) + "\" ";
  }

}