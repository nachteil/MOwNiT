package com.yorg.mownit.lab6;

import com.yorg.mownit.commons.*;
import com.yorg.mownit.commons.datasources.PointSource;
import com.yorg.mownit.commons.datasources.RegularSource;
import com.yorg.mownit.commons.plot.DataSeries;
import com.yorg.mownit.commons.plot.Plot;
import com.yorg.mownit.commons.plot.PlotType;

public class Main {

    public static final Function myFunction = x -> x * Math.sin(3.0 * Math.PI / x);
    public static final Range myRange = new Range(1.0/3.0, 3.0);
    public static final PointSource source = new RegularSource(myRange);

    public static void main(String[] args) {

        Plot plot = Plot.newPlot()
                .withTitle("Comparison based on different \\polynomial degree\\nNumber of approximation points: " + 10)
                .withXLabel("x")
                .withYLabel("y")
                .withXRange(myRange)
                .withYRange(-3.0, 2.0)
                .build();

        plot.addFunctionPlot(myFunction, "original function");
        Point2D[] points = source.getFunctionValues(myFunction, 25);
        Function interpolation = new TrigonometricApproximator(points, 65).getApproximateFunction();
        plot.addFunctionPlot(interpolation, "interpolation");
        plot.addPointsPlot(points, "interpolation points");
        plot.plotWithWindow();
    }

    public void compareOnDifferentNumberOfPoints(int approxDegree) {

        Plot plot = Plot.newPlot()
                .withTitle("Comparison based on different \\number of approximation points\\nPolynomial degree: " + approxDegree)
                .withXLabel("x")
                .withYLabel("y")
                .withXRange(myRange)
                .withYRange(-3.0, 2.0)
                .build();

        Point2D [] originalPoints = source.getFunctionValues(myFunction, 200);
        DataSeries originalDataSeries = plot.newDataSeries("Original function", PlotType.LINES);
        originalDataSeries.addData(originalPoints);

        Approximator approximator;

        for(int numOfPoints = approxDegree; numOfPoints <= 20; numOfPoints += 5) {

            Point2D [] pointsToApproximateFrom = source.getFunctionValues(myFunction, numOfPoints);
            approximator = new Approximator(pointsToApproximateFrom, approxDegree);
            Function approximateFunction = approximator.discretePolynomialApproximation();
            plot.addFunctionPlot(approximateFunction, String.format("N = %d", numOfPoints));
        }

        plot.plotWithWindow();
    }

    public void compareOnDifferentPolynomialDegree(int numOfPoints) {

        Plot plot = Plot.newPlot()
                .withTitle("Comparison based on different \\polynomial degree\\nNumber of approximation points: " + numOfPoints)
                .withXLabel("x")
                .withYLabel("y")
                .withXRange(myRange)
                .withYRange(-3.0, 2.0)
                .build();

        Point2D [] originalPoints = source.getFunctionValues(myFunction, 200);
        DataSeries originalDataSeries = plot.newDataSeries("Original function", PlotType.LINES);
        originalDataSeries.addData(originalPoints);

        Approximator approximator;

        for(int approximationOrder = 1; approximationOrder <= numOfPoints; approximationOrder += 8) {

            Point2D [] pointsToApproximateFrom = source.getFunctionValues(myFunction, numOfPoints);
            approximator = new Approximator(pointsToApproximateFrom, approximationOrder);
            Function approximateFunction = approximator.discretePolynomialApproximation();
            plot.addFunctionPlot(approximateFunction, String.format("N = %d", approximationOrder));
        }

        plot.plotWithWindow();

    }

}
