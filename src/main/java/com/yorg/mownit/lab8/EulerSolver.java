package com.yorg.mownit.lab8;

import com.yorg.mownit.commons.Point2D;
import com.yorg.mownit.commons.Range;

public class EulerSolver extends AbstractSolver {

    public EulerSolver(DERightSideFunction function, int numOfSteps) {
        super(function, numOfSteps);
    }

    @Override
    protected String getDescription() {
        return "Euler method";
    }

    @Override
    protected Point2D [] solve(Range range, double initialValue) {

        Point2D [] result = new Point2D[numOfSteps];
        result[0] = new Point2D(range.start, initialValue);
        double step = (range.end - range.start) / numOfSteps;
        double previousValue = initialValue;

        for(int i = 1; i < numOfSteps; ++i) {

            double x = range.start + i*step;
            double y = previousValue + step*function.getVal(x, previousValue);
            previousValue = y;

            result[i] = new Point2D(x, y);
        }
        return result;
    }
}
