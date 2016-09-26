import by.bsuir.misoi.filter.GrayScale;
import by.bsuir.misoi.image.Iimage;
import by.bsuir.misoi.image.ImageIml;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Main {

    public static void main(String[] args) throws IOException {
        //Iimage imageHelper = new ImageIml());
        //imageHelper.read();
    	
    	File input = new File("1.jpeg");
    	BufferedImage image = ImageIO.read(input);
        
        GrayScale SashaGray = new GrayScale();
        SashaGray.doFilter(image);
    	
    }
}
