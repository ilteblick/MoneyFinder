package by.bsuir.misoi.regions;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * Created by User-PC on 24.10.2016.
 */
public class MiddleColor {

    public void getColor(BufferedImage original){
        int red=0, green=0, blue=0;
        for(int i=0;i<original.getWidth();i++){
            for(int j=0;j<original.getHeight();j++){
                Color color = new Color(original.getRGB(i,j));
                red+=color.getRed();
                green+=color.getGreen();
                blue+=color.getBlue();
            }
        }

        red = red / (original.getHeight() * original.getWidth());
        green = green / (original.getHeight() * original.getWidth());
        blue = blue / (original.getHeight() * original.getWidth());

        System.out.println(red);
        System.out.println(green);
        System.out.println(blue);
    }
}
