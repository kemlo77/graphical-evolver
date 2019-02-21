package evolver;

import java.awt.Color;
import java.awt.Graphics2D;

abstract class Trait {

  Color color;
  private Color oldColor;

  abstract void mutateShape();

  abstract void removeLastShapeMutation();

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


  abstract void draw(Graphics2D graphics);

}
