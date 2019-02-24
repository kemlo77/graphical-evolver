package evolver;

import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.geom.Path2D;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Polygon extends Trait {

  //TODO: ej komplexa polygoner?
  //TODO: fler konstruktorer ex: utan parametrar
  //enkel: 8 vertices, random color
  // utförlig: Point-list, r,g,b, transparens


  private List<Point> pointList;


  Polygon(int numberOfVertices, int width, int height) {
    super(width, height);

    pointList = new ArrayList<>();
    for (int i = 0; i < numberOfVertices; i++) {
      pointList.add(generateRandomPoint());
    }
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
    g2d.setPaint(getColor());
    g2d.fill(polygon);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("<polygon points=\"");
    for (Point p : pointList) {
      sb.append(p.x).append(",")
          .append(p.y).append(" ");
    }
    sb.append("\" ")
        .append("fill=");
    //color info
    sb.append(super.toString());
    sb.append("/>");
    return sb.toString();
  }


}
