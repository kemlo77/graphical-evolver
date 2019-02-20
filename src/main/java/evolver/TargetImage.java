package evolver;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

class TargetImage {

    private int imageWidth;
    private int imageHeight;
    private int imageType;
    private BufferedImage bufferedImage;


    int getImageWidth() {
        return imageWidth;
    }

    int getImageHeight() {
        return imageHeight;
    }

    int getImageType() {
        return imageType;
    }

    BufferedImage getBufferedImage() {
        return bufferedImage;
    }

    TargetImage(File file) throws IOException {
        bufferedImage = ImageIO.read(file);
        imageHeight = bufferedImage.getHeight();
        imageWidth = bufferedImage.getWidth();
        imageType = bufferedImage.getType();
    }
}
