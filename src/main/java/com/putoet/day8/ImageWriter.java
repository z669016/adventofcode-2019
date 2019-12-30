package com.putoet.day8;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;

public class ImageWriter {
    private static BufferedImage createImageFromLayer(List<Integer> pixels, int width, int height) {
        final BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        for (int idy = 0; idy < height; idy++) {
            for (int idx = 0; idx < width; idx++) {
                image.setRGB(idx, idy, pixels.get(idy * width + idx) == 0 ? Color.BLACK.getRGB() : Color.WHITE.getRGB());
            }
        }
        return image;
    }

    public static boolean saveImage(String fileName, Layer layer) throws IOException {
        final BufferedImage image = createImageFromLayer(layer.pixels(), layer.dimension().x(), layer.dimension().y());
        final File outputFile = new File(fileName);
        return ImageIO.write(image, "PNG", outputFile);
    }
}
