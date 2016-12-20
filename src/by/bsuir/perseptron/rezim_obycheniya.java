package by.bsuir.perseptron;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Scanner;

/**
 * Created by Denis on 20.12.2016.
 */
public class rezim_obycheniya {

    File file;
    BufferedImage image;

    public rezim_obycheniya(BufferedImage img) throws IOException {
        Scanner in = new Scanner(System.in);
        System.out.print("side (0 / 1): ");
        int side = in.nextInt();
        System.out.print("num (5 / 10 / 20 / 50 / 100 ...): ");
        int num = in.nextInt();

        String file_path = "res/perseptron/";
        if ( side == 0){
            file_path += String.format("front/%d.png", num);
        }else{
            file_path += String.format("back/%d.png", num);
        }

        File fil = new File(file_path);
        BufferedImage sha;
        if ( fil.exists() ){
            sha = ImageIO.read(fil);
        }else{
            int w = img.getWidth();
            int h = img.getHeight();
            sha = new BufferedImage(w, h, img.getType());
            for (int i = 0 ; i < w; i++) {
                for (int j = 0; j < h; j++) {

                    Color color = new Color(img.getRGB(i, j));
                    int rgb = (color.getRed() + color.getGreen() + color.getBlue()) / 3;

                    // 0 - black
                    // 255 - white
                    if ( rgb == 0 )
                        sha.setRGB(i, j, 0);
                    else
                        // needed
                        sha.setRGB(i, j, (int)(255*0.3));
                }
            }
        }

        File filee = new File("res/perseptron/lol.png");
        ImageIO.write(sha, "png", filee);
    }
}
