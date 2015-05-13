package com.yorg.mownit.lab8;

import com.yorg.mownit.commons.Point2D;
import com.yorg.mownit.commons.Range;

public class RungeKutaSolver extends AbstractSolver {

    public RungeKutaSolver(DERightSideFunction function, int numOfSteps) {
        super(function, numOfSteps);
    }

    @Override
    protected Point2D[] solve(Range range, double initialValue) {

        Point2D [] result = new Point2D[numOfSteps];
        result[0] = new Point2D(range.start, initialValue);
        double step = (range.end - range.start) / numOfSteps;
        double previousValue = initialValue;

        for(int i = 1; i < numOfSteps; ++i) {

            double x = range.start + (i-1)*step;
            double newX = x + step;

            double k1 = step * function.getVal(x, previousValue);
            double k2 = step * function.getVal(x+step/2.0, previousValue + k1/2.0);
            double k3 = step * function.getVal(x+step/2.0, previousValue + k2/2.0);
            double k4 = step * function.getVal(x+step, previousValue+k3);

            double y = previousValue + (k1 + 2.0 * k2 + 2.0 * k3 + k4) / 6.0;
            previousValue = y;
            result[i] = new Point2D(newX, y);
        }
        return result;
    }

    @Override
    protected String getDescription() {
        return "Runge-Kuta (4th degree) method";
    }
}
