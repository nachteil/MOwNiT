package com.yorg.mownit.lab5.interpolation;

import com.yorg.mownit.commons.Function;
import com.yorg.mownit.commons.Point2D;

public class QuadraticInterpoler {

    public Function natural(Point2D [] points) {

        int N = points.length-1;

        double [] z = new double [points.length];
        z[0] = 0;

        for(int i = 1; i < points.length; ++i) {
            z[i] = -z[i-1] + 2*((points[i].getY()-points[i-1].getY())/(points[i].getX()-points[i-1].getX()));
        }

        Function [] splines = new Function[points.length-1];
        for(int i = 0; i < N; ++i) {
            final int finalI = i;
            double xi = points[finalI].getX();
            splines[i] = x -> {
                double dif = x - xi;
                return (z[finalI+1]-z[finalI]) / (2.0*(points[finalI+1].getX()-(points[finalI].getX()))) * Math.pow(dif, 2)
                        + z[finalI] * dif + points[finalI].getY();
            };
        }
        return x -> splines[determineInterval(points, x)].getValue(x);
    }

    public Function clamped(Point2D [] points, double z0) {

        int N = points.length-1;

        double [] z = new double [points.length];
        z[0] = z0;

        for(int i = 1; i < points.length; ++i) {
            z[i] = -z[i-1] + 2*((points[i].getY()-points[i-1].getY())/(points[i].getX()-points[i-1].getX()));
        }

        Function [] splines = new Function[points.length-1];
        for(int i = 0; i < N; ++i) {
            final int finalI = i;
            double xi = points[finalI].getX();
            splines[i] = x -> {
                double dif = x - xi;
                return (z[finalI+1]-z[finalI]) / (2.0*(points[finalI+1].getX()-(points[finalI].getX()))) * Math.pow(dif, 2)
                        + z[finalI] * dif + points[finalI].getY();
            };
        }
        return x -> splines[determineInterval(points, x)].getValue(x);
    }

    public int determineInterval(Point2D [] points, double x) {

        if(x < points[0].getX()) {
            return 0;
        }
        if(x > points[points.length-1].getX()) {
            return points.length-2;
        }

        int i = 0;
        while(!(x >= points[i].getX() && x <= points[i+1].getX())) ++i;

        return i;
    }

}
