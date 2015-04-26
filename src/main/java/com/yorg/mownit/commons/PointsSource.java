package com.yorg.mownit.commons;

import java.util.ArrayList;
import java.util.List;

public class PointsSource {

    private final Range range;

    public PointsSource(Range range) {
        this.range = range;
    }

    public Point2D [] getFunctionValues(Function f, int numberOfPoints) {

        List<Point2D> points = new ArrayList<>();
        double step = (range.end - range.start) / (numberOfPoints - 1);
        for(int i = 0; i < numberOfPoints; ++i) {
            double x = range.start + step * i;
            double y = f.getValue(x);
            Point2D point2D = new Point2D(x, y);
            points.add(point2D);
        }

        return points.toArray(new Point2D [points.size()]);
    }

}
