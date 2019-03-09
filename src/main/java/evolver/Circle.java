package evolver;

import java.awt.Graphics2D;
import java.awt.Point;

class Circle extends Trait {

  private Point midPoint;
  private int diameter;
  private int oldDiameter;
  //TODO: Annat max-värde för cirkel-diameter
  private final int maxDiameter;

  Circle(int width, int height) {
    super(width, height);

    this.maxDiameter = height / 2;
    this.midPoint = generateRandomPoint();
    this.diameter = Utils.mutateInWholeInterval(1, maxDiameter);
    this.oldDiameter = this.diameter;

  }

  @Override
  void mutateShape(float degree) {
    mutatePoint(midPoint, degree);

    oldDiameter = diameter;
    diameter = Utils.mutateInInterval(1, maxDiameter, diameter, degree);
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
  public String toSvg() {
    return
        "<circle "
            + "cx=\"" + midPoint.x + "\" "
            + "cy=\"" + midPoint.y + "\" "
            + "r=\"" + (diameter / 2f) + "\" "
            + "fill="
            + super.svgColorInfo()
            + "/>";
  }
}