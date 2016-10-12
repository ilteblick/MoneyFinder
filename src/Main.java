import by.bsuir.misoi.filter.FilterFactory;
import by.bsuir.misoi.filter.IFilter;
import by.bsuir.misoi.image.Iimage;
import by.bsuir.misoi.image.ImageIml;
import by.bsuir.misoi.regions.Rotator;
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
        Wildfire wildfire = new Wildfire(result,original);
        BufferedImage area = wildfire.findRegions();
        imageHelper.write("result_wild",area);
        
        int[][] mask = wildfire.getMask();
        Rotator rotate = new Rotator(original, mask);
        BufferedImage out = rotate.baby_spin_me_right_n_round();
        imageHelper.write("result_rotator",out);
        
        System.out.print("GOTOVO !");
    }
}
