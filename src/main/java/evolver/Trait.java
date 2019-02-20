package evolver;

import java.awt.*;

public interface Trait {


    void mutateShape();

    void mutateRGB();

    void mutateAlpha();

    void mutateAll();

    void removeLastMutation();

    void draw(Graphics2D graphics);


}
