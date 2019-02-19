package evolver;

import java.awt.*;

public interface Trait {


    public abstract void mutateShape();

    public abstract void mutateRGB();

    public abstract void mutateAlpha();

    public abstract void mutateAll();

    public abstract String toCsv();

    public abstract void removeLastMutation();

    public abstract void draw(Graphics2D graphics);




}
