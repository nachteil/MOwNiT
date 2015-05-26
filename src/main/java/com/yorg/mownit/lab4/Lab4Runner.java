package com.yorg.mownit.lab4;

import com.yorg.mownit.commons.CSVLogger;
import com.yorg.mownit.commons.Function;
import com.yorg.mownit.commons.Point2D;
import com.yorg.mownit.commons.Range;
import com.yorg.mownit.commons.datasources.ChebyschevSource;
import com.yorg.mownit.commons.datasources.DoubledPointSource;
import com.yorg.mownit.commons.datasources.PointSource;
import com.yorg.mownit.commons.datasources.RegularSource;
import com.yorg.mownit.commons.functioncomp.AbsIntegralComparator;
import com.yorg.mownit.commons.functioncomp.FunctionComparator;
import com.yorg.mownit.commons.plot.Plot;
import com.yorg.mownit.lab4.interpolation.Interpolator;
import com.yorg.mownit.lab4.interpolation.LagrangeInterpolator;
import com.yorg.mownit.lab4.interpolation.NewtonInterpolator;
import com.yorg.mownit.lab4.interpolation.hermit.HermitInterpolator;
import com.yorg.mownit.lab4.polynomial.Polynomial;
import lombok.SneakyThrows;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class Lab4Runner {

    private static final Range range = new Range(1.0/3.0, 3.0);
    private static final Function function = x -> x * Math.sin(3 * Math.PI / x);

    // calculated analytically
    private static final Function derivative = x -> Math.sin(3.0 * Math.PI / x) - 3.0 * Math.PI * Math.cos(3.0 * Math.PI / x) / x;

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

    @SneakyThrows
    private Plot getPlotTemplate(int n, String suffix, String dirname) {

        File file = new File(dirname);
        if(!file.exists() || !file.isDirectory()) {
            file.mkdirs();
        }

        Plot plot = Plot.newPlot()
                .withPlotFileName(dirname + "/" + "lab4_ex1_n" + n + "_" + suffix + ".png")
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

        Plot allPlot = getPlotTemplate(n, "all", "ex1_all");
        Plot regularPlot = getPlotTemplate(n, "regular", "ex_1regular");
        Plot chebyschevPlot = getPlotTemplate(n, "chebyschev", "ex1_chebyschev");

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

        allPlot.plotWithWindow();
        regularPlot.plotWithWindow();
        chebyschevPlot.plotWithWindow();
    }

    public void runEx1() {
        for(int i = 1; i < 51; ++i) {
            plotInterpolationForN(i);
        }
    }

    @SneakyThrows
    public void compare() {

        FunctionComparator absCmp = new AbsIntegralComparator(range);

        Interpolator lagrange = new LagrangeInterpolator();
        Interpolator hermit = new HermitInterpolator(derivative);
        Interpolator newton = new NewtonInterpolator();

        PointSource regularSource = new RegularSource(range);
        PointSource chebyschevSource = new ChebyschevSource(range);

        PointSource doubledRegular = new DoubledPointSource(regularSource);
        PointSource doubledChebyschev = new DoubledPointSource(chebyschevSource);

        CSVLogger logger = new CSVLogger("lab4_comparison.csv");

        Point2D [] regLagP = new Point2D[50];
        Point2D [] cheLagP = new Point2D[50];
        Point2D [] regNewP = new Point2D[50];
        Point2D [] cheNewP = new Point2D[50];
        Point2D [] regHerP = new Point2D[50];
        Point2D [] cheHerP = new Point2D[50];

        List<Thread> thread = new ArrayList<>(50);

        for(int k = 1; k < 51; ++k) {

            final int i = k;
            Thread t = new Thread(() -> {

                System.out.println(i);

                Function regularLagrangeInterpolant = lagrange.getInterpolationPolynomial(regularSource.getFunctionValues(function, i));
                Function chebyschevLagrangeInterpolant = lagrange.getInterpolationPolynomial(chebyschevSource.getFunctionValues(function, i));

                Function regularNewtonInterpolant = newton.getInterpolationPolynomial(regularSource.getFunctionValues(function, i));
                Function chebyschevNewtonInterpolant = newton.getInterpolationPolynomial(chebyschevSource.getFunctionValues(function, i));

                Function regularHermitInterpolant = hermit.getInterpolationPolynomial(doubledRegular.getFunctionValues(function, i));
                Function chebyschevHermitInterpolant = hermit.getInterpolationPolynomial(doubledChebyschev.getFunctionValues(function, i));

                System.out.println("Interpolated");

                double regLag = absCmp.compare(function, regularLagrangeInterpolant);
                double chebLag = absCmp.compare(function, chebyschevLagrangeInterpolant);

                System.out.println("Lagrange compared");

                double regNew = absCmp.compare(function, regularNewtonInterpolant);
                double chebNew = absCmp.compare(function, chebyschevNewtonInterpolant);

                System.out.println("Newton compared");

                double regHer= absCmp.compare(function, regularHermitInterpolant);
                double chebHer= absCmp.compare(function, chebyschevHermitInterpolant);

                System.out.println("Hermit compared");

                regLagP[i-1] = new Point2D(i, regLag);
                cheLagP[i-1] = new Point2D(i, chebLag);
                regNewP[i-1] = new Point2D(i, regNew);
                cheNewP[i-1] = new Point2D(i, chebNew);
                regHerP[i-1] = new Point2D(i, regHer);
                cheHerP[i-1] = new Point2D(i, chebHer);

                logger.logLine(i, regLag, chebLag, regNew, chebNew, regHer, chebHer);
            });

            thread.add(t);
            t.start();
        }

        for(Thread t : thread) {
            t.join();
        }

        Plot plotCheb = Plot.newPlot()
                .withPlotFileName("lab4_cmp_cheb.png")
                .withTitle("Interpolation error (on Chebyschev nodes)")
                .withXLabel("x")
                .withYLabel("y")
                .withXRange(new Range(0, 45))
                .withFunctionPlotRange(range)
                .build();

        Plot plotReg = Plot.newPlot(false, true)
                .withPlotFileName("lab4_cmp.png")
                .withTitle("Interpolation error (on regular nodes)")
                .withXLabel("x")
                .withYLabel("y")
                .withXRange(new Range(0, 45))
                .withFunctionPlotRange(range)
                .build();

        plotReg.addPointsPlot(regLagP, "Lagrange on regular nodes");
        plotReg.addPointsPlot(regNewP, "Newton on regular nodes");

        plotCheb.addPointsPlot(cheNewP, "Newton on Chebyschev nodes");
        plotCheb.addPointsPlot(cheLagP, "Lagrange on Chebyschev nodes");


        plotCheb.plotWithWindow();
        plotReg.plotWithWindow();
    }

    public void runHermit() {


        HermitInterpolator hermitInterpolator = new HermitInterpolator(derivative);

        PointSource source = new DoubledPointSource(new ChebyschevSource(range));


        for(int i = 1; i < 51; ++i) {
            Plot plot = getPlotTemplate(i, "hermit", "ex2");
            Point2D [] points = source.getFunctionValues(function, i);
            Polynomial interpolant = hermitInterpolator.getInterpolationPolynomial(points);

            plot.addFunctionPlot(interpolant, "Hermit interpolation");
            plot.addPointsPlot(points, "Interpolation nodes");
            plot.plotWithWindow();
        }


    }

    public static void main(String[] args) {

        Lab4Runner runner = new Lab4Runner();

        runner.compare();
    }

}
