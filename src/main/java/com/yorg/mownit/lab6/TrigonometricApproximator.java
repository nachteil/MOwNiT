package com.yorg.mownit.lab6;

import com.yorg.mownit.commons.Function;
import com.yorg.mownit.commons.Point2D;

import java.util.List;

public class TrigonometricApproximator {

    private final List<Point2D> points;
    private final int apprpximationOrder;

    public TrigonometricApproximator(List<Point2D> point2DList, int approximationOrder) {
        this.points = point2DList;
        this.apprpximationOrder = approximationOrder;
    }

    public Function getApproximateFunction() {

        double a0 = getA0();
        double n = points.size();

        return x -> {
            double sum = 0.0;
            for(int i = 1; i <= apprpximationOrder; ++i) {
                sum += getA(i) * Math.cos(2.0 * Math.PI * i / n) * x;
                sum += getB(i) * Math.cos(2.0 * Math.PI * i / n) * x;
            }
            return a0 + sum;
        };
    }

    private double getA0() {
        double a0 = 0.0;
        for(Point2D p : points) {
            a0 += p.getY();
        }
        a0 /= points.size();

        return a0;
    }

    private double getA(int i) {
        double sum = 0.0;
        return 0;
    }

    private double getB(int i) {
        return 0;
    }

}
