package evolver;

import java.awt.Graphics2D;

class Background extends Trait {

  Background() {
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
    g2d.fillRect(0, 0, TargetImage.getImageWidth(), TargetImage.getImageHeight());
  }


  @Override
  public String toSvg() {
    return "<rect width=\"" + TargetImage.getImageWidth() + "\" height=\"" + TargetImage
        .getImageHeight() + "\""
        + " fill=" + super.svgColorInfo() + "/>";
  }
}
