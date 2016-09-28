import by.bsuir.misoi.filter.FilterFactory;
import by.bsuir.misoi.filter.IFilter;
import by.bsuir.misoi.image.Iimage;
import by.bsuir.misoi.image.ImageIml;
import by.bsuir.misoi.regions.Wildfire;

import java.awt.image.BufferedImage;
import java.io.IOException;

public class Main {

    public static void main(String[] args) throws IOException {
        Iimage imageHelper = new ImageIml();
        BufferedImage original = imageHelper.read();
        FilterFactory factory = new FilterFactory();
        IFilter filter = factory.createFilter(1);
        BufferedImage result = filter.doFilter(original);
        Wildfire wildfire = new Wildfire();
        wildfire.findRegions(result);
        imageHelper.write("result",result);
    }
}
