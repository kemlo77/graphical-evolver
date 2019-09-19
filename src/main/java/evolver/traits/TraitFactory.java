package evolver.traits;

import evolver.traits.Background;
import evolver.traits.Circle;
import evolver.traits.Line;
import evolver.traits.Polygon;
import evolver.traits.Trait;

public class TraitFactory {

  public static Trait getTrait(String trait) {

    if ("circle".equalsIgnoreCase(trait)) {
      return new Circle();
    } else if ("polygon".equalsIgnoreCase(trait)) {
      return new Polygon(6);
    } else if ("background".equalsIgnoreCase(trait)) {
      return new Background();
    } else if ("line".equalsIgnoreCase(trait)) {
      return new Line();
    }
    return null;
  }


}
