package by.bsuir.misoi.regions;

import by.bsuir.misoi.entity.Point;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Stack;

/**
 * Created by User-PC on 28.09.2016.
 */
public class Wildfire {

    public Wildfire(BufferedImage original){
        this.width = original.getWidth();
        this.height = original.getHeight();
        this.original = original;
        this.lab = new int[width][height];
    }
    int width,height;
    BufferedImage original;
    int[][] lab;

    public void findRegions(){
        for(int i=0;i<width;i++){
            for(int j=0;j<height;j++){
                lab[i][j] =0;
            }
        }
        Stack<Point> stack = new Stack<>();
        ArrayList<Point> RegionList = new ArrayList<>();
        int flag = 0;
        for(int i=1;i<width-1;i++){
            for(int j=1;j<height-1;j++) {
                Color color = new Color(original.getRGB(i,j));
                int pixel = color.getRed() + color.getBlue() + color.getGreen();
                if((lab[i][j] == 0)&&(pixel == 0)){
                    /*
                    Point point = new Point();
                    point.x = i;
                    point.y = j;
                    RegionList.add(point);
                    stack.push(point);*/
                    flag++;
                    lab[i][j] = flag;
                    setFlag(i,j,flag);
                    /*while(!stack.empty()){
                        Point p1 = stack.pop();
                        for(int k=p1.x-1;k<=p1.x+1;k++){
                            for(int l=p1.y-1;l<=p1.y+1;l++){
                                if((original.getRGB(k,l)==current) && (lab[k][l]==0)){
                                    lab[k][l] = RegionList.size();

                                    Point point2 = new Point();
                                    point.x = i;
                                    point.y = j;
                                    RegionList.add(point2);
                                    stack.push(point2);
                                }
                            }
                        }
                    }*/
                }
            }

        }
        System.out.print('l');
    }

    private void setFlag(int x,int y, int flag){
        for(int k = x-1;k<=x+1;k++){
            for(int l= y-1;l<=y+1;l++){
                Color color = new Color(original.getRGB(x,y));
                int oldPix = color.getRed() + color.getBlue() + color.getGreen();
                Color color1 = new Color(original.getRGB(k,l));
                int newPix = color1.getRed() + color1.getBlue() + color1.getGreen();
                if((oldPix == newPix) && ((x != k) || (y != l)) && (lab[k][l] == 0)){
                    lab[k][l] = flag;
                    setFlag(k,l,flag);
                }
            }
        }
    }


}
