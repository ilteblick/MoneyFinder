package by.bsuir.perseptron;

import sun.java2d.cmm.ColorTransform;

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
            for (int i = 0 ; i < sha.getWidth(); i++) {
                for (int j = 0; j < sha.getHeight(); j++) {

                    Color color = new Color(img.getRGB(i, j));
                    int rgb = (color.getRed() + color.getGreen() + color.getBlue()) / 3;
                    if ( rgb != 0 )
                    {
                        Color color_1 = new Color(sha.getRGB(i, j));
                        int rgb_1 = (color_1.getRed() + color_1.getGreen() + color_1.getBlue()) / 3;
                        if ( rgb_1 != 0 ){

                            Color c = new Color(sha.getRGB(i, j));
                            int red = (int)(c.getRed() + 50 );
                            int green = (int)(c.getGreen() + 50 );
                            int blue = (int)(c.getBlue() + 50 );
                            Color newColor = new Color((red+green+blue) / 3,(red+green+blue)/3,(red+green+blue)/3);
                            sha.setRGB(i, j,newColor.getRGB());

                        }else{
                            Color c = new Color(sha.getRGB(i, j));
                            int red = (int)(c.getRed() + 75 );
                            int green = (int)(c.getGreen() + 75 );
                            int blue = (int)(c.getBlue() + 75);
                            Color newColor = new Color((red+green+blue) / 3,(red+green+blue)/3,(red+green+blue)/3);
                            sha.setRGB(i, j,newColor.getRGB());
                        }
                    }
                }
            }
        }else{
            int w = img.getWidth();
            int h = img.getHeight();
            sha = new BufferedImage(w, h, img.getType());

            for (int i = 0 ; i < w; i++) {
                for (int j = 0; j < h; j++) {
                    Color color = new Color(img.getRGB(i, j));
                    int rgb = (color.getRed() + color.getGreen() + color.getBlue()) / 3;
                    if ( rgb == 0 )
                        sha.setRGB(i, j, 0);
                    else {
                        Color c = new Color(img.getRGB(i, j));
                        int red = (int)(c.getRed() - 180 );
                        int green = (int)(c.getGreen() - 180 );
                        int blue = (int)(c.getBlue() - 180);
                        Color newColor = new Color((red+green+blue) / 3,(red+green+blue)/3,(red+green+blue)/3);
                        sha.setRGB(i, j,newColor.getRGB());
                    }
                }
            }
        }

        ImageIO.write(sha, "png", fil);
    }
}
