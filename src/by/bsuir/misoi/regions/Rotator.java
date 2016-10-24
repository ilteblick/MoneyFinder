package by.bsuir.misoi.regions;

import java.awt.Color;
import java.awt.image.BufferedImage;

import by.bsuir.misoi.entity.Point;

/**
 * Created by NE User-PC on 12.10.2016.
 */
public class Rotator {
	
	private BufferedImage original = null;
	private int width = 0, height = 0;
	private int[][] mask = null;
	private boolean[][] outArray = null;
	
    public Rotator(BufferedImage image, int[][] mask){
    	original = image;
    	width = original.getWidth();
    	height = original.getHeight();
    	this.mask = mask;
    }
    
    public BufferedImage baby_spin_me_right_n_round()
    {   
    	double angle = getRotateAngle();
    	BufferedImage output_img = new BufferedImage(width,
    			height, original.getType());
    	outArray = new boolean[width][height];
    	
        for(int i=1;i<width-1;i++){
            for(int j=1;j<height-1;j++){
            	outArray[i][j] = false;
            	int src_x = (int)(-Math.sin(angle)*j + Math.cos(angle)*i );
                int src_y  = (int)(Math.cos(angle) * j + Math.sin(angle)*i );
                int x0 = (int)(height/2 - Math.cos(angle) * height/2 + Math.sin(angle)*width/2);
                int y0 = (int)(width/2 - Math.cos(angle) * width/2 - Math.sin(angle)*height/2);
                src_x+=x0;
                src_y+=y0;
                if (( src_x>=1 )&&( src_y >= 1 )&&(src_x<=width-1)&&(src_y<=height-1))
                {
                	output_img.setRGB(i, j, mask[src_x][src_y]);
                	if ( mask[src_x][src_y] != -1 )
                		outArray[i][j] = true;
                	else
                		outArray[i][j] = false;
            	}
            	else
            	{
                	output_img.setRGB(i, j, -1);
                	outArray[i][j] = false;
            	}
            }
        }
        
        int xmin=999999999,xmax=-1,ymin=999999999,ymax=-1;
        for(int i=0;i<width;i++){
            for(int j=0;j<height;j++) {
                if(outArray[i][j]){
                    if(xmin > i) xmin = i;
                    if(xmax < i) xmax = i;
                    if(ymin > j) ymin = j;
                    if(ymax < j) ymax = j;
                }
            }
        }
        int newWidth = xmax-xmin+1;
        int newHeight = ymax-ymin+1;
        BufferedImage binarized = new BufferedImage(newWidth, newHeight, original.getType());
        for(int i=xmin;i<=xmax;i++){
            for (int j=ymin;j<=ymax;j++){
            	int alpha = new Color(output_img.getRGB(i, j)).getAlpha();
                Color color = new Color(output_img.getRGB(i, j));
                
                if (outArray[i][j]) {
                    int pixel = colorToRGB(alpha, color.getRed(), color.getGreen(), color.getBlue());
                    binarized.setRGB(i-xmin,j-ymin, pixel);
                } else {
                    binarized.setRGB(i-xmin, j-ymin, 0);
                }
            }
        }
    	return binarized;
    }
    
    /*
     * 0.523599f = 30*
     * 0.785398f = 45*
     * 1.0472f   = 60*
     * 1.5708f   = 90*
     * 2.0944f   = 120*
     * 6.28319f  = 360*
     */
    private double getRotateAngle(){
    	// calculating 4 corners
    	
    	// KAK ZE YA TEBYA NENAVIZY !!!
    	Point p1 = null, p2 = null, p3 = null, p4 = null;
    	p1 = new Point();
    	p2 = new Point();
    	p3 = new Point();
    	p4 = new Point();
    	p1.x = 99999;
    	p2.y = 99999;
    	p3.y = -99999;
    	p4.x = -99999;
  
        for(int i=0; i<width; i++){
            for(int j=0; j<height; j++){
            	if ( mask[i][j] != -1 ){
                    if ( i < p1.x ){ p1.x = i; p1.y = j; }
            		if ( j < p2.y ){ p2.x = i; p2.y = j; }
            		if ( j > p3.y ){ p3.x = i; p3.y = j; }
            		if ( i > p4.x ){ p4.x = i; p4.y = j; }
            	}
            }
        }
        
        if ( Math.sqrt((p3.x - p1.x)*(p3.x - p1.x) + (p3.y - p1.y)*(p3.y - p1.y)) > 
        		Math.sqrt((p4.x - p3.x)*(p4.x - p3.x) + (p4.y - p3.y)*(p4.y - p3.y)))
        {
        	return Math.atan2((p3.y - p1.y), (p3.x - p1.x));
        }
        else
        {
        	return Math.atan2(p4.y - p3.y , p4.x - p3.x);
        }
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
}
