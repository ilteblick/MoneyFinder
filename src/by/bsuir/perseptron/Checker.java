package by.bsuir.perseptron;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.IOException;

import by.bsuir.misoi.regions.Rotator;
import com.sun.javafx.collections.SortHelper;

public class Checker {

	private BufferedImage img;
	private boolean isFront = true;
	private boolean isNew  = true;
	
	public Checker(BufferedImage original) throws IOException {
		img = original;
        colored_regions();
	}

	private void colored_regions() throws IOException {
		int[] white_arr = new int[8];
		int w = img.getWidth();
		int h = img.getHeight();
		int tmp = 0;
		for ( int i = 0; i<w; i++) {
			for (int j = 0; j <h ; j++) {
                Color color = new Color(img.getRGB(i, j));
                int rgb = (color.getRed() + color.getGreen() + color.getBlue()) / 3;
				if ( rgb > 165 ){
				    tmp = 1;
                }else{
				    if ( rgb > 15 )
				        tmp = -1;
				    else
				        tmp = 0;
                }

                    if ( i >= w/4.5 && i <= w/2.8 ){
                        if ( j <= h/2.2 ){
                            // then block I
                            white_arr[0]+=tmp;
                        }else{
                            // block II
							white_arr[1]+=tmp;
                        }
                    }else{
                        if ( i>w/1.56 && i<w/1.34 ){
                            if ( j < h/1.87 ){
                                // then block III
								white_arr[2]+=tmp;
                            }else{
                                // block IV
								white_arr[3]+=tmp;
                            }
                        } else {
                            if ( i < w/30 ){
                                // block V
                                white_arr[4]+=tmp;
							}else{
                                if ( i>w/1.035 ){
                                    // block VI
                                    white_arr[5]+=tmp;
								}else{
                                    if ( i < w/5 ){
                                        // block VII
                                        white_arr[6]+=tmp;
									}else{
                                        if ( i > w/1.26 ){
                                            // block VIII
                                            white_arr[7]+=tmp;
										}
                                    }
                                }
                            }
                        }
                    }
			}
		}

		SortHelper helper = new SortHelper();
        int[] temp;
        temp = white_arr.clone();
        helper.sort(temp, 0, 8);
        int max = temp[7];

        System.out.println("Это: ");
        if ( max == white_arr[0] || max == white_arr[1] || max == white_arr[2] ||max == white_arr[3] )
        {
            isNew = true;
            System.out.println("1. Новая Купюра");
            if ( max == white_arr[1] )
            {
                if ( white_arr[0] < 0) {
                    isFront = false;
                    System.out.println("2. Тыльная Сторона");
                    System.out.println("3. Вверх Ногами -> переворачиваем..");
                    Rotator rotator = new Rotator();
                    img = rotator.flipX(img);
                    System.out.println("   перевернута.");
                }else{
                    isFront = true;
                    System.out.println("2. Лицевая Сторона");
                    System.out.println("3. Вниз Ногами");
                }
            }
            else
            {
                if ( max == white_arr[2] ) {
                    if (white_arr[3] < 0) {
                        isFront = false;
                        System.out.println("2. Тыльная Сторона");
                        System.out.println("3. Вниз Ногами");
                    } else {
                        isFront = true;
                        System.out.println("2. Лицевая Сторона");
                        System.out.println("3. Вверх Ногами -> переворачиваем..");
                        Rotator rotator = new Rotator();
                        img = rotator.flipX(img);
                        System.out.println("   перевернута.");
                    }
                }
            }
        }else {
            isNew = false;
            System.out.println("1. Старая Купюра");
        }
        return;
	}

	public BufferedImage getImg(){
	    return img;
    }

    public boolean isFront(){
	    return isFront;
    }

    public boolean isNew(){
        return isNew;
    }

}

