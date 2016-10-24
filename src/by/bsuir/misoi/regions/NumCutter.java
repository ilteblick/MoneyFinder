package by.bsuir.misoi.regions;

import java.awt.image.BufferedImage;

public class NumCutter {
	private BufferedImage resault_image = null;
	
	public NumCutter(BufferedImage image) {
		
		int width_orig = image.getWidth(), height_orig = image.getHeight();
		int width_res = (int)(width_orig / 3), height_res = (int)(height_orig / 1);
		int poz_x = width_orig - width_res, poz_y = height_orig - height_res;
		
		resault_image = new BufferedImage(width_res -10,
                height_res-20, image.getType());
		
        for(int i=0; i<width_res-10; i++){
            for(int j=0; j<height_res-20; j++){
            	resault_image.setRGB(i, j, image.getRGB(poz_x + i, j+10));
            }
        }
	}
	
	public BufferedImage getImage()
	{
		return resault_image;
	}
}
