package com.yorg.mownit.lab2;

import com.yorg.mownit.lab2.math.JacobiSolver;
import com.yorg.mownit.lab2.math.MatrixUtils;
import com.yorg.mownit.lab2.math.VectorUtils;
import org.ejml.simple.SimpleMatrix;

/**
 * Created by yorg on 25.03.15.
 */
public class Lab2Runner {

    // TODO: czy warunek zbieznosci jest konieczny, czy wystarczający?
    // policzyc promien spektralny

    private static final int k = 8;
    private static final int m = 2;

    public static void main(String[] args) {

        double stopLimit = 0.001d;

        StopCritter stop = new EuclidianNormBetweenIterationsCritter(stopLimit);
        conductExperiment(30, stopLimit, stop);

    }

    private static void conductExperiment(int N, double r, StopCritter stop) {

        System.out.println("STARTING EXPERIMENT\n");
        System.out.println("Stop criteria: " + stop.getDescription());
        System.out.println("Problem size N: " + N);

        Lab2 lab2 = new Lab2(MatrixElementType.A);

        SimpleMatrix A = lab2.getMatrix(N, m, k);
        SimpleMatrix x = VectorUtils.getRandomVactor(N);

        SimpleMatrix b = A.mult(x);

        JacobiSolver solver = new JacobiSolver(A, b);

        System.out.println("\nMatrix A:\n");
        System.out.println(A);

        System.out.println("\nOriginal vector x:\n");
        System.out.println(x);

        System.out.println("\nVector b:\n");
        System.out.println(b);

        System.out.println("Should it be convergent based on A? " + MatrixUtils.isJacobiConvergent(A));
        System.out.println();

        Experiment experiment = new Experiment(solver, stop);
        experiment.start();

        if(experiment.getResult() != null) {
            System.out.println("Result: ");
            System.out.printf("Difference dropped below %f after %d iterations\n", r, experiment.getIterationCount());
            System.out.println("Total time: " + experiment.getTotalDuration() + " ms");
            System.out.println("Simulation time: " + experiment.getSimulationDuration() + " ms");
            System.out.println(experiment.getResult());
        } else {
            System.out.println("Experiment failed - the result was not convergent after " + Experiment.MAX_ITERATIONS + " iterations");
        }

    }
}
