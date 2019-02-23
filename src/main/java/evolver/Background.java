package evolver;

import java.awt.Graphics2D;

class Background extends Trait {

  Background(int width, int height) {
    super(width, height);
    setWidth(width);
    setHeight(height);

  }

  @Override
  void mutateShape() {

  }

  @Override
  void removeLastShapeMutation() {

  }

  @Override
  void draw(Graphics2D g2d) {
    g2d.setPaint(getColor());
    g2d.fillRect(0, 0, getWidth(), getHeight());
  }
}
