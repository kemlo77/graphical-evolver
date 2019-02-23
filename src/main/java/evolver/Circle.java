package evolver;

import java.awt.Graphics2D;
import java.awt.Point;

class Circle extends Trait {

  private Point midPoint;
  private int diameter;
  private int oldDiameter;
  private final int maxDiameter = 20;

  Circle(int width, int height) {
    super(width, height);

    this.midPoint = generateRandomPoint();
    this.diameter = Utils.mutateInWholeInterval(1, maxDiameter);

  }

  @Override
  void mutateShape() {
    mutatePoint(midPoint);

    //TODO: raden borde kanske slumpas enligt mutateInInterval där man kan ange spreadRatio
    oldDiameter = diameter;
    diameter = Utils.mutateInWholeInterval(1, maxDiameter);
  }

  @Override
  void removeLastShapeMutation() {
    removeLastPointMutation();
    diameter = oldDiameter;
  }

  @Override
  void draw(Graphics2D g2d) {
    g2d.setColor(getColor());
    g2d.fillOval(midPoint.x, midPoint.y, diameter, diameter);
  }
}
