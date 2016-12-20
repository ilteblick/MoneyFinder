package by.bsuir.misoi.entity;

import java.util.Vector;

/**
 * Created by Denis on 15.12.2016.
 */
public class Quad {

    private Vector<Point> corners = null;

    public Quad(){}

    public Quad(Point dot1, Point dot2, Point dot3, Point dot4){
        corners = new Vector<Point>(4);
        corners.add(dot1);
        corners.add(dot2);
        corners.add(dot3);
        corners.add(dot4);
    }

    public Quad(int p1_x, int p1_y, int p2_x, int p2_y, int p3_x, int p3_y, int p4_x, int p4_y){
        corners = new Vector<Point>(4);
        corners.add(new Point(p1_x, p1_y));
        corners.add(new Point(p2_x, p2_y));
        corners.add(new Point(p3_x, p3_y));
        corners.add(new Point(p4_x, p4_y));
    }

    public boolean isContainsPoint(Point dot){
        if ( corners == null )
            return false;

        boolean result = false;
        for (int i = 0, j = corners.size() - 1; i < corners.size(); j = i++) {
            if ((corners.elementAt(i).y > dot.y) != (corners.elementAt(j).y > dot.y) &&
                    (dot.x < (corners.elementAt(j).x -
                            corners.elementAt(i).x) * (dot.y - corners.elementAt(i).y) /
                            (corners.elementAt(j).y-corners.elementAt(i).y) + corners.elementAt(i).x)) {
                result = !result;
            }
        }
        return result;
    }

    public boolean isContainsPoint(int x, int y){
        if ( corners == null )
            return false;

        boolean result = false;
        for (int i = 0, j = corners.size() - 1; i < corners.size(); j = i++) {
            if ((corners.elementAt(i).y > y) != (corners.elementAt(j).y > y) &&
                    (x < (corners.elementAt(j).x -
                            corners.elementAt(i).x) * (y - corners.elementAt(i).y) /
                            (corners.elementAt(j).y-corners.elementAt(i).y) + corners.elementAt(i).x)) {
                result = !result;
            }
        }
        return result;
    }

    public boolean contains(Point point)
    {
        if ( corners == null )
            return false;

        int a = (corners.elementAt(0).x - point.x) * (corners.elementAt(1).y -
                corners.elementAt(0).y) - (corners.elementAt(1).x -
                corners.elementAt(0).x) * (corners.elementAt(0).y - point.y);
        int b = (corners.elementAt(1).x - point.x) * (corners.elementAt(2).y -
                corners.elementAt(1).y) - (corners.elementAt(2).x -
                corners.elementAt(1).x) * (corners.elementAt(1).y - point.y);
        int c = (corners.elementAt(2).x - point.x) * (corners.elementAt(0).y -
                corners.elementAt(2).y) - (corners.elementAt(0).x -
                corners.elementAt(2).x) * (corners.elementAt(2).y - point.y);

        if ((a >= 0 && b >= 0 && c >= 0) || (a <= 0 && b <= 0 && c <= 0))
            return true;

        int d = (corners.elementAt(0).x - point.x) * (corners.elementAt(3).y -
                corners.elementAt(0).y) - (corners.elementAt(3).x -
                corners.elementAt(0).x) * (corners.elementAt(0).y - point.y);
        int e = (corners.elementAt(3).x - point.x) * (corners.elementAt(2).y -
                corners.elementAt(3).y) - (corners.elementAt(2).x -
                corners.elementAt(3).x) * (corners.elementAt(3).y - point.y);
        int f = (corners.elementAt(2).x - point.x) * (corners.elementAt(0).y -
                corners.elementAt(2).y) - (corners.elementAt(0).x -
                corners.elementAt(2).x) * (corners.elementAt(2).y - point.y);

        if ((d >= 0 && e >= 0 && f >= 0) || (d <= 0 && e <= 0 && f <= 0))
            return true;

        return false;
    }

}
