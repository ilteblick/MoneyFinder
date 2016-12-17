package by.bsuir.perseptron;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.IOException;

import by.bsuir.misoi.filter.GrayScale;
import by.bsuir.misoi.image.Iimage;
import by.bsuir.misoi.image.ImageIml;

public class Checker {

	private BufferedImage img;
	boolean isOld = true;
	
	public Checker(BufferedImage original) throws IOException {
		// TODO Auto-generated constructor stub
		img = original;
		//checkNew();
		//checkSize();
		checkColor();
	}

	public int nominal()
	{
		return 0;
	}
	
	private void checkNew() throws IOException
	{
		double cc = 0;
		for ( int i = 0; i<img.getWidth() / 25; i++)
		{
			for ( int j = 0; j<img.getHeight(); j++)
			{
				int alpha_l = new Color(img.getRGB(i, j)).getAlpha();
                Color color_l = new Color(img.getRGB(i, j));
                int pix_l = colorToRGB(alpha_l, color_l.getRed(), color_l.getGreen(), color_l.getBlue());
                //System.out.print(" 777!! " + color_l.getGreen() + " ");
                int l = (color_l.getRed() + color_l.getGreen() + color_l.getBlue()) / 3;
                
                int alpha_r = new Color(img.getRGB(img.getWidth() - i - 1, j)).getAlpha();
                Color color_r = new Color(img.getRGB(img.getWidth() - i - 1, j));
                int pix_r = colorToRGB(alpha_r, color_r.getRed(), color_r.getGreen(), color_r.getBlue());
                int r = (color_r.getRed() + color_r.getGreen() + color_r.getBlue()) / 3;
                
				if ( l <= 220 && r <= 220 )
				{
					cc = cc - 1.0f;
				}
				else
				{
					cc = cc + 1.0f;
				}
			}
		}
		
		if ( cc < 0 )
		{
			System.out.print("\nNOVAYA!\n");
			isOld = false;
		}
		else
		{
			System.out.print("\nSTARAYA\n");
			isOld = true;
		}
	}
	
	private void checkSize()
	{
		if ( isOld ) return;
		double k = (double)img.getWidth() / (double)img.getHeight();
		
		int n = 0;
		if ( k > 1.2f && k < 1.86f )
		{
			n = 5;
		}
		else if ( k >= 1.86f && k < 1.96f )
		{
			n = 10;
		}
		else if ( k >= 1.96f && k < 2.0001f )
		{
			n = 20;
		}
		else if ( k>=2.0001f && k<2.033f )
		{
			n = -777;
			System.out.print("STARAYA!!! ");
		}
		else if ( k>=2.033f && k<2.06f )
		//else if ( k>=2.01f && k<2.06f )
		{
			n = 50;
		}
		else if ( k>=2.06f && k<2.99f )
		{
			n = 100;
		}
		else if ( k>=2.99f && k < 2.17f )
		{
			n = 200;
		}
		else if ( k>=2.17f && k < 2.3f )
		{
			n = 500;
		}
		System.out.print("ETO: " + n + " ryblei \n");
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
	
	private void checkColor() throws IOException
	{
		int temp = 0;
		for ( int i = 0; i<img.getWidth(); i++)
		{
			for ( int j = 0; j<img.getHeight(); j++)
			{
                Color color = new Color(img.getRGB(i, j));
                int rgb = (color.getRed() + color.getGreen() + color.getBlue()) / 3;
                if (rgb > 30 && rgb < 225)
                {
                	
                	
                	
                }
			}
		}
	}
	
}

