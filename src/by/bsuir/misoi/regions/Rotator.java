package by.bsuir.misoi.regions;

import java.awt.image.BufferedImage;

/**
 * Created by NE User-PC on 12.10.2016.
 */
public class Rotator {
	
	private BufferedImage original = null;
	private int width = 0, height = 0;
	private int[][] mask = null;
	
    public Rotator(BufferedImage image, int[][] mask){
    	original = image;
    	width = original.getWidth();
    	height = original.getHeight();
    	this.mask = mask;
    }
    
    public BufferedImage baby_spin_me_right_n_round()
    {   
    	float angle = getRotateAngle();
    	BufferedImage output_img = new BufferedImage(width,
    			height, 5);
    	
        for(int i=1;i<width-1;i++){
            for(int j=1;j<height-1;j++){
            	int src_x = (int)(-Math.sin(angle)*j + Math.cos(angle)*i );
                int src_y  = (int)(Math.cos(angle) * j + Math.sin(angle)*i );
                int x0 = (int)(height/2 - Math.cos(angle) * height/2 + Math.sin(angle)*width/2);
                int y0 = (int)(width/2 - Math.cos(angle) * width/2 - Math.sin(angle)*height/2);
                src_x+=x0;
                src_y+=y0;
                if (( src_x>=1 )&&( src_y >= 1 )&&(src_x<=width-1)&&(src_y<=height-1))
                {
                	output_img.setRGB(i, j, mask[src_x][src_y]);
            	}
            	else
            	{
                	output_img.setRGB(i, j, -1);
            	}
            }
        }
    	return output_img;
    }
    
    /*
     * 0.523599f = 30*
     * 0.785398f = 45*
     * 1.0472f   = 60*
     * 1.5708f   = 90*
     * 2.0944f   = 120*
     * 6.28319f  = 360*
     */
    private float getRotateAngle(){
    	// calculating 4 corners
    	float angle = 0.785398f;
    	return angle;
    }
    
}
