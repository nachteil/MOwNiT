package com.yorg.mownit.commons.datasources;

import com.yorg.mownit.commons.Function;
import com.yorg.mownit.commons.Point2D;
import com.yorg.mownit.commons.Range;

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractSource implements PointSource {

    final Range range;

    public AbstractSource(Range range) {
        this.range = range;
    }

    @Override
    public Point2D[] getFunctionValues(Function f, int numberOfPoints) {

        double [] xVals = getXValues(numberOfPoints);

        Point2D [] points = new Point2D[numberOfPoints];
        double step = (range.end - range.start) / (numberOfPoints - 1);
        for(int i = 0; i < numberOfPoints; ++i) {
            double x = xVals[i];
            double y = f.getValue(x);
            Point2D point2D = new Point2D(x, y);
            points[i] = point2D;
        }

        return points;
    }

    abstract double [] getXValues(int numberOfPoints);
}
