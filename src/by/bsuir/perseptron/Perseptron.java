package by.bsuir.perseptron;

import com.sun.javafx.collections.SortHelper;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by Denis on 20.12.2016.
 */
public class Perseptron {

    List<String> files = new ArrayList<>();
    List<Double> wes = new ArrayList<>();

    public Perseptron(BufferedImage img, boolean isFront) throws IOException {

        String file_path;
        if ( isFront ) {
            file_path = "res/perseptron/front/";
            files.add("5.png");
            files.add("10.png");
            files.add("20.png");
            files.add("50.png");
        }else{
            file_path = "res/perseptron/back/";
            files.add("5.png");
            files.add("10.png");
            files.add("20.png");
            files.add("50.png");
        }
            BufferedImage sha;
            for (String name: files){
                File fil = new File(file_path + name);
                sha = ImageIO.read(fil);
                Double koeff = 0.0;
                for (int i = 0 ; i < sha.getWidth(); i++) {
                    for (int j = 0; j < sha.getHeight(); j++) {
                        Color color = new Color(img.getRGB(i, j));
                        int rgb = (color.getRed() + color.getGreen() + color.getBlue()) / 3;

                        if (rgb != 0){
                            Color color_1 = new Color(sha.getRGB(i, j));
                            int rgb_1 = (color_1.getRed() + color_1.getGreen() + color_1.getBlue()) / 3;
                            if ( rgb_1 != 0 ){
                                koeff += rgb_1/100;
                            }
                        }
                    }
                }
                wes.add(koeff);
            }

            //Collections.sort(wes);
            int index = 0;
            Double max = wes.get(0);
            for(Double d: wes){
                if(max <= d){
                    max = d;
                    index = wes.indexOf(d);
                }
            }
            System.out.println("Po Perseptrony: " + files.get(index));
    }
}
