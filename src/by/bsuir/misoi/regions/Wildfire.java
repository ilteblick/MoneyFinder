package by.bsuir.misoi.regions;

import by.bsuir.misoi.entity.Point;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Stack;

/**
 * Created by User-PC on 28.09.2016.
 */
public class Wildfire {
    public void findRegions(BufferedImage original){
        int width = original.getWidth();
        int height = original.getHeight();
        int[][] lab = new int[width][height];
        for(int i=0;i<width;i++){
            for(int j=0;j<height;j++){
                lab[i][j] =0;
            }
        }
        Stack<Point> stack = new Stack<>();
        ArrayList<Point> RegionList = new ArrayList<>();
        for(int i=1;i<width-1;i++){
            for(int j=1;j<height-1;j++) {
                if(lab[i][j] == 0){
                    int current = original.getRGB(i,j);
                    Point point = new Point();
                    point.x = i;
                    point.y = j;
                    RegionList.add(point);
                    stack.push(point);
                    while(!stack.empty()){
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
                    }
                }
            }

        }
        System.out.print('l');
    }


}
