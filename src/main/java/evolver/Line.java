package evolver;

import java.awt.BasicStroke;
import java.awt.Graphics2D;
import java.awt.Point;
import java.util.Random;

public class Line extends Trait {

  private Point p1;
  private Point p2;
  private int lineWidth;
  private int oldLineWidth;
  private final int maxLineWidth = 5;


  Line(int width, int height) {
    super(width, height);

    this.p1 = generateRandomPoint();
    this.p2 = generateRandomPoint();

    this.lineWidth = Utils.mutateInWholeInterval(1, maxLineWidth);

  }

  @Override
  void mutateShape() {
    Random rand = new Random();
    if (rand.nextBoolean()) {
      mutatePoint(p1);
    } else {
      mutatePoint(p1);
    }
    oldLineWidth = lineWidth;
    lineWidth = Utils.mutateInWholeInterval(1, maxLineWidth);
  }

  @Override
  void removeLastShapeMutation() {
    removeLastPointMutation();
    lineWidth = oldLineWidth;
  }

  @Override
  void draw(Graphics2D g2d) {

    g2d.setStroke(new BasicStroke(lineWidth));
    g2d.setColor(getColor());
    g2d.drawLine(p1.x, p1.y, p2.x, p2.y);
  }
}
