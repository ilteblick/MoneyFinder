package by.bsuir.perseptron;

import by.bsuir.misoi.filter.FilterFactory;
import by.bsuir.misoi.filter.IFilter;

import java.awt.*;
import java.awt.color.ColorSpace;
import java.awt.image.*;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import javax.imageio.ImageIO;

public class PRE_Perseptron {

    private BufferedImage image;
    private BufferedImage filtered;
    private boolean isNew;
    private  boolean isFront;

    public PRE_Perseptron(BufferedImage original, boolean isNew, boolean isFront) throws IOException {

        if (!isNew) return;

        FilterFactory factory = new FilterFactory();
        IFilter binarization = factory.createFilter(1);
        IFilter median = factory.createFilter(2);
        IFilter gray = factory.createFilter(3);
        image = original;
        this.isNew = isNew;
        this.isFront = isFront;
        filtered = binarization.doFilter(median.doFilter(median.doFilter(gray.doFilter(original))));
        filtered = cut();
    }

    private BufferedImage scale(BufferedImage img) throws IOException {

        double scale_x;
        double scale_y;

        if ( isFront == false ) {
            scale_x = 60.0 / img.getWidth();
            scale_y = 45.0 / img.getHeight();
        } else {
            scale_x = 60.0 / img.getWidth();
            scale_y = 45.0 / img.getHeight();
        }
        BufferedImage bi = new BufferedImage(60,45, img.getType());

        Graphics2D grph = (Graphics2D) bi.getGraphics();
        grph.scale(scale_x, scale_y);
        grph.drawImage(img, 0, 0, null);
        grph.dispose();

        return bi;
    }





    private BufferedImage cut() throws IOException {
        int n_w = (int) Math.ceil(filtered.getWidth() * 0.154);
        int n_h = (int) Math.ceil(filtered.getHeight() * 0.2);

        BufferedImage out = new BufferedImage(n_w, n_h, filtered.getType());

        for (int i = 0; i < n_w; i++){
            for (int j = 0; j < n_h; j++){
                if ( isFront )
                    out.setRGB(i, j, filtered.getRGB(i+10, j+16));
                else
                    out.setRGB(i, n_h -1-j, filtered.getRGB(i + 10, filtered.getHeight() - j - 11));
            }
        }
        return scale(out);
    }



    // img for perceptron
    public BufferedImage getImgForPeseptron(){
        return filtered;
    }
}
