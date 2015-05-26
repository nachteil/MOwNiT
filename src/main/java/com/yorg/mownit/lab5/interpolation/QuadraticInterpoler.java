package com.yorg.mownit.lab5.interpolation;

import com.yorg.mownit.commons.Function;
import com.yorg.mownit.commons.Point2D;

public class QuadraticInterpoler {

    public Function natural(Point2D [] points) {

        int N = points.length;

        double [] z = new double [points.length-1];
        z[0] = 0;

        for(int i = 1; i < points.length-2; ++i) {
            z[i] = -z[i-1] + 2*((points[i].getY()-points[i-1].getY())/(points[i].getX()-points[i-1].getX()));
        }

        Function [] splines = new Function[points.length-1];
        for(int i = 0; i < N-1; ++i) {
            final int finalI = i;
            double xi = points[finalI].getX();
            splines[i] = x -> {
                double dif = x -
            }

        }

    }

    public int determineInterval(Point2D [] points, double x) {

        if(x < points[0].getX()) {
            return 0;
        }
        if(x > points[points.length-1].getX()) {
            return points.length-1;
        }

        int i = 0;
        while(!(x >= points[i].getX() && x <= points[i+1].getX())) ++i;

        if(cnt.get(i) == null) cnt.put(i, 0);
        cnt.put(i, cnt.get(i)+1);

        return i;
    }

}
