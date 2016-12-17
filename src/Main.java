import by.bsuir.misoi.filter.FilterFactory;
import by.bsuir.misoi.filter.IFilter;
import by.bsuir.misoi.image.Iimage;
import by.bsuir.misoi.image.ImageIml;
import by.bsuir.misoi.regions.MiddleColor;
import by.bsuir.misoi.regions.NumCutter;
import by.bsuir.misoi.regions.Rotator;
import by.bsuir.misoi.regions.Wildfire;
import by.bsuir.perseptron.Checker;
import by.bsuir.perseptron.Perseptron;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;

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

        Rotator rotate = new Rotator(original, wildfire.getMask());
        BufferedImage out = rotate.baby_spin_me_right_n_round();
        imageHelper.write("result_rotator",out);
        
        Checker che = new Checker(out);

        //IFilter filter2 = factory.createFilter(2);
        //MiddleColor middleColor = new MiddleColor();
        //middleColor.getColor(out);

<<<<<<< 32e3cf983e66e6c47b46c6c7cf652deb7e2de03c

        IFilter filter2 = factory.createFilter(2);

        MiddleColor middleColor = new MiddleColor();
        middleColor.getColor(out);

        for(int i=0;i< 0; i++){
            out = filter2.doFilter(out);
        }
        NumCutter cutter = new NumCutter(out);
        BufferedImage cut = cutter.getImage();
        imageHelper.write("result_cutter", cut);
=======
        //for(int i=0;i< 2; i++){
        //    out = filter2.doFilter(out);
        //}
        //NumCutter cutter = new NumCutter(out);
        //BufferedImage cut = cutter.getImage();
        //imageHelper.write("result_cutter", cut);
>>>>>>> 19bf0cbc2ab19d879d4d7241a1b718f07e3c2170


        //Wildfire wildfire1 = new Wildfire(cut,cut);
        //wildfire1.findRegions();
        //ArrayList<BufferedImage> images = wildfire1.findMain();
        
        //Perseptron ps = new Perseptron(images);
        //ps.doPerseptron();
                
        System.out.print("GOTOVO !");
    }
}
