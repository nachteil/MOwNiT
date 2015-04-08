package com.yorg.mownit.lab3;

import com.yorg.mownit.lab3.math.Function;
import com.yorg.mownit.lab3.math.Polynomial;
import com.yorg.mownit.lab3.solvers.AbstractSolver;
import com.yorg.mownit.lab3.solvers.ISolver;
import com.yorg.mownit.lab3.solvers.SecantSolver;
import com.yorg.mownit.lab3.utils.Compartment;
import com.yorg.mownit.lab3.utils.Result;
import com.yorg.mownit.lab3.utils.SolveParams;
import com.yorg.mownit.lab3.utils.Stopper;
import lombok.SneakyThrows;

import java.io.InputStream;
import java.util.DoubleSummaryStatistics;
import java.util.Properties;

public class Main {

    private static final String PRECISSION_PROPERTY_KEY = "accuracy";
    private static final String MAX_ITERATIONS_PROPERTY_KEY = "maxIterations";

    private static final String M_KEY = "lab3.param.m";
    private static final String N_KEY = "lab3.param.n";
    private static final String COMPARTMENT_START_KEY = "lab3.param.compartmentStart";
    private static final String COMPARTMENT_END_KEY = "lab3.param.compartmentEnd";

    private static Properties properties = null;

    public static void main(String ... args) {

        performNewtonMethod();

    }

    public static void performNewtonMethod() {

        SolveParams initialParams = getParams();

        Function<Double> analyzedFunction = getFunction();
        ISolver solver = new SecantSolver(analyzedFunction);

        Stopper stopperPrecission = (x1, x2, fVal, iterNum) -> Math.abs(fVal) < initialParams.getAccuracy();
        Stopper stopperIterations = (x1, x2, fVal, iterNum) -> iterNum < initialParams.getMaxIterationCount();

        double start = initialParams.getCompartment().getStart();
        double end = initialParams.getCompartment().getEnd();

        for(double new_end = end; new_end > start; new_end -= 0.1d) {

            Compartment compartment = new Compartment(start, new_end);
            SolveParams p = new SolveParams(compartment, initialParams.getAccuracy(), initialParams.getMaxIterationCount());

            Result result = solver.solve(p, stopperPrecission);
            printResult(p, result);
        }
    }

    private static void printResult(SolveParams p, Result result) {

        System.out.println("Result: ");
        System.out.println("Precission: " + p.getAccuracy());
        System.out.println("Compartment: [" + p.getCompartment().getStart() + " : " + p.getCompartment().getEnd() + "]");
        System.out.println("Max iterations: " + p.getMaxIterationCount());

        if (result.isCorrect()) {
            System.out.println(result.getResult());
            System.out.println("Last x diff: " + result.getLastXDiff());
            System.out.println("Last function value: " + result.getLastFunctionValue());
            System.out.println("Number of iterations: " + result.getNumIterations());
        } else {
            System.out.println("No correct result: ");
        }
        System.out.println();
    }

    public static SolveParams getParams() {

        properties = getProperties();

        double precission = Double.valueOf(properties.getProperty(PRECISSION_PROPERTY_KEY));
        int maxIterations = Integer.valueOf(properties.getProperty(MAX_ITERATIONS_PROPERTY_KEY));

        double compStart = Double.valueOf(properties.getProperty(COMPARTMENT_START_KEY));
        double compEnd = Double.valueOf(properties.getProperty(COMPARTMENT_END_KEY));

        Compartment compartment = new Compartment(compStart, compEnd);
        return new SolveParams(compartment, precission, maxIterations);
    }

    public static Function<Double> getFunction() {

        properties = getProperties();

        int m = Integer.valueOf(properties.getProperty(M_KEY));
        int n = Integer.valueOf(properties.getProperty(N_KEY));

        return (x) -> Math.pow(x, n) - Math.pow((1-x), m);
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

}
