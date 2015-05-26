package com.yorg.mownit.lab5.interpolation;

import com.yorg.mownit.commons.Point2D;

public class ClampedSplineHelper {

    private final Point2D [] points;

    public ClampedSplineHelper(Point2D [] points) {

        this.points = points;
    }

    public double h(int i) {
        return points[i+1].getX() - points[i].getX();
    }

}
