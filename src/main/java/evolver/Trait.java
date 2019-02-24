package evolver;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.util.concurrent.ThreadLocalRandom;

abstract class Trait {

  //TODO: borde inte color vara private?
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

  abstract void mutateShape();

  abstract void removeLastShapeMutation();

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

  void mutateColor() {
    //backing up old color
    oldColor = color;
    //creating new color
    int newR = Utils.mutateInWholeInterval(0, 255);
    int newG = Utils.mutateInWholeInterval(0, 255);
    int newB = Utils.mutateInWholeInterval(0, 255);
    int newAlpha = Utils.mutateInWholeInterval(0, 255);
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

  void mutatePoint(Point pointToBeMutated) {
    int currentX = (int) pointToBeMutated.getX();
    int currentY = (int) pointToBeMutated.getY();
    deltaX = Utils.mutateInInterval(0, width, currentX, 1f) - currentX;
    deltaY = Utils.mutateInInterval(0, height, currentY, 1f) - currentY;
    pointToBeMutated.translate(deltaX, deltaY);
    pointThatWasMutated = pointToBeMutated;
  }

  void removeLastPointMutation() {
    pointThatWasMutated.translate(-deltaX, -deltaY);
    deltaX = 0;
    deltaY = 0;
  }


  abstract void draw(Graphics2D graphics);

  @Override
  public String toString() {
    //TODO: färre antal decimaler på alpha
    StringBuilder sb = new StringBuilder();
    sb.append("rgb(")
        .append(color.getRed()).append(",")
        .append(color.getGreen()).append(",")
        .append(color.getBlue()).append(") ")
        .append("opacity=\"")
        .append(color.getAlpha() / 255f).append("\" ");
    return sb.toString();
  }

}
