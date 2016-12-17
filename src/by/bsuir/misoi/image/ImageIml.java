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
//        Scanner in = new Scanner(System.in);
//        String name = in.nextLine();
<<<<<<< 32e3cf983e66e6c47b46c6c7cf652deb7e2de03c
        File file = new File("t9.jpg");

=======
    	
    	
       // File file = new File("10.2.jpg");
        File file = new File("5.1.jpg");
       // File file = new File("10.2.1.jpg");
       // File file = new File("20.1.jpg");
          
          
>>>>>>> 19bf0cbc2ab19d879d4d7241a1b718f07e3c2170
        return ImageIO.read(file);
    }

    @Override
    public void write(String output, BufferedImage image) throws IOException {
        File ouptut = new File(output + ".png");
        ImageIO.write(image, "png", ouptut);
    }
}
