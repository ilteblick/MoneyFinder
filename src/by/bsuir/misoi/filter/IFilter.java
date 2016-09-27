package by.bsuir.misoi.filter;

import java.awt.image.BufferedImage;
import java.io.IOException;

/**
 * Created by User-PC on 21.09.2016.
 */
public interface IFilter {
    BufferedImage doFilter(BufferedImage original) throws IOException;
}
