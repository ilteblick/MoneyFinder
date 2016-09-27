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
        Scanner in = new Scanner(System.in);
        String name = in.nextLine();
        File file = new File(name);

        return ImageIO.read(file);
    }

    @Override
    public void write(String output, BufferedImage image) throws IOException {
        File file = new File(output);
        //file.getParentFile().mkdirs();
        ImageIO.write(image, "jpg", file);
        System.out.println("done: " + output);
    }
}
