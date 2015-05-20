package com.yorg.mownit.commons.datasources;

import com.yorg.mownit.commons.Function;
import com.yorg.mownit.commons.Point2D;

public class DoubledPointSource implements PointSource {

    private final PointSource source;

    public DoubledPointSource(PointSource source) {
        this.source = source;
    }

    @Override
    public Point2D[] getFunctionValues(Function f, int numberOfPoints) {

        Point2D[] points = source.getFunctionValues(f, numberOfPoints);
        Point2D[] doubledPoints = new Point2D[2 * numberOfPoints];

        for(int i = 0; i < points.length; ++i) {
            doubledPoints[2*i] = points[i];
            doubledPoints[2*i + 1] = points[i];
        }
        return doubledPoints;
    }
}
