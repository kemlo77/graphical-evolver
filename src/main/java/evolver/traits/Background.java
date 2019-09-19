package evolver.traits;

import evolver.TargetImage;
import java.awt.Graphics2D;

public class Background extends Trait {

  public Background() {
  }

  @Override
  public void mutateShape(float degree) {

  }

  @Override
  public void removeLastShapeMutation() {

  }

  @Override
  public void draw(Graphics2D g2d) {
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
