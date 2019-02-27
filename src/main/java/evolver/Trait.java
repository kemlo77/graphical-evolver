package evolver;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.util.Locale;
import java.util.concurrent.ThreadLocalRandom;

abstract class Trait {

  private Color color;
  private Color oldColor;
  private Point pointThatWasMutated;
  private int deltaX = 0;
  private int deltaY = 0;
  private int width;
  private int height;

  Trait(int width, int height) {
    this.width = width;
    this.height = height;

    int r = ThreadLocalRandom.current().nextInt(0, 255);
    int g = ThreadLocalRandom.current().nextInt(0, 255);
    int b = ThreadLocalRandom.current().nextInt(0, 255);

    this.color = new Color(r, g, b, 10);
  }

  abstract void mutateShape(float degree);

  abstract void removeLastShapeMutation();

  abstract void draw(Graphics2D graphics);

  abstract String toSvg();

  void setWidth(int width) {
    this.width = width;
  }

  void setHeight(int height) {
    this.height = height;
  }

  int getWidth() {
    return width;
  }

  int getHeight() {
    return height;
  }

  Color getColor() {
    return this.color;
  }

  void mutateColor(float degree) {
    oldColor = color;

    int newR = Utils.mutateInInterval(0, 255, color.getRed(), degree);
    int newG = Utils.mutateInInterval(0, 255, color.getGreen(), degree);
    int newB = Utils.mutateInInterval(0, 255, color.getBlue(), degree);
    int newAlpha = Utils.mutateInInterval(0, 255, color.getAlpha(), degree);
    color = new Color(newR, newG, newB, newAlpha);
  }


  void removeLastColorMutation() {
    if (oldColor != null) {
      color = oldColor;
      oldColor = null;
    }
  }

  Point generateRandomPoint() {
    int x = ThreadLocalRandom.current().nextInt(0, width + 1);
    int y = ThreadLocalRandom.current().nextInt(0, height + 1);
    return new Point(x, y);
  }

  void mutatePoint(Point pointToBeMutated, float degree) {
    int currentX = (int) pointToBeMutated.getX();
    int currentY = (int) pointToBeMutated.getY();
    deltaX = Utils.mutateInInterval(0, width, currentX, degree) - currentX;
    deltaY = Utils.mutateInInterval(0, height, currentY, degree) - currentY;
    pointToBeMutated.translate(deltaX, deltaY);
    pointThatWasMutated = pointToBeMutated;
  }

  void removeLastPointMutation() {
    pointThatWasMutated.translate(-deltaX, -deltaY);
    deltaX = 0;
    deltaY = 0;
  }


  String svgColorInfo() {
    return
        "rgb("
            + color.getRed() + ","
            + color.getGreen() + ","
            + color.getBlue() + ") "
            + "opacity=\""
            + String.format(Locale.ROOT, "%.3f", color.getAlpha() / 255f) + "\" ";
  }

}
