package evolver;

import java.awt.Graphics2D;
import java.awt.Point;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Polygon extends Trait {

  //TODO: ej komplexa polygoner?
  //TODO: fler konstruktorer ex: utan parametrar
  //enkel: 8 vertices, random color
  // utförlig: Point-list, r,g,b, transparens


  private List<Point> pointList;


  Polygon(int numberOfVertices) {
    pointList = new ArrayList<>();
    for (int i = 0; i < numberOfVertices; i++) {
      pointList.add(generateRandomPoint());
    }
  }


  @Override
  public void mutateShape(float degree) {
    Random rand = new Random();
    int mutadedPointNo = rand.nextInt(pointList.size());
    mutatePoint(pointList.get(mutadedPointNo), degree);
  }


  @Override
  public void removeLastShapeMutation() {
    removeLastPointMutation();
  }


  @Override
  public void draw(Graphics2D g2d) {
    //Seems to be about 7% faster than drawing with: moveTo, lineTo, closePath...
    java.awt.Polygon p = new java.awt.Polygon();
    for (int i = 1; i < pointList.size(); i++) {
      p.addPoint(pointList.get(i).x, pointList.get(i).y);
    }
    g2d.setPaint(getColor());
    g2d.fillPolygon(p);
  }


  @Override
  public String toSvg() {
    StringBuilder sb = new StringBuilder();
    sb.append("<polygon points=\"");
    for (Point p : pointList) {
      sb.append(p.x).append(",")
          .append(p.y).append(" ");
    }
    sb.append("\" ")
        .append("fill=");
    //color info
    sb.append(super.svgColorInfo());
    sb.append("/>");
    return sb.toString();
  }


}
