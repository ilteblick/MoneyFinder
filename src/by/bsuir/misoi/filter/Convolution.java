package by.bsuir.misoi.filter;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.color.ColorSpace;
import java.awt.image.*;
import java.io.ByteArrayInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by User-PC on 27.09.2016.
 */
public class Convolution implements IFilter {
    @Override
    public BufferedImage doFilter(BufferedImage original) throws IOException {

        int n = 5;
        byte[] imageBytes = ((DataBufferByte) original.getData().getDataBuffer()).getData();
        int width = original.getWidth();
        int height = original.getHeight();
        double[][] kernel = {
                {0, 0, 0, 0, 0},
                {0, -1, -1, -1, 0},
                {0, -1, 0, -1, 0},
                {0, -1, -1, -1, 0},
                {0, 0, 0, 0, 0}
        };

        byte[] outputBytes = new byte[imageBytes.length];

        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                double rSum = 0, gSum = 0, bSum = 0, kSum = 0;
                for (int i = 0; i < n; i++) {
                    for (int j = 0; j < n; j++) {
                        int pixelPosX = x + (i - (n / 2));
                        int pixelPosY = y + (j - (n / 2));
                        if ((pixelPosX < 0) || (pixelPosX >= width) || (pixelPosY < 0) || (pixelPosY >= height))
                            continue;
                        byte r = imageBytes[3 * (width * pixelPosY + pixelPosX) + 0];
                        byte g = imageBytes[3 * (width * pixelPosY + pixelPosX) + 1];
                        byte b = imageBytes[3 * (width * pixelPosY + pixelPosX) + 2];

                        double kernelVal = kernel[i][j];

                        rSum += r * kernelVal;
                        gSum += g * kernelVal;
                        bSum += b * kernelVal;

                        kSum += kernelVal;
                        if (kSum <= 0) kSum = 1;

                        //Контролируем переполнения переменных
                        rSum /= kSum;
                        if (rSum < 0) rSum = 0;
                        if (rSum > 255) rSum = 255;

                        gSum /= kSum;
                        if (gSum < 0) gSum = 0;
                        if (gSum > 255) gSum = 255;

                        bSum /= kSum;
                        if (bSum < 0) bSum = 0;
                        if (bSum > 255) bSum = 255;

                        outputBytes[3 * (width * y + x) + 0] = (byte) rSum;
                        outputBytes[3 * (width * y + x) + 1] = (byte) gSum;
                        outputBytes[3 * (width * y + x) + 2] = (byte) bSum;
                    }
                }
            }
        }
        InputStream inputStream = new ByteArrayInputStream(outputBytes);
        //BufferedImage image = ImageIO.read(inputStream);

        BufferedImage image = createRGBImage(outputBytes, width, height);


        return image;
    }

    private static BufferedImage createRGBImage(byte[] bytes, int width, int height) {
        DataBufferByte buffer = new DataBufferByte(bytes, bytes.length);
        ColorModel cm = new ComponentColorModel(ColorSpace.getInstance(ColorSpace.CS_sRGB), new int[]{8, 8, 8}, false, false, Transparency.OPAQUE, DataBuffer.TYPE_BYTE);
        return new BufferedImage(cm, Raster.createInterleavedRaster(buffer, width, height, width * 3, 3, new int[]{0, 1, 2}, null), false, null);
    }
}