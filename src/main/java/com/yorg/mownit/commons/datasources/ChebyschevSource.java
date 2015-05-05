package com.yorg.mownit.commons.datasources;

import com.yorg.mownit.commons.Function;
import com.yorg.mownit.commons.Point2D;
import com.yorg.mownit.commons.Range;

import java.util.ArrayList;
import java.util.List;

public class ChebyschevSource extends AbstractSource {

    public ChebyschevSource(Range range) {
        super(range);
    }

    @Override
    double[] getXValues(int numberOfPoints) {

        double [] points = new double [numberOfPoints];

        for(int j = 0; j < numberOfPoints; ++j) {
            double originalX = Math.cos(Math.PI * ((2.0d * (j+1) - 1.0d)/(2.0d * numberOfPoints)));
            double scaledX = (originalX + 1.0d) / 2.0d;
            scaledX *= (range.end - range.start);
            scaledX += range.start;
            points[j] = scaledX;
        }

        return points;
    }
}
