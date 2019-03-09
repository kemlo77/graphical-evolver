package evolver;

import java.awt.Graphics2D;

class Background extends Trait {

  Background(int width, int height) {
    super(width, height);
  }

  @Override
  void mutateShape(float degree) {

  }

  @Override
  void removeLastShapeMutation() {

  }

  @Override
  void draw(Graphics2D g2d) {
    g2d.setPaint(getColor());
    g2d.fillRect(0, 0, getWidth(), getHeight());
  }


  @Override
  public String toSvg() {
    return "<rect width=\"" + getWidth() + "\" height=\"" + getHeight() + "\""
        + " fill=" + super.svgColorInfo() + "/>";
  }
}
