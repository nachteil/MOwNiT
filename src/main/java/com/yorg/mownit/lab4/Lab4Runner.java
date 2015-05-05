package com.yorg.mownit.lab4;

import com.yorg.mownit.commons.*;
import com.yorg.mownit.commons.datasources.ChebyschevSource;
import com.yorg.mownit.commons.datasources.PointSource;
import com.yorg.mownit.commons.datasources.RegularSource;
import com.yorg.mownit.commons.plot.DataSeries;
import com.yorg.mownit.commons.plot.Plot;
import com.yorg.mownit.commons.plot.PlotType;
import com.yorg.mownit.lab4.interpolation.Interpolator;
import com.yorg.mownit.lab4.interpolation.LagrangeInterpolator;
import com.yorg.mownit.lab4.interpolation.NewtonInterpolator;
import com.yorg.mownit.lab4.polynomial.Polynomial;
import lombok.SneakyThrows;

import javax.swing.*;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Lab4Runner {

    private static final Range range = new Range(1.0/3.0, 3.0);
    private static final Function function = x -> x * Math.sin(3 * Math.PI / x);

    public double  getMaxDiff(Polynomial interpolant) {

        double a = range.start;
        double b = range.end;

        double diff = interpolant.getValue(a) - function.getValue(a);
        diff = Math.abs(diff);

        for(double d = a; d <= b + 1e-5; d += 0.001d) {
            double curr = interpolant.getValue(d) - function.getValue(d);
            curr = Math.abs(curr);
            diff = curr > diff ? curr : diff;
        }
        return diff;
    }

    public double getAbsIntegral(Polynomial interpolant) {

        double a = range.start;
        double b = range.end;

        double sum = 0.0;

        for(double d = a; d <= b; d += 1e-5) {
            sum += Math.abs(interpolant.getValue(d) - function.getValue(d));
        }
        return sum;
    }

    public double getSquareIntegral(Polynomial interpolant) {

        double a = range.start;
        double b = range.end;

        double sum = 0.0;

        for(double d = a; d <= b; d += 1e-5) {
            sum += Math.pow(interpolant.getValue(d) - function.getValue(d), 2);
        }
        return sum;
    }

    private Plot getPlotTemplate(int n, String suffix) {

        Plot plot = Plot.newPlot()
                .withPlotFileName("lab4_ex1_n" + n + "_" + suffix + ".png")
                .withTitle("Interpolation for n = " + n)
                .withXLabel("x")
                .withYLabel("y")
                .withXRange(new Range(0, 3.5))
                .withFunctionPlotRange(range)
                .build();

        plot.addFunctionPlot(function, "Original function");

        return plot;
    }

    public void plotInterpolationForN(int n) {

        Plot allPlot = getPlotTemplate(n, "all");
        Plot regularPlot = getPlotTemplate(n, "regular");
        Plot chebyschevPlot = getPlotTemplate(n, "chebyschev");

        PointSource regularPointSource = new RegularSource(range);
        PointSource chebyschevPointSource = new ChebyschevSource(range);

        Interpolator newtonInterpolator = new NewtonInterpolator();

        Point2D [] regularPoints = regularPointSource.getFunctionValues(function, n);
        Point2D [] chebyschevPoints = chebyschevPointSource.getFunctionValues(function, n);

        Polynomial regularNewtonInterpolant = newtonInterpolator.getInterpolationPolynomial(regularPoints);
        Polynomial chebyschevNewtonInterpolant = newtonInterpolator.getInterpolationPolynomial(chebyschevPoints);

        allPlot.addFunctionPlot(regularNewtonInterpolant, "Interpolation on regular nodes");
        allPlot.addFunctionPlot(chebyschevNewtonInterpolant, "Interpolation on Chebyschev nodes");

        allPlot.addPointsPlot(regularPoints, "Regular interpolation nodes (" + n + ")");
        allPlot.addPointsPlot(chebyschevPoints, "Chebyschev interpolation nodes (" + n + ")");

        regularPlot.addFunctionPlot(regularNewtonInterpolant, "Interpolation on regular nodes");
        regularPlot.addPointsPlot(regularPoints, "Regular interpolation nodes");

        chebyschevPlot.addFunctionPlot(chebyschevNewtonInterpolant, "Interpolation on Chebyschev nodes");
        chebyschevPlot.addPointsPlot(chebyschevPoints, "Chebyschev interpolation nodes");

        allPlot.plot();
        regularPlot.plot();
        chebyschevPlot.plot();
    }

    public static void main(String[] args) {

        Lab4Runner runner = new Lab4Runner();

        int [] sizes = {2,3,4,5,6,7,8,9,10,14,17,20,25,30,35,40,45,50};
        for(int size : sizes) {
            runner.plotInterpolationForN(size);
        }

    }

}
