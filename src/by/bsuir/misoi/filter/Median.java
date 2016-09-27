package by.bsuir.misoi.filter;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Arrays;

/**
 * Created by User-PC on 21.09.2016.
 */
public class Median implements IFilter{

    @Override
    public BufferedImage doFilter(BufferedImage original) {
        Color[] pixel=new Color[9];
        int[] R=new int[9];
        int[] B=new int[9];
        int[] G=new int[9];

        BufferedImage result = new BufferedImage(original.getWidth(),
                original.getHeight(), original.getType());

        for(int i=1;i<original.getWidth()-1;i++)
            for(int j=1;j<original.getHeight()-1;j++)
            {
                pixel[0]=new Color(original.getRGB(i-1,j-1));
                pixel[1]=new Color(original.getRGB(i-1,j));
                pixel[2]=new Color(original.getRGB(i-1,j+1));
                pixel[3]=new Color(original.getRGB(i,j+1));
                pixel[4]=new Color(original.getRGB(i+1,j+1));
                pixel[5]=new Color(original.getRGB(i+1,j));
                pixel[6]=new Color(original.getRGB(i+1,j-1));
                pixel[7]=new Color(original.getRGB(i,j-1));
                pixel[8]=new Color(original.getRGB(i,j));
                for(int k=0;k<9;k++){
                    R[k]=pixel[k].getRed();
                    B[k]=pixel[k].getBlue();
                    G[k]=pixel[k].getGreen();
                }
                Arrays.sort(R);
                Arrays.sort(G);
                Arrays.sort(B);
                result.setRGB(i,j,new Color(R[4],B[4],G[4]).getRGB());
            }
        return result;
    }
}
