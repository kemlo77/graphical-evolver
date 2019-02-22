package evolver;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.geom.Path2D;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class Polygon extends Trait {

  //TODO: ej komplexa polygoner?
  //TODO: fler konstruktorer ex: utan parametrar
  //enkel: 8 vertices, random color
  // utförlig: Point-list, r,g,b, transparens


  private List<Point> pointList;


  Polygon(int numberOfVertices, int width, int height) {

    //TODO: hantera width och height bättre så att det inte måste finnas med i varje objekt
    setWidth(width);
    setHeight(height);

    ArrayList<Point> points = new ArrayList<>();

    for (int i = 0; i < numberOfVertices; i++) {
      int x = ThreadLocalRandom.current().nextInt(0, width + 1);
      int y = ThreadLocalRandom.current().nextInt(0, height + 1);
      points.add(new Point(x, y));
    }
    this.pointList = points;

    int r = ThreadLocalRandom.current().nextInt(0, 255);
    int g = ThreadLocalRandom.current().nextInt(0, 255);
    int b = ThreadLocalRandom.current().nextInt(0, 255);

    this.color = new Color(r, g, b, 10);
  }


  @Override
  public void mutateShape() {
    Random rand = new Random();
    int mutadedPointNo = rand.nextInt(pointList.size());
    mutatePoint(pointList.get(mutadedPointNo));
  }


  @Override
  public void removeLastShapeMutation() {
    removeLastPointMutation();
  }

  @Override
  public void draw(Graphics2D g2d) {
    Path2D.Double polygon = new Path2D.Double();
    polygon.moveTo(pointList.get(0).x, pointList.get(0).y);

    for (int i = 1; i < pointList.size(); i++) {
      polygon.lineTo(pointList.get(i).x, pointList.get(i).y);
    }
    polygon.closePath();
    g2d.setPaint(color);
    g2d.fill(polygon);
  }

  @Override
  public String toString() {

    StringBuilder sb = new StringBuilder();
    sb.append("[")
        .append(color.getRed()).append(",")
        .append(color.getGreen()).append(",")
        .append(color.getBlue()).append(",")
        .append(color.getAlpha()).append("] ");
    for (Point p : pointList) {
      sb.append(p.x).append(",")
          .append(p.y).append(" ");
    }
    return sb.toString();
  }


}
