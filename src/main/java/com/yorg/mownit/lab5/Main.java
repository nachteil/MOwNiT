package com.yorg.mownit.lab5;

import com.yorg.mownit.commons.Function;
import com.yorg.mownit.commons.Point2D;
import com.yorg.mownit.commons.Range;
import com.yorg.mownit.commons.datasources.PointSource;
import com.yorg.mownit.commons.datasources.RegularSource;
import com.yorg.mownit.commons.plot.Plot;
import com.yorg.mownit.lab5.interpolation.CubicInterpoler;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Main {

    public static final double k = 3;
    private final Range range = new Range(1.0/3.0, 3.0);

    Function f = (x) -> x * Math.sin(k * Math.PI / x);
    Function df = x -> Math.sin(k * Math.PI / x) - 3.0 * Math.PI * Math.cos(k * Math.PI / x) / x;

    private static final Map<Integer, Double> clampedMaxErr = new HashMap<>();
    private static final Map<Integer, Double> naturalMaxErr = new HashMap<>();
    private static final Map<Integer, Double> clampedIntergralErr = new HashMap<>();
    private static final Map<Integer, Double> naturalIntegralErr = new HashMap<>();

    public void runCubicSplines(int n) {

        PointSource source = new RegularSource(range);
        Point2D [] points = source.getFunctionValues(f, n);

        CubicInterpoler interpoler = new CubicInterpoler();
        Function naturalSpline = interpoler.natural(points);
        Function clampedSpline = interpoler.clamped(points, df.getValue(range.start), df.getValue(range.end));

        Plot plot = getExamplePlot(n);

        plot.addFunctionPlot(f, "Original function");
        plot.addPointsPlot(points, "Source points");
        plot.addFunctionPlot(naturalSpline, "Natural spline");
        plot.addFunctionPlot(clampedSpline, "Clamped spline");

        plot.plotWithoutWindow(false);

        printErrorLine(n, clampedSpline, naturalSpline);
    }

    private void printErrorLine(int n, Function clampedSpline, Function naturalSpline) {

        double sumClamped = 0.0;
        double sumNatural = 0.0;

        double maxErrorClamped = Math.abs(clampedSpline.getValue(range.start) - f.getValue(range.start));
        double maxErrorNatural = Math.abs(naturalSpline.getValue(range.start) - f.getValue(range.start));

        double dx = 1e-5;
        for(double x = range.start; x <= range.end; x += dx) {

            double absClamped = Math.abs(clampedSpline.getValue(x));
            double absNatural = Math.abs(naturalSpline.getValue(x));

            sumClamped += absClamped * dx;
            sumNatural += absNatural * dx;

            double clampedErrorTerm = Math.abs(clampedSpline.getValue(x) - f.getValue(x));
            double naturalErrorTerm = Math.abs(naturalSpline.getValue(x) - f.getValue(x));

            maxErrorClamped = Math.max(clampedErrorTerm, maxErrorClamped);
            maxErrorNatural = Math.max(naturalErrorTerm, maxErrorNatural);
        }

        naturalIntegralErr.put(n, sumNatural);
        clampedIntergralErr.put(n, sumClamped);
        naturalMaxErr.put(n, maxErrorNatural);
        clampedMaxErr.put(n, maxErrorClamped);

        System.out.println(String.format("N: %d - max err. clamped: %f - max err. natural: %f - integral error clamped: %f - integral error natural %f",
                n, maxErrorClamped, maxErrorNatural, sumClamped, sumNatural));

    }

    public static void main(String[] args) throws IOException {

        Main main = new Main();

        int [] ns = new int[] {5, 10, 15, 20, 25, 30, 35, 40};

        for(int n : ns) {
            main.runCubicSplines(n);
        }

        Plot errorPlot = Plot.newPlot()
                .withTitle("Interpolation error")
                .withXLabel("Number of interpolation points")
                .withYLabel("Error")
                .withXRange(0, 50)
                .withPlotFileName("interpolation-error.png")
                .build();

        errorPlot.addLinesPointsPlot(mapToPoints(clampedMaxErr), "Clamped spline - max. error");
        errorPlot.addLinesPointsPlot(mapToPoints(clampedIntergralErr), "Clamped spline - integral error");
        errorPlot.addLinesPointsPlot(mapToPoints(naturalMaxErr), "Natural spline - max. error");
        errorPlot.addLinesPointsPlot(mapToPoints(naturalIntegralErr), "Natural spline - max. error");

        errorPlot.plotWithoutWindow(false);
    }

    private static Point2D [] mapToPoints(Map<Integer, Double> map) {
        List<Point2D> points = map.keySet().stream().sorted().map(i -> new Point2D(i, map.get(i))).collect(Collectors.toList());
        return points.toArray(new Point2D[map.keySet().size()]);
    }

    private Plot getExamplePlot(int n) {

        return Plot.newPlot()
                .withFunctionPlotRange(range)
                .withXRange(range)
                .withPlotFileName("inteprolation-"+n+".png")
                .withTitle("Cubic spline interpolation on " + n + " points")
                .withXLabel("x")
                .withYLabel("y")
                .build();
    }

}
