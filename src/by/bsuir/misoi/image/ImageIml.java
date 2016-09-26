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
    public void read() throws IOException {
        Scanner in = new Scanner(System.in);
        String name = in.nextLine();
        File file = new File(name);

        BufferedImage original = ImageIO.read(file);

        System.out.print("HYI");


    }

    @Override
    public void write() {

    }
}
