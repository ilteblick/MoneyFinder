package by.bsuir.misoi.regions;

import by.bsuir.misoi.entity.Point;
import by.bsuir.misoi.entity.Quad;
import javafx.scene.shape.*;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.Polygon;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Stack;


public class Wildfire {

    public Wildfire(BufferedImage original,BufferedImage lol){
        this.width = original.getWidth();
        this.height = original.getHeight();
        this.original = original;
        this.lab = new int[width][height];
        this.mask = new int[width][height];
        this.lol = lol;
    }
    
    private int width,height;
    private BufferedImage original,lol;
    private int[][] lab;
    private int[][] mask = null;
    private int flag;
    //ArrayList<BufferedImage> images = new ArrayList<>();

    BufferedImage img_orig = null;

    public BufferedImage findRegions() throws IOException {
        for(int i=0;i<width;i++){
            for(int j=0;j<height;j++){
                lab[i][j] = 0;
                mask[i][j] = -1;
            }
        }
        //Stack<Point> stack = new Stack<>();
        //ArrayList<Point> RegionList = new ArrayList<>();
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
//        int kastil;
//        if(width >= height){
//            kastil = height;
//        }else{
//            kastil = width;
//        }
        
        return findSize(lab);
    }

    private BufferedImage findSize(int[][] lab) throws IOException {

        int xmin = 999999999, xmax = -1, ymin = 999999999, ymax = -1;
        int xmin_y = -1, xmax_y = -1, ymin_x = -1, ymax_x = -1;
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                if (lab[i][j] == 1) {
                    if (xmin > i) {
                        xmin = i;
                        xmin_y = j;
                    }
                    if (xmax < i) {
                        xmax = i;
                        xmax_y = j;
                    }
                    if (ymin > j) {
                        ymin = j;
                        ymin_x = i;
                    }
                    if (ymax < j) {
                        ymax = j;
                        ymax_x = i;
                    }
                }
            }
        }

        int newWidth = xmax - xmin + 1;
        int newHeight = ymax - ymin + 1;

        BufferedImage binarized = new BufferedImage(newWidth,
                newHeight, lol.getType());

        img_orig = new BufferedImage(newWidth + 50,
                newHeight + 50, lol.getType());

        for (int i = 0 ; i < 25; i++) {
            for (int j = 0; j < 25; j++) {
                img_orig.setRGB(i, j, Color.black.getRGB());
                img_orig.setRGB(newWidth + 49 - i, newHeight + 49 - j, Color.black.getRGB());
            }
        }

        Quad rect = new Quad(xmin, xmin_y, ymax_x, ymax, xmax, xmax_y, ymin_x, ymin);
        Polygon poly = new Polygon();
        poly.addPoint(xmin, xmin_y);
        poly.addPoint(ymax_x, ymax);
        poly.addPoint(xmax, xmax_y);
        poly.addPoint(ymin_x, ymin);
        poly.addPoint(xmin, xmin_y);

        for (int i = xmin ; i <= xmax; i++) {
            for (int j = ymin  ; j <= ymax; j++) {
                if (lab[i][j] == 1) {
                    binarized.setRGB(i - xmin, j - ymin, -1);
                    mask[i][j] = lol.getRGB(i, j);
                    img_orig.setRGB(i - xmin + 25, j - ymin + 25, lol.getRGB(i, j));
                } else {
                    binarized.setRGB(i - xmin, j - ymin, 0);
                    img_orig.setRGB(i - xmin + 25, j - ymin + 25, Color.black.getRGB());
                }

                if ( rect.contains(new Point(i, j)) || rect.isContainsPoint(i, j) ||
                        rect.isContainsPoint(new Point(i,j)) /*|| poly.contains(i, j)*/){
                    img_orig.setRGB(i - xmin + 25, j - ymin + 25, lol.getRGB(i, j));
                }else{
                    //img_orig.setRGB(i - xmin + 25, j - ymin + 25, Color.black.getRGB());
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

    public BufferedImage getOrig() throws IOException {
        File ouptut = new File("img_wild.png");
        ImageIO.write(img_orig, "png", ouptut);
        return img_orig;
    }
}
