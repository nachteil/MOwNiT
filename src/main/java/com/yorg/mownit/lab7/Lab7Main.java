package com.yorg.mownit.lab7;

import com.yorg.mownit.commons.CSVLogger;
import com.yorg.mownit.commons.Function;
import com.yorg.mownit.commons.Point2D;
import com.yorg.mownit.commons.Range;
import com.yorg.mownit.commons.plot.DataSeries;
import com.yorg.mownit.commons.plot.Plot;
import com.yorg.mownit.commons.plot.PlotType;
import com.yorg.mownit.lab7.integration.AbstractIntegrator;
import com.yorg.mownit.lab7.integration.GaussIntegrator;
import com.yorg.mownit.lab7.integration.NewtonCotesIntegrator;
import lombok.SneakyThrows;

public class Lab7Main {

    private static final Function function = x -> x * Math.sin(3.0 * Math.PI / x);
    private static final double exactValue = -1.6469292797054044247820816863487;
    private static final Range range = new Range(1.0/3.0, 3.0);

    @SneakyThrows
    public void run() {

        int [] bigIntervals = {1, 5, 10, 20, 50, 100, 200, 500, 1000, 2000, 5000, 10000, 20000};
        int [] smallintervals = {1,2,3,4,5,6,7,8,9,10,20,30,40,50,60,70,80,90,100,200,300,400,500, 600, 700, 800, 900, 1000};

        int intervals [] = bigIntervals;

        CSVLogger csvLogger = new CSVLogger("lab7_newton-cotes.csv");
        csvLogger.logLine("#integral_value","number_of_intervals","quadrature_degree","relative_error");

        AbstractIntegrator integrator;

        Plot newtonPlot = Plot.newPlot(true, true)
                .withFunctionPlotRange(new Range(0.1, 1000))
                .withPlotFileName("newton-cotes.png")
                .withTitle("Relative error of f(x) = x sin(3 PI / x) integral\\nin [1/3;3] (Newton-Cotes integration)")
                .withXLabel("Number of intervals")
                .withYLabel("Relative error [%]")
                .withXRange(new Range(0.1, 1000))
                .build();

        Plot gaussPlot = Plot.newPlot(true, true)
                .withFunctionPlotRange(new Range(0.1, 1000))
                .withPlotFileName("gauss-cotes.png")
                .withTitle("Relative error of f(x) = x sin(3 PI / x) integral\\nin [1/3;3] (Gauss-Legrande integration)")
                .withXLabel("Number of intervals")
                .withYLabel("Relative error [%]")
                .withXRange(new Range(0.1, 1000))
                .build();

        DataSeries [] series = new DataSeries[4];

        for(int i = 0; i < 4; ++i) {
            series[i] = newtonPlot.newDataSeries("Quadrature degree: " + i, PlotType.LINESPOINTS);
        }

        for(int numOfIntervals: intervals) {
            for(int degree = 1; degree <= 4; ++degree) {

                integrator = new NewtonCotesIntegrator(degree);
                double integralValue = integrator.integrate(function, range, numOfIntervals);
                double error = Math.abs((exactValue - integralValue) / exactValue) * 100.0;
                csvLogger.logLine(integralValue, numOfIntervals, degree, error);

                series[degree-1].addData(new Point2D(numOfIntervals, error));
            }
            System.out.println("Finished for " + numOfIntervals + " intervals");
        }

        newtonPlot.plot();

        // gauss
        series = new DataSeries[5];
        csvLogger = new CSVLogger("lab7_gauss-legrande.csv");

        for(int i = 0; i < 5; ++i) {
            series[i] = gaussPlot.newDataSeries("Quadrature degree: " + i, PlotType.LINESPOINTS);
        }

        for(int numOfIntervals: intervals) {
            for(int degree = 1; degree <= 5; ++degree) {

                integrator = new GaussIntegrator(degree);
                double integralValue = integrator.integrate(function, range, numOfIntervals);
                double error = Math.abs((exactValue - integralValue) / exactValue) * 100.0;
                csvLogger.logLine(integralValue, numOfIntervals, degree, error);

                series[degree-1].addData(new Point2D(numOfIntervals, error));
            }
            System.out.println("Finished for " + numOfIntervals + " intervals");
        }

        gaussPlot.plot();
    }

    public static void main(String[] args) {

        new Lab7Main().run();

    }

}
