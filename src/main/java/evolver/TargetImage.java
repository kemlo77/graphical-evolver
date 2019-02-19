package evolver;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class TargetImage {

    private int imageWidth;
    private int imageHeight;
    private int imageType;
    private BufferedImage bufferedImage;


    public int getImageWidth() {
        return imageWidth;
    }

    public int getImageHeight() {
        return imageHeight;
    }

    public int getImageType() {
        return imageType;
    }

    public BufferedImage getBufferedImage() {
        return bufferedImage;
    }

    public TargetImage(File file) throws IOException {
        bufferedImage = ImageIO.read(file);
        imageHeight=bufferedImage.getHeight();
        imageWidth=bufferedImage.getWidth();
        imageType = bufferedImage.getType();
    }
}
