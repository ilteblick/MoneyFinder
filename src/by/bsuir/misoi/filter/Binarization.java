package by.bsuir.misoi.filter;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * Created by User-PC on 21.09.2016.
 */
public class Binarization implements IFilter{

    private int otsuTreshold(BufferedImage original){
        int[] histogram = new int[256];

        for (int i = 0; i < original.getWidth(); i++) {
            for (int j = 0; j < original.getHeight(); j++) {
                Color pixel = new Color(original.getRGB(i, j));
                int color = (pixel.getRed() + pixel.getBlue() + pixel
                        .getGreen()) / 3;
                histogram[color]++;
            }
        }

        // Total number of pixels
        int total = original.getHeight()*original.getWidth();

        float sum = 0;
        for (int i=0 ; i<256 ; i++)
            sum += i * histogram[i];

        float sumB = 0;
        int wB = 0;
        int wF = 0;

        float varMax = 0;
        int threshold = 0;

        for (int i=0 ; i<256 ; i++) {
            wB += histogram[i];               // Weight Background
            if (wB == 0) continue;

            wF = total - wB;                 // Weight Foreground
            if (wF == 0) break;

            sumB += (float) (i * histogram[i]);

            float mB = sumB / wB;            //
            float mF = (sum - sumB) / wF;    //

            // Calculate Between Class Variance
            float varBetween = (float)wB * (float)wF * (mB - mF) * (mB - mF);

            // Check if new maximum found
            if (varBetween > varMax) {
                varMax = varBetween;
                threshold = i;
            }


    }
        return threshold;
    }

    @Override
    public BufferedImage doFilter(BufferedImage original) {
        int oldPixel;
        int newPixel;

        int threshold = otsuTreshold(original);

        BufferedImage binarized = new BufferedImage(original.getWidth(),
                original.getHeight(), original.getType());

        for (int i = 0; i < original.getWidth(); i++) {
            for (int j = 0; j < original.getHeight(); j++) {

                Color pixelColor = new Color(original.getRGB(i, j));
                oldPixel = (pixelColor.getRed() + pixelColor.getBlue() + pixelColor
                        .getGreen()) / 3;
                int alpha = new Color(original.getRGB(i, j)).getAlpha();
                if (oldPixel < threshold) {
                    newPixel = 255;
                } else {
                    newPixel = 0;
                }
                newPixel = colorToRGB(alpha, newPixel, newPixel, newPixel);
                binarized.setRGB(i, j, newPixel);

            }
        }

        return binarized;
    }

    private static int colorToRGB(int alpha, int red, int green, int blue) {

        int newPixel = 0;
        newPixel += alpha;
        newPixel = newPixel << 8;
        newPixel += red; newPixel = newPixel << 8;
        newPixel += green; newPixel = newPixel << 8;
        newPixel += blue;

        return newPixel;

    }
}
