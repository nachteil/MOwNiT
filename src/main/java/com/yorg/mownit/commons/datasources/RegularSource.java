package com.yorg.mownit.commons.datasources;

import com.yorg.mownit.commons.Function;
import com.yorg.mownit.commons.Point2D;
import com.yorg.mownit.commons.Range;
import com.yorg.mownit.lab1.math.DoubleMatrix;

import java.util.ArrayList;
import java.util.List;

public class RegularSource extends AbstractSource {

    public RegularSource(Range range) {
        super(range);
    }

    @Override
    double [] getXValues(int numberOfPoints) {

        double step = (range.end - range.start) / (numberOfPoints - 1);
        double [] points = new double [numberOfPoints];

        for(int i = 0; i < numberOfPoints; ++i) {
            double x = range.start + step * i;
            points[i] = x;
        }
        return points;
    }
}
