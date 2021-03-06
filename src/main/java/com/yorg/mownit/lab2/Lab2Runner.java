package com.yorg.mownit.lab2;

import com.yorg.mownit.lab2.math.MatrixUtils;
import com.yorg.mownit.commons.VectorUtils;
import com.yorg.mownit.lab2.solvers.JacobiSolver;
import com.yorg.mownit.lab2.solvers.SORSolver;
import com.yorg.mownit.lab2.solvers.Solver;
import com.yorg.mownit.lab2.utils.EuclidianNormBetweenIterationsCritter;
import com.yorg.mownit.lab2.utils.EuclidianResultDeviation;
import com.yorg.mownit.lab2.utils.StopCritter;
import lombok.SneakyThrows;
import org.ejml.simple.SimpleMatrix;

import javax.annotation.processing.SupportedSourceVersion;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class Lab2Runner {

    private static final int k = 8;
    private static final int m = 2;

    private static int num;
    private static int count = 0;

    private static synchronized int getCount() {return ++count;}
    private static synchronized int getNum() {return num;}

    private static PrintStream csvLog;
    private synchronized static void logToCSV(Object ... obs) {
        StringBuilder b = new StringBuilder();
        for(Object o : obs) {
            b.append(o).append(",");
        }
        b.setLength(b.length()-1);
        csvLog.println(b.toString());
    }

    @SneakyThrows
    public static void main(String[] args) {
        runConcurrentlyTwoMethodsComparison();
    }

    private static void calculateRadius() throws FileNotFoundException {
        Lab2 lab = new Lab2(MatrixElementType.A);
        PrintStream print = new PrintStream(new FileOutputStream("radius.csv"));
        for(int N = 5; N < 1000; N += 10) {
            print.println(N + "," + MatrixUtils.getIterationMatrixSpectralRadius(lab.getMatrix(N, m, k)));
        }
    }

    @SneakyThrows
    private static void runConcurrentlyTwoMethodsComparison() {

        // diff between iterations
        csvLog = new PrintStream(new FileOutputStream("sor_iterations.csv"));

        PrintStream oldOut = System.out;
        System.setOut(new PrintStream(new FileOutputStream("comparison_new.txt")));

        ExecutorService executor = Executors.newFixedThreadPool(5);
        List<Runnable> runnables = new ArrayList<>();

        for(int N = 5; N < 1000; N *= 2) {
            for(double precission = 1e-3; precission > 1e-10; precission /= 100) {
                final int _N = N;
                final double _precission = precission;
                Runnable r = () -> {
                    StopCritter stop = new EuclidianNormBetweenIterationsCritter(_precission);
                    conductSORComparison(_N, _precission, stop);
                    oldOut.println("Finished " + getCount() + " of " + getNum());
                };
                runnables.add(r);
            }
        }

        num = runnables.size();
        for(Runnable r : runnables) {
            executor.execute(r);
        }
        executor.shutdown();
        executor.awaitTermination(1500, TimeUnit.SECONDS);

        // result diff
        csvLog = new PrintStream(new FileOutputStream("sor_result_diff.csv"));

        System.setOut(new PrintStream(new FileOutputStream("comparison_new.txt")));

        executor = Executors.newFixedThreadPool(5);
        runnables = new ArrayList<>();

        for(int N = 5; N < 1000; N *= 2) {
            for(double precission = 1e-3; precission > 1e-10; precission /= 100) {
                final int _N = N;
                final double _precission = precission;
                Runnable r = () -> {
                    StopCritter stop = new EuclidianResultDeviation(_precission);
                    conductSORComparison(_N, _precission, stop);
                    oldOut.println("Finished " + getCount() + " of " + getNum());
                };
                runnables.add(r);
            }
        }

        num = runnables.size();
        for(Runnable r : runnables) {
            executor.execute(r);
        }
        executor.shutdown();
        executor.awaitTermination(1500, TimeUnit.SECONDS);
    }

    @SneakyThrows
    private static void runJacobiPassages() {

        PrintStream old = System.out;
        PrintStream outStream = new PrintStream(new FileOutputStream("jacobi_iteration_step.csv"));
        System.setOut(outStream);

        for(int N = 5; N < 1000; N += 10) {
            for(double precission = 1e-3; precission > 1e-10; precission /= 100) {
                StopCritter stop = new EuclidianNormBetweenIterationsCritter(precission);
                conductExperiment(N, precission, stop);
            }
            old.println(N);
        }

        outStream = new PrintStream(new FileOutputStream("jacobi_result_diff.csv"));
        System.setOut(outStream);

        for(int N = 5; N < 1000; N += 10) {
            for(double precission = 1e-3; precission > 1e-10; precission /= 100) {
                StopCritter stop = new EuclidianResultDeviation(precission);
                conductExperiment(N, precission, stop);
            }
            old.println(N);
        }
    }

    private static void conductExperiment(int N, double r, StopCritter stop) {

        Lab2 lab2 = new Lab2(MatrixElementType.A);

        SimpleMatrix A = lab2.getMatrix(N, m, k);
        SimpleMatrix x = VectorUtils.getRandomVactor(N);

        SimpleMatrix b = A.mult(x);

        Solver solver = new JacobiSolver(A, b);

        Experiment experiment = new Experiment(solver, stop);
        experiment.start();

        if(experiment.getResult() != null) {
            double distance = VectorUtils.euclidianNorm(experiment.getResult().minus(x));
            System.out.println(N + "," + r + "," + experiment.getIterationCount() + "," + distance);
        } else {
            System.out.println("Experiment failed - the result was not convergent after " + Experiment.MAX_ITERATIONS + " iterations");
        }

    }

    private static void conductSORComparison (int N, double r, StopCritter stop) {

        StringBuilder builder = new StringBuilder();

        builder.append("\nSTARTING EXPERIMENT\n");
        builder.append("\nStop criteria: " + stop.getDescription());
        builder.append("\nProblem size N: " + N);

        Lab2 lab2 = new Lab2(MatrixElementType.A);

        SimpleMatrix A = lab2.getMatrix(N, m, k);
        SimpleMatrix x = VectorUtils.getRandomVactor(N);

        SimpleMatrix b = A.mult(x);

        Solver jacobiSolver = new JacobiSolver(A, b);

        Experiment jacobiSolverExperiment = new Experiment(jacobiSolver, stop);

        jacobiSolverExperiment.start();

        if(jacobiSolverExperiment.getResult() != null) {
            builder.append("\nJacobi result: ");
            builder.append(String.format("Difference dropped below %e after %d iterations\n", r, jacobiSolverExperiment.getIterationCount()));
            builder.append("\nTotal time: " + jacobiSolverExperiment.getTotalDuration() + " ms");
            builder.append("\nSimulation time: " + jacobiSolverExperiment.getSimulationDuration() + " ms");

        } else {
            builder.append("\nExperiment failed - the result was not convergent after " + Experiment.MAX_ITERATIONS + " iterations");
        }

        builder.append("\nSor result: ");
        for(double omega = 0.1; omega < 2.0; omega += 0.1) {

            Solver sorSolver = new SORSolver(A, b, omega);
            Experiment sorSolverExperiment = new Experiment(sorSolver, stop);
            sorSolverExperiment.start();

            if (sorSolverExperiment.getResult() != null) {
                builder.append("\n   omega = " + omega + " -> " + sorSolverExperiment.getIterationCount() + " iterations");
                logToCSV(N, r, omega, sorSolverExperiment.getIterationCount(), jacobiSolverExperiment.getIterationCount());
            } else {
                builder.append("\nExperiment failed - the result was not convergent after " + Experiment.MAX_ITERATIONS + " iterations");
            }
        }

        builder.append("\n- = - = - = - = - = - = - = - = - = - = - = - = - = - = - = - = - = - = - = - =");
        System.out.println(builder.toString());
    }
}
