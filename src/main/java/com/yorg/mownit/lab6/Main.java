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

        Main m = new Main();
        m.compareOnDifferentNumberOfPoints(6);
        m.compareOnDifferentPolynomialDegree(25);
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
            DataSeries series = plot.newDataSeries(String.format("N = %d", numOfPoints), PlotType.LINES);

            Point2D [] pointsToApproximateFrom = source.getFunctionValues(myFunction, numOfPoints);
            approximator = new Approximator(pointsToApproximateFrom, approxDegree);
            Function approximateFunction = approximator.discretePolynomialApproximation();
            Point2D [] pointsFromApproximation = source.getFunctionValues(approximateFunction, 20);
            series.addData(pointsFromApproximation);
        }

        plot.plot();
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
            DataSeries series = plot.newDataSeries(String.format("N = %d", approximationOrder), PlotType.LINES);

            Point2D [] pointsToApproximateFrom = source.getFunctionValues(myFunction, numOfPoints);
            approximator = new Approximator(pointsToApproximateFrom, approximationOrder);
            Function approximateFunction = approximator.discretePolynomialApproximation();
            Point2D [] pointsFromApproximation = source.getFunctionValues(approximateFunction, 20);
            series.addData(pointsFromApproximation);
        }

        plot.plot();

    }

}
