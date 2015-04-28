package com.yorg.mownit.lab3;

import com.yorg.mownit.commons.*;
import com.yorg.mownit.commons.datasources.PointSource;
import com.yorg.mownit.commons.datasources.RegularSource;
import com.yorg.mownit.commons.plot.DataSeries;
import com.yorg.mownit.commons.plot.Plot;
import com.yorg.mownit.commons.plot.PlotType;
import com.yorg.mownit.lab3.solvers.ISolver;
import com.yorg.mownit.lab3.solvers.NewtonSolver;
import com.yorg.mownit.lab3.solvers.SecantSolver;
import com.yorg.mownit.lab3.systems.MultidimensionalSolver;
import com.yorg.mownit.lab3.systems.MyFunction;
import com.yorg.mownit.lab3.utils.Result;
import com.yorg.mownit.lab3.utils.SolveParams;
import com.yorg.mownit.lab3.utils.Stopper;
import lombok.SneakyThrows;
import org.ejml.simple.SimpleMatrix;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class Main {


    private static Properties properties = null;

    public static void main(String ... args) throws FileNotFoundException {
        runMultidimensionalSolveAnalisys();
    }

    private static void runMultidimensionalSolveAnalisys() throws FileNotFoundException {

        MultidimensionalSolver multidimensionalSolver = new MultidimensionalSolver();

        SimpleMatrix startVec = new SimpleMatrix(3, 1);
        SimpleMatrix exact = new SimpleMatrix(3, 1);

        double a = -Math.sqrt(3.0/2.0);
        double b = 0.0;
        double c = -0.5;

        exact.set(0, 0, a);
        exact.set(1, 0, b);
        exact.set(2, 0, c);

        MultidimensionalSolver solver = new MultidimensionalSolver();

        String [] fnames = {"solution_vector_1.txt", "solution_vector_2.txt", "solution_vector_3.txt", "solution_vector_4.txt"};
        double [][] vecVals = {{-1.0, 0.0, 0.0}, {1.0, 0.0, 0.0}, {-Math.sqrt(1.5), 0, -0.5}, {Math.sqrt(1.5), 0.0, -0.5}};

        for(int i = 0; i < 4; ++i) {

            PrintWriter writer = new PrintWriter(new FileOutputStream(fnames[i]));
            writer.println("delta v1, delta v2, delta v3, final result");

            exact = new SimpleMatrix(3, 1);

            exact.set(0, 0, vecVals[i][0]);
            exact.set(1, 0, vecVals[i][1]);
            exact.set(2, 0, vecVals[i][2]);

            for(double deltaA = -1.0; deltaA < 1.0; deltaA += 0.1) {
                for(double deltaB = -1.0; deltaB < 1.0; deltaB += 0.1) {
                    for(double deltaC = -1.0; deltaC < 1.0; deltaC += 0.1) {

                        SimpleMatrix result;
                        StringBuilder line;
                        line = new StringBuilder(String.format("%f, %f, %f, ", deltaA, deltaB, deltaC));

                        try {
                            startVec.set(0, 0, vecVals[i][0] + deltaA);
                            startVec.set(1, 0, vecVals[i][1] + deltaB);
                            startVec.set(2, 0, vecVals[i][2] + deltaC);

                            result = solver.solve(startVec);

                            if(result == null) {
                                line.append("not convergent");
                            } else if(VectorUtils.euclidianDiff(result, exact) > 0.3) {
                                line.append(String.format("[%2.3f,%2.3f,%2.3f]", result.get(0), result.get(1), result.get(2)));
                            } else {
                                line.append("exact result matched");
                            }
                        } catch (Exception e) {
                            line.append("error");
                        }
                        writer.println(line.toString());
                    }
                }
            }
            writer.flush();
        }
    }

    public static void plotAnalyzedFunction(Range xrange, String plotFileName) {

        Function function = x -> Math.pow(x, 10) - Math.pow(1-x, 15);

        Plot plot = Plot.newPlot()
                .withTitle("Analyzed function")
                .withXLabel("x")
                .withYLabel("y")
                .withXRange(xrange)
                .withPlotFileName(plotFileName)
                .build();

        DataSeries series = plot.newDataSeries("x^{10} - (1-x)^{15}", PlotType.LINES);
        PointSource source = new RegularSource(xrange);
        Point2D[] functionValues = source.getFunctionValues(function, 100);
        series.addData(functionValues);
        plot.plot();

    }

    public static void performNewtonMethod() {

        SolveParams initialParams = getParams();

        Function analyzedFunction = getFunction();
        Function derivative = getDerivative();

        ISolver solver = new NewtonSolver(analyzedFunction, derivative);


        double start = initialParams.getRange().start;
        double end = initialParams.getRange().end;

        Plot plot = Plot.newPlot()
                .withPlotFileName("lab3_ex1_step_diff.png")
                .withTitle("Newton method in [0.1 ; 2.1]\\n|x^{(i+1)} - x^{(i)}| < R")
                .withXLabel("Starting point")
                .withYLabel("Number of iterations")
                .withYRange(0, 40)
                .withXRange(0.0, 2.0)
                .build();

        Stopper stopperPrecission = (x1, x2, fVal, iterNum) -> Math.abs(x1-x2) < initialParams.getAccuracy();

        for(double precission = 1e-5; precission > 1e-11; precission /= 100) {

            DataSeries series = plot.newDataSeries("R = " + String.format("%1.2e", precission), PlotType.LINESPOINTS);
            List<Point2D> points = new ArrayList<>();

            for(double startPoint = end; startPoint > start; startPoint -= 0.1d) {

                Range range = initialParams.getRange();
                SolveParams p = new SolveParams(range, initialParams.getAccuracy(), initialParams.getMaxIterationCount(), startPoint, 0.0);

                Result result = solver.solve(p, stopperPrecission);
//                printResult(p, result);
                points.add(new Point2D(startPoint, result.getNumIterations()));
            }
            series.addData(points.toArray(new Point2D[points.size()]));
        }

        plot.plot();

        Plot plot1 = Plot.newPlot()
                .withPlotFileName("lab3_ex1_abs_val.png")
                .withTitle("Newton method in [0.1 ; 2.1]\\n|f(x^{(i)})| < R")
                .withYRange(0, 40)
                .withXLabel("Starting point")
                .withYLabel("Number of iterations")
                .withXRange(0.0, 2.0)
                .build();

        Stopper stopperVal = (x1, x2, fVal, iterNum) -> Math.abs(fVal) < initialParams.getAccuracy();

        for(double precission = 1e-5; precission > 1e-13; precission /= 100) {

            DataSeries series = plot1.newDataSeries("R = " + String.format("%2.3e", precission), PlotType.LINESPOINTS);
            List<Point2D> points = new ArrayList<>();

            for(double startPoint = end; startPoint > start; startPoint -= 0.1d) {

                Range range = initialParams.getRange();
                SolveParams p = new SolveParams(range, initialParams.getAccuracy(), initialParams.getMaxIterationCount(), startPoint, 0.0);

                Result result = solver.solve(p, stopperVal);
//                printResult(p, result);
                points.add(new Point2D(startPoint, result.getNumIterations()));
            }
            series.addData(points.toArray(new Point2D[points.size()]));
        }

        plot1.plot();

    }

    public static void performSecantMethod() {

        performSecantFromAToVarFunAbs();
        performSecantFromAToVarIterDiff();
        performSecantFromVarToBFunAbs();
        performSecantFromVarToBIterDiff();
    }

    private static void performSecantFromAToVarIterDiff() {

        SolveParams initialParams = getParams();
        Function analyzedFunction = getFunction();

        ISolver solver = new SecantSolver(analyzedFunction);

        double start = initialParams.getRange().start;
        double end = initialParams.getRange().end;

        Plot plot = Plot.newPlot()
                .withPlotFileName("lab3_ex1_sec_step_diff_a_v.png")
                .withTitle("Secant method in [0.1 ; 2.1]\\n|x^{(i+1)} - x^{(i)}| < R\\nStarting from 0.1 and variable point")
                .withXLabel("Variable starting point")
                .withYLabel("Number of iterations")
                .withYRange(0, 40)
                .withXRange(0.0, 2.0)
                .build();

        Stopper stopperPrecission = (x1, x2, fVal, iterNum) -> Math.abs(x1-x2) < initialParams.getAccuracy();

        for(double precission = 1e-5; precission > 1e-11; precission /= 100) {

            DataSeries series = plot.newDataSeries("R = " + String.format("%1.2e", precission), PlotType.LINESPOINTS);
            List<Point2D> points = new ArrayList<>();

            for(double startPoint = end; startPoint > start; startPoint -= 0.1d) {

                Range range = initialParams.getRange();
                SolveParams p = new SolveParams(range, initialParams.getAccuracy(), initialParams.getMaxIterationCount(), range.start, startPoint);

                Result result = solver.solve(p, stopperPrecission);
                points.add(new Point2D(startPoint, result.getNumIterations()));
            }
            series.addData(points.toArray(new Point2D[points.size()]));
        }

        plot.plot();
    }

    private static void performSecantFromVarToBIterDiff() {

        SolveParams initialParams = getParams();
        Function analyzedFunction = getFunction();

        ISolver solver = new SecantSolver(analyzedFunction);

        double start = initialParams.getRange().start;
        double end = initialParams.getRange().end;

        Plot plot = Plot.newPlot()
                .withPlotFileName("lab3_ex1_sec_iter_diff_v_b.png")
                .withTitle("Secant method in [0.1 ; 2.1]\\n|f(x^{(i)})| < R\\nStarting from variable point and 2.1")
                .withYRange(0, 40)
                .withXLabel("Variable starting point")
                .withYLabel("Number of iterations")
                .withXRange(0.0, 2.0)
                .build();

        Stopper stopperVal = (x1, x2, fVal, iterNum) -> Math.abs(x1-x2) < initialParams.getAccuracy();

        for(double precission = 1e-5; precission > 1e-13; precission /= 100) {

            DataSeries series = plot.newDataSeries("R = " + String.format("%2.3e", precission), PlotType.LINESPOINTS);
            List<Point2D> points = new ArrayList<>();

            for(double startPoint = end; startPoint > start; startPoint -= 0.1d) {

                Range range = initialParams.getRange();
                SolveParams p = new SolveParams(range, initialParams.getAccuracy(), initialParams.getMaxIterationCount(), startPoint, range.end);

                Result result = solver.solve(p, stopperVal);
                points.add(new Point2D(startPoint, result.getNumIterations()));
            }
            series.addData(points.toArray(new Point2D[points.size()]));
        }

        plot.plot();

    }

    private static void performSecantFromAToVarFunAbs() {

        SolveParams initialParams = getParams();
        Function analyzedFunction = getFunction();

        ISolver solver = new SecantSolver(analyzedFunction);

        double start = initialParams.getRange().start;
        double end = initialParams.getRange().end;

        Plot plot = Plot.newPlot()
                .withPlotFileName("lab3_ex1_sec_abs_val_a_v.png")
                .withTitle("Secant method in [0.1 ; 2.1]\\n|f(x^{(i)})| < R\\nStarting from variable point and 2.1")
                .withYRange(0, 40)
                .withXLabel("Variable starting point")
                .withYLabel("Number of iterations")
                .withXRange(0.0, 2.0)
                .build();

        Stopper stopperVal = (x1, x2, fVal, iterNum) -> Math.abs(fVal) < initialParams.getAccuracy();

        for(double precission = 1e-5; precission > 1e-13; precission /= 100) {

            DataSeries series = plot.newDataSeries("R = " + String.format("%2.3e", precission), PlotType.LINESPOINTS);
            List<Point2D> points = new ArrayList<>();

            for(double startPoint = end; startPoint > start; startPoint -= 0.1d) {

                Range range = initialParams.getRange();
                SolveParams p = new SolveParams(range, initialParams.getAccuracy(), initialParams.getMaxIterationCount(), startPoint, range.end);

                Result result = solver.solve(p, stopperVal);
                points.add(new Point2D(startPoint, result.getNumIterations()));
            }
            series.addData(points.toArray(new Point2D[points.size()]));
        }

        plot.plot();

    }

    private static void performSecantFromVarToBFunAbs() {

        SolveParams initialParams = getParams();
        Function analyzedFunction = getFunction();

        ISolver solver = new SecantSolver(analyzedFunction);

        double start = initialParams.getRange().start;
        double end = initialParams.getRange().end;

        Plot plot = Plot.newPlot()
                .withPlotFileName("lab3_ex1_sec_abs_val_v_b.png")
                .withTitle("Secant method in [0.1 ; 2.1]\\n|f(x^{(i)})| < R\\nStarting from variable point and 2.1")
                .withYRange(0, 40)
                .withXLabel("Variable starting point")
                .withYLabel("Number of iterations")
                .withXRange(0.0, 2.0)
                .build();

        Stopper stopperVal = (x1, x2, fVal, iterNum) -> Math.abs(fVal) < initialParams.getAccuracy();

        for(double precission = 1e-5; precission > 1e-13; precission /= 100) {

            DataSeries series = plot.newDataSeries("R = " + String.format("%2.3e", precission), PlotType.LINESPOINTS);
            List<Point2D> points = new ArrayList<>();

            for(double startPoint = end; startPoint > start; startPoint -= 0.1d) {

                Range range = initialParams.getRange();
                SolveParams p = new SolveParams(range, initialParams.getAccuracy(), initialParams.getMaxIterationCount(), startPoint, range.end);

                Result result = solver.solve(p, stopperVal);
                points.add(new Point2D(startPoint, result.getNumIterations()));
            }
            series.addData(points.toArray(new Point2D[points.size()]));
        }

        plot.plot();
    }

    private static void printResult(SolveParams p, Result result) {

        System.out.println("Result: ");
        System.out.println("\tPrecission: " + p.getAccuracy());
        System.out.println("\tRange: [" + p.getRange().start + " : " + p.getRange().end + "]");
        System.out.println("\tMax iterations: " + p.getMaxIterationCount());

        if (result.isCorrect()) {
            System.out.println("\tStarting point: " + p.getStartPoint());
            System.out.println("\tResult value: " + result.getResult());
            System.out.println("\tLast x diff: " + result.getLastXDiff());
            System.out.println("\tLast function value: " + result.getLastFunctionValue());
            System.out.println("\tNumber of iterations: " + result.getNumIterations());
        } else {
            System.out.println("\tNo correct result: ");
        }
        System.out.println();
    }

    public static SolveParams getParams() {

        properties = getProperties();

        double precission = Double.valueOf(properties.getProperty(PRECISSION_PROPERTY_KEY));
        int maxIterations = Integer.valueOf(properties.getProperty(MAX_ITERATIONS_PROPERTY_KEY));

        double compStart = Double.valueOf(properties.getProperty(COMPARTMENT_START_KEY));
        double compEnd = Double.valueOf(properties.getProperty(COMPARTMENT_END_KEY));

        Range range = new Range(compStart, compEnd);
        return new SolveParams(range, precission, maxIterations, 0.0, 0.0);
    }

    public static Function getFunction() {

        properties = getProperties();

        int m = Integer.valueOf(properties.getProperty(M_KEY));
        int n = Integer.valueOf(properties.getProperty(N_KEY));

        return (x) -> Math.pow(x, n) - Math.pow((1-x), m);
    }

    public static Function getDerivative() {

        properties = getProperties();

        int m = Integer.valueOf(properties.getProperty(M_KEY));
        int n = Integer.valueOf(properties.getProperty(N_KEY));

        return x -> n * Math.pow(x, n-1) + m * Math.pow((1-x), m-1);
    }

    @SneakyThrows
    private static Properties getProperties() {

        if(properties == null) {
            Properties properties = new Properties();
            InputStream inputStream = Main.class.getClassLoader().getResourceAsStream("lab3/lab3.properties");
            properties.load(inputStream);
            return properties;
        }
        return properties;
    }


    private static final String PRECISSION_PROPERTY_KEY = "accuracy";
    private static final String MAX_ITERATIONS_PROPERTY_KEY = "maxIterations";

    private static final String M_KEY = "lab3.param.m";
    private static final String N_KEY = "lab3.param.n";
    private static final String COMPARTMENT_START_KEY = "lab3.param.rangeStart";
    private static final String COMPARTMENT_END_KEY = "lab3.param.rangeEnd";

}
