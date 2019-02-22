package evolver;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;

abstract class Trait {

  //TODO: borde inte color vara private?
  Color color;
  private Color oldColor;
  private Point pointThatWasMutated;
  private int deltaX = 0;
  private int deltaY = 0;
  private int width;
  private int height;

  abstract void mutateShape();

  abstract void removeLastShapeMutation();

  void setWidth(int width) {
    this.width = width;
  }

  void setHeight(int height) {
    this.height = height;
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

}
