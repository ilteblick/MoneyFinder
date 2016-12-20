package by.bsuir.perseptron;

import by.bsuir.misoi.filter.FilterFactory;
import by.bsuir.misoi.filter.IFilter;

import java.awt.*;
import java.awt.color.ColorSpace;
import java.awt.image.*;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import javax.imageio.ImageIO;

public class PRE_Perseptron {
//
//	private ArrayList<BufferedImage> images;
//	BufferedImage shablon[];
//
//	public PRE_Perseptron(ArrayList<BufferedImage> images) throws IOException {
//		this.images = images;
//		shablon = new BufferedImage[4];
//		loadShablon();
//	}
//
//	public void doPerseptron() throws IOException
//	{
//		for ( int index=0; index<images.size(); index++)
//		{
//			BufferedImage img = images.get(index);
//			img = scale_resize(img);
//			schitatb(img);
//		}
//	}
//
//	// return 60x60
//	private BufferedImage scale_resize(BufferedImage inp)
//	{
//		int w2 = 60, h2 = 60;
//        BufferedImage size_img = new BufferedImage(w2, h2, inp.getType());
//        int w1 = inp.getWidth(), h1 = inp.getHeight();
//
//        // resize (sqare)
//        int tmp_size = 0;
//        int w_k = (int)((w2 - w1) / 2),
//        	h_k = (int)((h2 - h1) / 2);
//        if ( w1 > h1 )
//        	tmp_size = w1;
//        else
//        	tmp_size = h1;
//        for ( int i=0; i<tmp_size; i++ ) //width
//        {
//        	for ( int j=0; j<tmp_size; j++ ) //height
//        	{
//        		if ( i>w_k && i<w_k+w1 && j>h_k && j<h_k+h1 )
//        		{
//        			size_img.setRGB(i, j, inp.getRGB(i-w_k, j-h_k));
//        		}
//        		else
//        		{
//        			size_img.setRGB(i, j, -1);
//        		}
//        	}
//        }
//
//        //double scale = h2 / tmp_size;
//        //for ( int i=0; i<tmp_size; i++ )
//        //{
//        //	for ( int j=0; j<tmp_size; j++ )
//        //	{
//        //
//        //	}
//        //}
//		return size_img;
//	}
//
//	// persenge of sovpadenie
//	private void schitatb(BufferedImage img)
//	{
//		System.out.println("");
//		for ( int counter=1; counter<=4; counter++ )
//		{
//			BufferedImage sh = shablon[counter-1];
//			double ball = 0;
//
//			for ( int i = 0; i < img.getHeight()-1; i++ )
//			{
//				for ( int j = 0; j < img.getWidth()-1; j++ )
//				{
//					int shab = sh.getRGB(i, j);
//					int pix = img.getRGB(i, j);
//					if ( (Math.abs(shab-pix) < 10) && (shab<1))
//					{
//						ball+=0.04;
//					}else
//					{
//						ball-=0.01;
//					}
//
//				}
//			}
//			System.out.println("ETO " + counter + " na " + (int)(ball) + "Ballov");
//		}
//	}
//
//	private void loadShablon() throws IOException
//	{
//		for ( int i = 1; i<=4; i++ )
//		{
//	        File file = new File("shablon_" + i + ".png");
//	        shablon[i-1] = ImageIO.read(file);
//		}
//	}

    private BufferedImage image;
    private BufferedImage filtered;
    private boolean isNew;
    private  boolean isFront;

    public PRE_Perseptron(BufferedImage original, boolean isNew, boolean isFront) throws IOException {

        if (!isNew) return;

        FilterFactory factory = new FilterFactory();
        IFilter binarization = factory.createFilter(1);
        IFilter median = factory.createFilter(2);
        IFilter gray = factory.createFilter(3);
        image = original;
        this.isNew = isNew;
        this.isFront = isFront;
        filtered = binarization.doFilter(median.doFilter(median.doFilter(gray.doFilter(original))));

        File ouptut_p = new File("PRE_FINAL.png");
        ImageIO.write(filtered, "png", ouptut_p);
        filtered = cut();
        File ouptut = new File("FINAL.png");
        ImageIO.write(filtered, "png", ouptut);
    }

    private BufferedImage scale(BufferedImage img) throws IOException {

//        double scale_x = 1;
//        double scale_y = 1;
//
//        if ( isFront == false ) {
//            scale_x = 60.0 / img.getWidth();
//            scale_y = 45.0 / img.getHeight();
//        } else {
//            scale_x = 60.0 / img.getWidth();
//            scale_y = 45.0 / img.getHeight();
//        }
//
//        BufferedImage bi = new BufferedImage((int)(scale_x * img.getWidth(null)),
//                (int)(scale_y * img.getHeight(null)), img.getType());
//
//        Graphics2D grph = (Graphics2D) bi.getGraphics();
//        grph.scale(scale_x, scale_y);
//        grph.drawImage(img, 0, 0, null);
//        grph.dispose();
//
        return img;
    }

    private BufferedImage cut() throws IOException {

        int n_w = (int)(filtered.getWidth()*0.158);
        int n_h = (int)(filtered.getHeight()*0.258);

//          int n_w = (int)(filtered.getWidth() / 2);
//          int n_h = (int)(filtered.getHeight() / 2);

        BufferedImage out = new BufferedImage(n_w, n_h, filtered.getType());

        for (int i = 0; i < n_w; i++){
            for (int j = 0; j < n_h; j++){
                if ( isFront )
                    out.setRGB(i, j, filtered.getRGB(i, j));
                else
                    out.setRGB(i, n_h -1-j, filtered.getRGB(i, filtered.getHeight() - j - 1));
            }
        }
        return scale(out);
    }
}
