package by.bsuir.misoi.regions;

import java.awt.Color;
import java.awt.image.BufferedImage;
import by.bsuir.misoi.entity.Point;

public class Rotator {
    public Rotator() {
    }

    public BufferedImage rotate_and_cut(BufferedImage original) {
        int w = original.getWidth();
        int h = original.getHeight();
        BufferedImage rotate_img = new BufferedImage(w,
                h, original.getType());

        double angle = getRotateAngle(original);
        for (int i = 0; i < w; i++) {
            for (int j = 0; j < h; j++) {
                double src_x = (Math.cos(angle) * i - Math.sin(angle) * j);
                double src_y = (Math.sin(angle) * i + Math.cos(angle) * j);
                double x0 = (h / 2 - Math.cos(angle) * h / 2 + Math.sin(angle) * w / 2);
                double y0 = (w / 2 - Math.cos(angle) * w / 2 - Math.sin(angle) * h / 2);
                src_x += x0;
                src_y += y0;
                if ((src_x >= 0) && (src_y >= 0) && ((int) src_x < w) && ((int) src_y < h)) {
                    rotate_img.setRGB(i, j, original.getRGB((int) src_x, (int) src_y));
                } else {
                    rotate_img.setRGB(i, j, Color.black.getRGB());
                }
            }
        }
        int xmin = 999999999, xmax = -1, ymin = 999999999, ymax = -1;
        for (int i = 0; i < w; i++) {
            for (int j = 0; j < h; j++) {
                if (rotate_img.getRGB(i, j) != Color.black.getRGB()) {
                    if (xmin > i) xmin = i;
                    if (xmax < i) xmax = i;
                    if (ymin > j) ymin = j;
                    if (ymax < j) ymax = j;
                }
            }
        }

        int newW = xmax - xmin + 1;
        int newH = ymax - ymin + 1;

        BufferedImage cut_img = new BufferedImage(newW, newH, original.getType());
        for (int i = xmin; i <= xmax; i++) {
            for (int j = ymin; j <= ymax; j++) {
                cut_img.setRGB(i - xmin, j - ymin, rotate_img.getRGB(i, j));
            }
        }
        return cut_img;
    }

    private double getRotateAngle(BufferedImage img) {
        int w = img.getWidth();
        int h = img.getHeight();
        Point p1, p2, p3, p4;
        p1 = new Point(999999, 0);
        p2 = new Point(0, 999999);
        p3 = new Point(0, -1);
        p4 = new Point(-1, 0);
        for (int i = 0; i < w; i++) {
            for (int j = 0; j < h; j++) {
                if (img.getRGB(i, j) != Color.black.getRGB()) {
                    if (i < p1.x) {
                        p1.x = i;
                        p1.y = j;
                    }
                    if (j < p2.y) {
                        p2.x = i;
                        p2.y = j;
                    }
                    if (j > p3.y) {
                        p3.x = i;
                        p3.y = j;
                    }
                    if (i > p4.x) {
                        p4.x = i;
                        p4.y = j;
                    }
                }
            }
        }
        double a;
        if (Math.sqrt((p3.x - p1.x) * (p3.x - p1.x) + (p3.y - p1.y) * (p3.y - p1.y)) >=
                Math.sqrt((p4.x - p3.x) * (p4.x - p3.x) + (p4.y - p3.y) * (p4.y - p3.y))) {
            a = Math.atan2((p3.y - p1.y), (p3.x - p1.x));
        } else {
            a = Math.atan2((p4.y - p3.y), (p4.x - p3.x));
        }
        return a;
    }

    public BufferedImage flipX(BufferedImage ori) {
        int w = ori.getWidth()-1;
        int h = ori.getHeight()-1;
        BufferedImage out = new BufferedImage(w,
                h, ori.getType());
        for (int i = 1; i <= w; i++) {
            for (int j = 1; j <= h; j++) {
                out.setRGB(w-i, h-j, ori.getRGB(i, j));
            }
        }
        return out;
    }
}