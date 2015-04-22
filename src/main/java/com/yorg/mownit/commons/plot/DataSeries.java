package com.yorg.mownit.commons.plot;

import com.yorg.mownit.commons.Point2D;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class DataSeries {

    public final List<Point2D> points;
    public final String description;

    DataSeries(String description) {
        this.description = description;
        points = new ArrayList<>();
    }

    public void addData(Point2D ... point2Ds) {
        Collections.addAll(points, point2Ds);
    }

    List<Point2D> getPoints() {
        return points;
    }

    String getDescription() {
        return description;
    }
}
