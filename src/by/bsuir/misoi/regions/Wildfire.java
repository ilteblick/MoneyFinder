package by.bsuir.misoi.regions;

import by.bsuir.misoi.entity.Point;
import by.bsuir.misoi.filter.Binarization;

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
    int flag;
    ArrayList<BufferedImage> images = new ArrayList<>();





    public BufferedImage findRegions() throws IOException {
        for(int i=0;i<width;i++){
            for(int j=0;j<height;j++){
                lab[i][j] = 0;
                mask[i][j] = -1;
            }
        }
        Stack<Point> stack = new Stack<>();
        ArrayList<Point> RegionList = new ArrayList<>();
        flag = 1;
        for(int i=1;i<width-1;i++){
            for(int j=1;j<height-1;j++) {
                Color color = new Color(original.getRGB(i,j));
                int pixel = color.getRed() + color.getBlue() + color.getGreen();
                if((lab[i][j] == 0)&&(pixel <10)) {
                    //flag++;
                    lab[i][j] = flag;
                    if(setFlag(i, j, flag)) {
                        flag++;
                    }
                }
            }
        }
        int kastil;
        if(width >= height){
            kastil = height;
        }else{
            kastil = width;
        }

       for(int i=1;i<kastil-1;i++){
           for(int j=1;j<kastil-1;j++){
                System.out.print(String.valueOf(lab[j][i])+ " ");
            }
            System.out.println();
        }
        
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
        for(int k = x-1;k<=x+1;k+=1){
            for(int l= y-1;l<=y+1;l+=1){
                if((k < 0)||(l<0) || (k>width-1) || (l>height-1)){
                    break;
                }
                Color color = new Color(original.getRGB(x,y));
                int oldPix = (color.getRed() + color.getBlue() + color.getGreen())/3;
                Color color1 = new Color(original.getRGB(k,l));
                int newPix = (color1.getRed() + color1.getBlue() + color1.getGreen())/3;
                if(((oldPix >= newPix-5) && (oldPix <= newPix+5 )) && !((x == k) && (y == l)) && (lab[k][l] == 0 )){
                    lab[k][l] = flag;
                    if(setFlag(k,l,flag)){
                        //continue;
                    }else{
                    //return true;
                    }
                }else{
                    if(lab[k][l] == flag){
                      //return false;
                    }
                    //return true;
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


    public ArrayList<BufferedImage> findMain() throws IOException {
        int mas [] = new int[flag];
        for(int i=0;i<width;i++){
            for(int j=0;j<height;j++){
                mas[lab[i][j]]++;
            }
        }

        for(int z=1;z<flag;z++){
            int xmin=999999999,xmax=-1,ymin=999999999,ymax=-1;
            for(int i=0;i<width;i++){
                for(int j=0;j<height;j++){
                    if(lab[i][j] == z){
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
            BufferedImage img = new BufferedImage(newWidth, newHeight, lol.getType());

            for(int i=xmin;i<=xmax;i++){
                for (int j=ymin;j<=ymax;j++){
                    if (lab[i][j] == z) {
                        //newPixel = colorToRGB(alpha,pixelColor.getRed(),pixelColor.getGreen(),pixelColor.getBlue());
                        img.setRGB(i-xmin,j-ymin,0);

                    } else {
                        //newPixel = colorToRGB(alpha, 255, 255, 255);
                        img.setRGB(i-xmin, j-ymin,-1);
                    }
                }
            }
            images.add(img);
            File ouptut = new File(Integer.toString(z) + ".png");
            ImageIO.write(img, "png", ouptut);
        }
        return images;
    }


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
