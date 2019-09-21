package evolver.traits;

import evolver.Utils;
import java.awt.BasicStroke;
import java.awt.Graphics2D;
import java.awt.Point;
import java.util.Random;

public class Line extends Trait {

  private Point p1;
  private Point p2;
  private int lineWidth;
  private int oldLineWidth;
  private final int maxLineWidth = 7;


  /**
   * A regular constructor that will generate a random Line object.
   */
  Line() {
    this.p1 = generateRandomPoint();
    this.p2 = generateRandomPoint();

    this.lineWidth = Utils.mutateInWholeInterval(1, maxLineWidth);
    this.oldLineWidth = this.lineWidth;

  }

  /**
   * A Copy Constructor for the Line class. It delivers a deep copy.
   *
   * @param line The line object used when creating a new one.
   */
  public Line(Line line) {
    this.p1 = new Point(line.p1);
    this.p2 = new Point(line.p2);
    this.lineWidth = line.lineWidth;
    this.oldLineWidth = line.oldLineWidth;
    this.setColor(line.getColor());
  }

  @Override
  public void mutateShape(float degree) {
    Random rand = new Random();
    if (rand.nextBoolean()) {
      mutatePoint(p1, degree);
    } else {
      mutatePoint(p1, degree);
    }
    oldLineWidth = lineWidth;
    lineWidth = Utils.mutateInInterval(1, maxLineWidth, lineWidth, degree);
  }

  @Override
  public void removeLastShapeMutation() {
    removeLastPointMutation();
    lineWidth = oldLineWidth;
  }

  @Override
  public void draw(Graphics2D g2d) {

    g2d.setStroke(new BasicStroke(lineWidth));
    g2d.setColor(getColor());
    g2d.drawLine(p1.x, p1.y, p2.x, p2.y);
  }


  @Override
  public String toSvg() {
    return "<line "
        + "x1=\"" + p1.x + "\" "
        + "y1=\"" + p1.y + "\" "
        + "x2=\"" + p2.x + "\" "
        + "y2=\"" + p2.y + "\" "
        + "stroke-width=\"" + lineWidth + "\" "
        + "stroke=" + super.svgColorInfo() + " "
        + "/>";
  }
}