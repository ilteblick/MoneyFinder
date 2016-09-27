package by.bsuir.misoi.image;

import java.awt.image.BufferedImage;
import java.io.IOException;

/**
 * Created by User-PC on 21.09.2016.
 */
public interface Iimage {
    BufferedImage read() throws IOException;
    void write(String output, BufferedImage image) throws IOException ;
}
