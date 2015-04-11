package com.yorg.mownit.lab1;

import com.yorg.mownit.commons.CSVLogger;
import com.yorg.mownit.commons.ConditionNumber;
import com.yorg.mownit.commons.MatrixElementFormula;
import com.yorg.mownit.lab1.math.DoubleMatrix;
import com.yorg.mownit.lab1.math.FloatMatrix;
import com.yorg.mownit.lab1.math.Matrix;
import com.yorg.mownit.lab1.util.Exercise;
import com.yorg.mownit.lab1.util.Lab1;
import com.yorg.mownit.lab1.util.MatrixUtils;

import java.lang.management.ManagementFactory;
import java.lang.management.ThreadMXBean;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class Lab1Runner {

    private CSVLogger floatLogger;
    private CSVLogger doubleLogger;

    public void runExercise(Exercise exercise) throws InterruptedException {

        initLoggers(exercise);
        ExecutorService executor = Executors.newFixedThreadPool(5);

        for(int n = 1; n < 1000; ++n) {
            Runnable r = getRunnable(n, exercise);
            executor.execute(r);
        }

        executor.shutdown();
        executor.awaitTermination(1000, TimeUnit.SECONDS);
    }

    private void initLoggers(Exercise exercise) {

        String floatLogFileName = "lab1_" + exercise.getDescription() + "_results_float.csv";
        String doubleLogFileName = "lab1_" + exercise.getDescription() + "_results_double.csv";

        floatLogger = new CSVLogger(floatLogFileName);
        floatLogger.logLine("#N", "type", "distance");

        doubleLogger = new CSVLogger(doubleLogFileName);
        doubleLogger.logLine("#N", "type", "distance");
    }

    private Runnable getRunnable(int N, Exercise exercise) {

        return () -> {

            FloatMatrix floatMatrixA = exercise.getFloatMatrix(N);

            FloatMatrix floatXVector = Lab1.getRandomFloatVector(N);
            FloatMatrix floatBVector = FloatMatrix.multiply(floatMatrixA, floatXVector);

            DoubleMatrix doubleMatrixA = exercise.getDoubleMatrix(N);
            DoubleMatrix doubleXVector = Lab1.upgradeFloatVectorToDouble(floatXVector);
            DoubleMatrix doubleBVector = DoubleMatrix.multiply(doubleMatrixA, doubleXVector);

            FloatMatrix extendedFloatMatrix = MatrixUtils.getExtendedFloatMatrix(floatMatrixA, floatBVector);
            extendedFloatMatrix.performGaussianElimination();

            DoubleMatrix extendedDoubleMatrix = MatrixUtils.getExtendedDoubleMatrix(doubleMatrixA, doubleBVector);
            extendedDoubleMatrix.performGaussianElimination();

            float floatDistance = MatrixUtils.getVectorDistance(floatXVector, extendedFloatMatrix);
            double doubleDistance = MatrixUtils.getVectorDistance(doubleXVector, extendedDoubleMatrix);

            floatLogger.logLine(N, Type.FLOAT.toString(), floatDistance);
            doubleLogger.logLine(N, Type.DOUBLE.toString(), doubleDistance);

            System.out.println(N);
        };
    }

    public Runnable getRunnableForCondition(int N, Exercise exercise) {

        return () -> {

            MatrixElementFormula firstMatrixFormula = exercise::getDoubleMatrixElement;
            double floatConditionNumber = ConditionNumber.getConditionNumber(N, firstMatrixFormula);

            floatLogger.logLine(N, "NONE", floatConditionNumber);
            System.out.println(N);
        };

    }

    public void calculateConditionNumber(Exercise exercise) throws InterruptedException {

        floatLogger = new CSVLogger("lab1_condition_number_" + exercise.getDescription() + ".csv");
        floatLogger.logLine("#N", "type", "condition");

        ExecutorService executor = Executors.newFixedThreadPool(5);

        for(int n = 1; n < 1000; ++n) {
            Runnable r = getRunnableForCondition(n, exercise);
            executor.execute(r);
        }

        executor.shutdown();
        executor.awaitTermination(1000, TimeUnit.SECONDS);

        floatLogger = new CSVLogger("lab1_condition_number_" + exercise.getDescription() + ".csv");
        floatLogger.logLine("#N", "type", "condition");

        executor.shutdown();
        executor.awaitTermination(1000, TimeUnit.SECONDS);

        executor = Executors.newFixedThreadPool(5);

        for(int n = 1; n < 1000; ++n) {
            Runnable r = getRunnableForCondition(n, exercise);
            executor.execute(r);
        }

        executor.shutdown();
        executor.awaitTermination(1000, TimeUnit.SECONDS);

    }

    private Runnable getThirdExerciseRunnableGaussian(int n) {

        return () -> {

            FloatMatrix floatA = Lab1.exerciseThree().getFloatMatrix(n);
            FloatMatrix floatX = Lab1.getRandomFloatVector(n);
            FloatMatrix floatB = FloatMatrix.multiply(floatA, floatX);
            FloatMatrix extendedMatrix = MatrixUtils.getExtendedFloatMatrix(floatA, floatB);

            DoubleMatrix doubleA = Lab1.exerciseThree().getDoubleMatrix(n);
            DoubleMatrix doubleX = Lab1.upgradeFloatVectorToDouble(floatX);
            DoubleMatrix doubleB = DoubleMatrix.multiply(doubleA, doubleX);
            DoubleMatrix extendedDoubleMatrix = MatrixUtils.getExtendedDoubleMatrix(doubleA, doubleB);

            long floatDuration, doubleDuration;
            long startCPUTime;

            ThreadMXBean bean = ManagementFactory.getThreadMXBean();

            startCPUTime = bean.getCurrentThreadCpuTime();
            extendedMatrix.performGaussianElimination();
            floatDuration = (bean.getCurrentThreadCpuTime() - startCPUTime)/1000;

            startCPUTime = bean.getCurrentThreadCpuTime();
            extendedDoubleMatrix.performGaussianElimination();
            doubleDuration = (bean.getCurrentThreadCpuTime() - startCPUTime)/1000;

            double floatDistance = MatrixUtils.getVectorDistance(floatX, extendedMatrix);
            double doubleDistance = MatrixUtils.getVectorDistance(doubleX, extendedDoubleMatrix);

            floatLogger.logLine(n, "FLOAT", floatDistance, floatDuration);
            doubleLogger.logLine(n, "DOUBLE", doubleDistance, doubleDuration);

            System.out.println(n);
        };
    }

    private Runnable getThirdExerciseRunnableTriDiag(int n) {

        return () -> {

            FloatMatrix floatA = Lab1.exerciseThree().getFloatMatrix(n);
            FloatMatrix floatX = Lab1.getRandomFloatVector(n);
            FloatMatrix floatB = FloatMatrix.multiply(floatA, floatX);

            DoubleMatrix doubleA = Lab1.exerciseThree().getDoubleMatrix(n);
            DoubleMatrix doubleX = Lab1.upgradeFloatVectorToDouble(floatX);
            DoubleMatrix doubleB = DoubleMatrix.multiply(doubleA, doubleX);

            long floatDuration, doubleDuration;
            long startCPUTime;
            ThreadMXBean bean = ManagementFactory.getThreadMXBean();


            startCPUTime = bean.getCurrentThreadCpuTime();
            FloatMatrix floatResult = new TriDiagonalSolver().solve(floatA, floatB);
            floatDuration = (bean.getCurrentThreadCpuTime() - startCPUTime)/1000;

            startCPUTime = bean.getCurrentThreadCpuTime();
            DoubleMatrix doubleResult = new TriDiagonalSolver().solve(doubleA, doubleB);
            doubleDuration = (bean.getCurrentThreadCpuTime() - startCPUTime)/1000;

            double floatDistance = 0.0f;
            double doubleDistance = 0.0d;

            for(int i = 0; i < n; ++i) {
                floatDistance += Math.pow(floatResult.get(i, 0) - floatX.get(i, 0),2);
                doubleDistance += Math.pow(doubleResult.get(i, 0) - doubleX.get(i, 0),2);
            }

            floatDistance = Math.sqrt(floatDistance);
            doubleDistance = Math.sqrt(doubleDistance);

            floatLogger.logLine(n, "FLOAT", floatDistance, floatDuration);
            doubleLogger.logLine(n, "DOUBLE", doubleDistance, doubleDuration);

            System.out.println(n);
        };
    }

    public void runThirdExerciseTriDiag() throws InterruptedException {

        // TriDiag here
        floatLogger = new CSVLogger("lab1_ex3_float_diag.csv");
        floatLogger.logLine("#N", "type", "distance", "time");

        doubleLogger = new CSVLogger("lab1_ex3_double_diag.csv");
        doubleLogger.logLine("#N", "type", "distance", "time");

        ExecutorService executor = Executors.newFixedThreadPool(5);

        for(int i = 1; i < 1000; ++i) {
            executor.execute(getThirdExerciseRunnableTriDiag(i));
        }

        executor.shutdown();
        executor.awaitTermination(1000, TimeUnit.SECONDS);
    }
    public void runThirdExerciseGauss() throws InterruptedException {

        String floatLogFileName = "lab1_ex3_float.csv";
        String doubleLogFileName = "lab1_ex3_double.csv";

        floatLogger = new CSVLogger(floatLogFileName);
        floatLogger.logLine("#N", "type", "distance", "time");

        doubleLogger = new CSVLogger(doubleLogFileName);
        doubleLogger.logLine("#N", "type", "distance", "time");

        ExecutorService executor = Executors.newFixedThreadPool(5);

        for(int i = 1; i < 1000; ++i) {
            executor.execute(getThirdExerciseRunnableGaussian(i));
        }

        executor.shutdown();
        executor.awaitTermination(1000, TimeUnit.SECONDS);

    }

    public void printResults(FloatMatrix a, FloatMatrix x, FloatMatrix b, FloatMatrix extendedMatrix, float distance) {
        System.out.println("A:");
        System.out.println(a);
        System.out.println("x:");
        System.out.println(x);
        System.out.println("b:");
        System.out.println(b);
        System.out.println("Reduced echelon:");
        System.out.println(extendedMatrix);
        System.out.println("Vectors distance: " + distance);
    }

    private static enum Type {
        DOUBLE, FLOAT
    }

}
