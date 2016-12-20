package by.bsuir.misoi.image;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Scanner;

/**
 * Created by User-PC on 21.09.2016.
 */
public class ImageIml implements Iimage {
    @Override
    public BufferedImage read() throws IOException {
//        File file = new File("20.1.jpg");
//        File file = new File("20.1.1.jpg");
//        File file = new File("100.1.jpg");

//        File file = new File("res/a_20.0.jpg");


        File file = new File("res/a_20.1.jpg");
//        File file = new File("res/a_20.2.jpg");

//        File file = new File("res/a_20.3.jpg");
//        File file = new File("res/a_20.0.jpg");

        return ImageIO.read(file);
    }

    @Override
    public void write(String output, BufferedImage image) throws IOException {
        File ouptut = new File(output + ".png");
        ImageIO.write(image, "png", ouptut);
    }
}
