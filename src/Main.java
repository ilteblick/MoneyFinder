import by.bsuir.misoi.filter.FilterFactory;
import by.bsuir.misoi.filter.IFilter;
import by.bsuir.misoi.image.Iimage;
import by.bsuir.misoi.image.ImageIml;
import by.bsuir.misoi.regions.Rotator;
import by.bsuir.misoi.regions.Wildfire;
import by.bsuir.perseptron.Checker;
import by.bsuir.perseptron.PRE_Perseptron;

import java.awt.image.BufferedImage;
import java.io.IOException;

public class Main {

    public static void main(String[] args) throws IOException {
        Iimage imageHelper = new ImageIml();
        BufferedImage original = imageHelper.read();
        
        FilterFactory factory = new FilterFactory();
        IFilter filter = factory.createFilter(1); // Binarization
        IFilter filter3 = factory.createFilter(2); //Median
        BufferedImage result = filter.doFilter(filter3.doFilter(original));
        imageHelper.write("result111",result);

        Wildfire wildfire = new Wildfire(result,original);
        BufferedImage area = wildfire.findRegions();
        imageHelper.write("result_wild",area);

        Rotator rotator = new Rotator();
        BufferedImage rotate_img = rotator.rotate_and_cut(wildfire.getOrig());
        imageHelper.write("img_rotator", rotate_img);

        Checker che = new Checker(rotate_img);
        BufferedImage che_img = che.getImg();
        imageHelper.write("img_checker", che_img);

        PRE_Perseptron PREPerseptron = new PRE_Perseptron(che_img, che.isNew(), che.isFront());

                
        System.out.print("GOTOVO !");
    }
}
