package evolver.traits;

import evolver.TargetImage;
import evolver.Utils;
import java.awt.Graphics2D;
import java.awt.Point;

public class Circle extends Trait {

  private Point midPoint;
  private int diameter;
  private int oldDiameter;
  private final int maxDiameter;

  /**
   * A regular constructor that will generate a random Circle object.
   */
  Circle() {
    //TODO:Inventera vilka filer som refererar till TargetImage.getImageHeight eller width
    this.maxDiameter = TargetImage.getImageHeight() / 2;
    this.midPoint = generateRandomPoint();
    this.diameter = Utils.mutateInWholeInterval(1, maxDiameter);
    this.oldDiameter = this.diameter;

  }

  /**
   * A Copy Constructor for the Circle class. It delivers a deep copy.
   *
   * @param circle The Circle object used when creating the new one.
   */
  public Circle(Circle circle) {
    this.maxDiameter = circle.maxDiameter;
    this.midPoint = new Point(circle.midPoint);
    this.diameter = circle.diameter;
    this.oldDiameter = circle.oldDiameter;
    this.setColor(circle.getColor());
  }

  @Override
  public void mutateShape(float degree) {
    mutatePoint(midPoint, degree);

    oldDiameter = diameter;
    diameter = Utils.mutateInInterval(1, maxDiameter, diameter, degree);
  }

  @Override
  public void removeLastShapeMutation() {
    removeLastPointMutation();
    diameter = oldDiameter;
  }

  @Override
  public void draw(Graphics2D g2d) {
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