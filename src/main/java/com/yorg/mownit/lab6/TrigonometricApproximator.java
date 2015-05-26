package com.yorg.mownit.lab6;

import com.yorg.mownit.commons.Function;
import com.yorg.mownit.commons.Point2D;

import java.util.List;

public class TrigonometricApproximator {

    private final Point2D [] points;
    private final int apprpximationOrder;

    public TrigonometricApproximator(Point2D [] points, int approximationOrder) {
        this.points = points;
        this.apprpximationOrder = approximationOrder;
    }

    public Function getApproximateFunction() {

        double a0 = getA0();
        double n = points.length;

        return x -> {
            double sum = 0.0;
            for(int i = 1; i <= apprpximationOrder; ++i) {
                sum += getA(i) * Math.cos(x * 2.0 * Math.PI * i / n);
                sum += getB(i) * Math.cos(x * 2.0 * Math.PI * i / n);
                System.out.println("a" + i + ": " + getA(i));
            }
            return a0 + sum;
        };
    }

    private double getA0() {
        double a0 = 0.0;
        for(Point2D p : points) {
            a0 += p.getY();
        }
        a0 /= points.length;

        return a0;
    }

    private double getA(int i) {
        double sum = 0.0;
        for(int j = 0; j < points.length; ++j) {
            sum += points[j].getY() * Math.cos(2.0*Math.PI * (j+1)*(i+1)/points.length);
        }
        return 2.0 * sum / points.length;
    }

    private double getB(int i) {
        double sum = 0.0;
        for(int j = 0; j < points.length; ++j) {
            sum += points[j].getY() * Math.sin(2.0 * Math.PI * (j + 1) * (i + 1) / points.length);
        }
        return 2.0 * sum / points.length;
    }

}
