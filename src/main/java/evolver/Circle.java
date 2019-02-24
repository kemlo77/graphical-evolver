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
    //TODO: kolla matten i denna. Kommer i 50% av fallen hamna en halv pixel fel?
    int x = midPoint.x - (diameter / 2);
    int y = midPoint.y - (diameter / 2);
    g2d.setColor(getColor());
    g2d.fillOval(x, y, diameter, diameter);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb
        .append("<circle ")
        .append("cx=\"").append(midPoint.x).append("\" ")
        .append("cy=\"").append(midPoint.y).append("\" ")
        .append("r=\"").append(diameter / 2f).append("\" ")
        .append("fill=")
        //color info
        .append(super.toString())
        .append("/>");
    return sb.toString();
  }
}
