package by.bsuir.misoi.regions;

import java.awt.image.BufferedImage;

/**
 * Created by User-PC on 28.09.2016.
 */
public class Rotator {
	
	private BufferedImage original = null;
	private int width = 0, height = 0;
	
    public Rotator(BufferedImage image){
    	original = image;
    	width = original.getWidth();
    	height = original.getHeight();
    }
    
    public BufferedImage baby_spin_me_right_n_round(float angle)
    {    	
    	int size = Math.max(width, height);
    	BufferedImage output_img = new BufferedImage(size,
    			size, original.getType());
    	
        for(int i=1;i<size-1;i++){
            for(int j=1;j<size-1;j++){
                int src_x = (int)(Math.cos(angle) * j + Math.sin(angle)*i ) ;
                int src_y = (int)(-Math.sin(angle)*j + Math.cos(angle)*i ) ;
                int x0 = (int)(width/2 - Math.cos(angle) * width/2 - Math.sin(angle)*height/2);
                int y0 = (int)(height/2 - Math.cos(angle) * height/2 + Math.sin(angle)*width/2);
                src_x+=x0;
                src_y+=y0;
                if (( src_x>=1 )&&( src_y >= 1 )&&(src_x<=width-1)&&(src_y<=height-1))
                {
                	int dot = original.getRGB(src_x, src_y);
                	output_img.setRGB(j, i, dot);
            	}
            	else
            	{
                	output_img.setRGB(j, i, -1);
            	}
            }
        }
    	return output_img;
    }
}
