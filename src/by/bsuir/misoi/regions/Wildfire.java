package by.bsuir.misoi.regions;

import by.bsuir.misoi.entity.Point;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Stack;

/**
 * Created by User-PC on 28.09.2016.
 */
public class Wildfire {

    public Wildfire(BufferedImage original,BufferedImage lol){
        this.width = original.getWidth();
        this.height = original.getHeight();
        this.original = original;
        this.lab = new int[width][height];
        this.mask = new int[width][height];
        this.lol = lol;
    }
    
    int width,height;
    BufferedImage original,lol;
    int[][] lab;
    int[][] mask = null;


    public BufferedImage findRegions() throws IOException {
        for(int i=0;i<width;i++){
            for(int j=0;j<height;j++){
                lab[i][j] = 0;
                mask[i][j] = -1;
            }
        }
        Stack<Point> stack = new Stack<>();
        ArrayList<Point> RegionList = new ArrayList<>();
        int flag = 1;
        for(int i=1;i<width-1;i++){
            for(int j=1;j<height-1;j++) {
                Color color = new Color(original.getRGB(i,j));
                int pixel = color.getRed() + color.getBlue() + color.getGreen();
                if((lab[i][j] == 0)&&(pixel <100)) {
                    //flag++;
                    lab[i][j] = flag;
                    //if(setFlag(i, j, flag)){
                        //flag++;
                    //};
                }
            }
        }
       // for(int i=0;i<width;i++){
       //    for(int j=0;j<height;j++){
       //         System.out.print(String.valueOf(lab[j][i])+ " ");
       //     }
       //     System.out.println();
       // }
        
        return findSize(lab);

    }

    private BufferedImage findSize(int[][] lab) throws IOException {

        int xmin=999999999,xmax=-1,ymin=999999999,ymax=-1;
        for(int i=0;i<width;i++){
            for(int j=0;j<height;j++) {
                if(lab[i][j] == 1){
                    if(xmin > i) xmin = i;
                    if(xmax < i) xmax = i;
                    if(ymin > j) ymin = j;
                    if(ymax < j) ymax = j;
                }
            }
        }
        int newWidth = xmax-xmin+1;
        int newHeight = ymax-ymin+1;
        //int oldPixel,newPixel;
        BufferedImage binarized = new BufferedImage(newWidth,
                newHeight, lol.getType());
        for(int i=xmin;i<=xmax;i++){
            for (int j=ymin;j<=ymax;j++){
            	
                //Color pixelColor = new Color(lol.getRGB(i, j));
                //oldPixel = (pixelColor.getRed() + pixelColor.getBlue() + pixelColor
                //        .getGreen()) ;
                int alpha = new Color(lol.getRGB(i, j)).getAlpha();
                Color color = new Color(lol.getRGB(i, j));
                
                if (lab[i][j] == 1) {
                    //newPixel = colorToRGB(alpha,pixelColor.getRed(),pixelColor.getGreen(),pixelColor.getBlue());
                    binarized.setRGB(i-xmin,j-ymin,-1);
                    
                    int pixel = colorToRGB(alpha, color.getRed(), color.getGreen(), color.getBlue());
                    mask[i][j] = pixel;
                } else {
                    //newPixel = colorToRGB(alpha, 255, 255, 255);
                    binarized.setRGB(i-xmin, j-ymin,0);
                }
            }
        }

        return binarized;
    }

    private boolean setFlag(int x,int y, int flag){
        for(int k = x-1;k<=x+1;k++){
            for(int l= y-1;l<=y+1;l++){
                Color color = new Color(original.getRGB(x,y));
                int oldPix = color.getRed() + color.getBlue() + color.getGreen();
                Color color1 = new Color(original.getRGB(k,l));
                int newPix = color1.getRed() + color1.getBlue() + color1.getGreen();
                if(((oldPix >= newPix-100) && (oldPix <= newPix+100 )) && !((x == k) && (y == l)) && (lab[k][l] == 0)){
                    lab[k][l] = flag;
                    setFlag(k,l,flag);
                    return false;
                }
            }
        }
        return true;
    }

//    private void printImage() throws IOException {
//        BufferedImage image = new BufferedImage(original.getWidth(),
//                original.getHeight(), original.getType());
//        for(int i=0;i<width;i++){
//            for(int j=0;j<height;j++){
//                if(mask[i][j] > -1){
//                    int newPixel = mask[i][j];
//                    image.setRGB(i, j, newPixel);
//                }else{
//                    int newPixel = mask[i][j];
//                    image.setRGB(i, j, newPixel);
//                }
//            }
//            System.out.println();
//        }
//
//        File ouptut = new File("result_1337.jpeg");
//        ImageIO.write(image, "jpeg", ouptut);
//    }

    private int colorToRGB(int alpha, int red, int green, int blue) {

        int newPixel = 0;
        newPixel += alpha;
        newPixel = newPixel << 8;
        newPixel += red; newPixel = newPixel << 8;
        newPixel += green; newPixel = newPixel << 8;
        newPixel += blue;
        return newPixel;
    }
    
    public int[][] getMask(){
    	return mask;
    }

}
