package com.yorg.mownit.lab5.interpolation;

import com.yorg.mownit.commons.Point2D;

public class NaturalSplineHelper {

    private final Point2D [] points;

    public NaturalSplineHelper(Point2D [] points) {
        this.points = points;
    }

    double u(int i) {
        return 2 * (h(i) + h(i-1));
    }

    double b(int i) {
        return 6 * (points[i+1].getY() - points[i].getY()) / h(i);
    }

    double v(int i) {
        return b(i) - b(i-1);
    }

    double h(int i) {
        return points[i+1].getX() - points[i].getX();
    }

}
