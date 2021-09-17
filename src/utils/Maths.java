package utils;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;

public class Maths {

    public static BufferedImage scaleImage(BufferedImage inputImage, float scale)
    {
        int scaledHeight = (int) (inputImage.getHeight() * scale);
        int scaledWidth = (int) (inputImage.getWidth() * scale);

        // creates output image
        BufferedImage outputImage = new BufferedImage(scaledWidth,
                scaledHeight, inputImage.getType());

        // scales the input image to the output image
        Graphics2D g2d = outputImage.createGraphics();
        g2d.drawImage(inputImage, 0, 0, scaledWidth, scaledHeight, null);
        g2d.dispose();

        return outputImage;
    }

    public static int getRandom(int range)
    {
        return (int)(Math.random() * range);
    }

}
