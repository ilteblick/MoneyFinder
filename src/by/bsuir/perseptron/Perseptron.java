package by.bsuir.perseptron;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.plaf.basic.BasicInternalFrameTitlePane.MaximizeAction;

public class Perseptron {
	private ArrayList<BufferedImage> images;
	BufferedImage shablon[];
	
	public Perseptron(ArrayList<BufferedImage> images) throws IOException {
		this.images = images;
		shablon = new BufferedImage[4];
		loadShablon();
	}

	public void doPerseptron() throws IOException
	{
		for ( int index=0; index<images.size(); index++)
		{
			BufferedImage img = images.get(index);
			img = scale_resize(img);
			schitatb(img);
		}
	}
	
	// return 60x60
	private BufferedImage scale_resize(BufferedImage inp)
	{
		int w2 = 60, h2 = 60;
        BufferedImage size_img = new BufferedImage(w2, h2, inp.getType());
        int w1 = inp.getWidth(), h1 = inp.getHeight();
        
        // resize (sqare)
        int tmp_size = 0;
        int w_k = (int)((w2 - w1) / 2), 
        	h_k = (int)((h2 - h1) / 2);
        if ( w1 > h1 )
        	tmp_size = w1;
        else
        	tmp_size = h1;
        for ( int i=0; i<tmp_size; i++ ) //width
        {
        	for ( int j=0; j<tmp_size; j++ ) //height
        	{
        		if ( i>w_k && i<w_k+w1 && j>h_k && j<h_k+h1 )
        		{
        			size_img.setRGB(i, j, inp.getRGB(i-w_k, j-h_k));
        		}
        		else
        		{
        			size_img.setRGB(i, j, -1);
        		}
        	}
        }
        
        //double scale = h2 / tmp_size;
        //for ( int i=0; i<tmp_size; i++ )
        //{
        //	for ( int j=0; j<tmp_size; j++ )
        //	{
        //		
        //	}
        //}
		return size_img;
	}
	
	// persenge of sovpadenie
	private void schitatb(BufferedImage img)
	{
		System.out.println("");
		for ( int counter=1; counter<=4; counter++ )
		{
			BufferedImage sh = shablon[counter-1];
			double ball = 0;
			
			for ( int i = 0; i < img.getHeight()-1; i++ )
			{
				for ( int j = 0; j < img.getWidth()-1; j++ )
				{
					int shab = sh.getRGB(i, j);
					int pix = img.getRGB(i, j);
					if ( (Math.abs(shab-pix) < 10) && (shab<1))
					{
						ball+=0.04;
					}else
					{
						ball-=0.01;
					}
					
				}
			}
			System.out.println("ETO " + counter + " na " + (int)(ball) + "Ballov");
		}
	}
	
	private void loadShablon() throws IOException
	{
		for ( int i = 1; i<=4; i++ )
		{
	        File file = new File("shablon_" + i + ".png");
	        shablon[i-1] = ImageIO.read(file);
		}
	}
}
