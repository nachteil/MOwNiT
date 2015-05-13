package com.yorg.mownit.lab8;

import com.yorg.mownit.commons.Point2D;
import com.yorg.mownit.commons.Range;
import com.yorg.mownit.commons.plot.Plot;

public abstract class AbstractSolver {

    protected final int numOfSteps;
    protected final DERightSideFunction function;

    protected AbstractSolver(DERightSideFunction function, int numOfSteps) {
        this.function = function;
        this.numOfSteps = numOfSteps;
    }

    public Plot getSolutionPlot(Range range, double initialValue) {

        Point2D[] solutionPoints = solve(range, initialValue);

        Range plottingRange = getPlottingRangeWithMargins(range);

        Plot plot = Plot.newPlot()
                .withFunctionPlotRange(range)
                .withXRange(plottingRange)
                .withPlotFileName("solution.png")
                .withTitle(getDescription() + " solution vs exact solution")
                .withXLabel("x")
                .withYLabel("y")
                .build();

        plot.addPointsPlot(solutionPoints, getDescription() + " points (n=" + numOfSteps + ")");

        return plot;
    }

    protected abstract Point2D [] solve(Range range, double initialValue);
    protected abstract String getDescription();

    private Range getPlottingRangeWithMargins(Range range) {
        double width = range.end - range.start;
        double from = range.start - width * 0.2;
        double to = range.end + width * 0.2;
        return new Range(from, to);
    }

}
