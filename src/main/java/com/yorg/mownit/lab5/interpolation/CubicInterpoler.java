package com.yorg.mownit.lab5.interpolation;

import com.yorg.mownit.lab1.TriDiagonalSolver;
import com.yorg.mownit.commons.Function;
import org.ejml.simple.SimpleMatrix;

public class CubicInterpoler {

    public Function getClampedSplineInterpolant(double [] xValues, double [] yValues, double derivativeAtStart, double derivativeAtEnd) {

        if(xValues.length != yValues.length) {
            throw new IllegalArgumentException("Sizes of x and y arrays are different");
        }

        int N = xValues.length - 1;

        SimpleMatrix matrixA = new SimpleMatrix(N+1, N+1);
        SimpleMatrix vectorB = new SimpleMatrix(N+1, 1);

        ClampedSplineHelper helper = new ClampedSplineHelper(xValues, yValues);

        for(int i = 1; i < N; ++i) {
            matrixA.set(i, i, 2*(helper.h(i) + helper.h(i-1)));
            matrixA.set(i+1, i, helper.h(i));
            matrixA.set(i, i+1, helper.h(i));
        }

        matrixA.set(0, 0, 2 * helper.h(0));
        matrixA.set(0, 1, helper.h(0));
        matrixA.set(1, 0, helper.h(0));
        matrixA.set(N, N, 2 * helper.h(N-1));

        for(int i = 1; i < N; ++i) {
            vectorB.set(i, 0, 3 * ((yValues[i + 1] - yValues[i]) / helper.h(i) - (yValues[i] - yValues[i - 1]) / helper.h(i - 1)));
        }

        vectorB.set(0, 0, 3 * ((yValues[1] - yValues[0]) / helper.h(0) - derivativeAtStart));
        vectorB.set(N, 0, 3 * (derivativeAtEnd - ((yValues[N] - yValues[N - 1]) / helper.h(N-1))));


        System.out.println(matrixA);
        System.out.println("3 * ((" + yValues[1] + " - " + yValues[0] + ") / " + helper.h(0) + " - " + derivativeAtStart);
        System.out.println(vectorB);

        TriDiagonalSolver solver = new TriDiagonalSolver();
        SimpleMatrix zVector = solver.solve(matrixA, vectorB);

        return (x) -> {
            if(x < xValues[0] || x > xValues[N]) {
                return 0.0;
            }
            double a, b, c, d;

            int i = determineInterval(xValues, x);
            a = yValues[i];
            c = zVector.get(i, 0);
            d = (zVector.get(i+1, 0) - zVector.get(i, 0)) / (3.0 * helper.h(i));
            b = (yValues[i+1] - a) / helper.h(i);
            b -= helper.h(i) / 3.0 * (2 * c + zVector.get(i+1,0));

            return a + b * (x - xValues[i]) + c * Math.pow((x - x-xValues[i]), 2) + d * Math.pow((x - x-xValues[i]), 3);
        };
    }

    public Function getNaturalSplineInterpolant(double [] xValues, double [] yValues) {

        if(xValues.length != yValues.length) {
            throw new IllegalArgumentException("Sizes of x and y arrays are different");
        }

        int N = xValues.length - 1;

        SimpleMatrix matrixA = new SimpleMatrix(N-1, N-1);
        SimpleMatrix b = new SimpleMatrix(N-1, 1);

        NaturalSplineHelper helper = new NaturalSplineHelper(xValues, yValues);

        for(int d = 1; d < N-2; ++d) {
            matrixA.set(d, d, helper.u(d + 1));
            matrixA.set(d, d + 1, helper.h(d + 1));
            matrixA.set(d + 1, d, helper.h(d + 1));
        }
        matrixA.set(0, 0, helper.u(1));
        matrixA.set(0, 1, helper.h(1));
        matrixA.set(1, 0, helper.h(1));
        matrixA.set(N - 2, N - 2, helper.u(N - 1));

        for(int i = 0; i < N-2; ++i) {
            b.set(i, 0, helper.v(i+1));
        }

        TriDiagonalSolver solver = new TriDiagonalSolver();
        SimpleMatrix zVector = solver.solve(matrixA, b);



        return (x) -> {
            if(x < xValues[0] || x > xValues[N]) {
                return 0.0;
            }

            int interval = determineInterval(xValues, x);
            if(interval == -1) {
                return 0.0d;
            } else {
                double zi = interval == 0 || interval == N ? 0.0 : zVector.get(interval-1, 0);
                double zi1 = interval == N-1 ? 0.0 : zVector.get(interval, 0);

                double A = (zi1 - zi)/(6.0 * helper.h(interval));
                double B = zi / 2.0;
                double C = -(helper.h(interval) * zi1 / 6.0) - helper.h(interval) * zi / 3.0 + (yValues[interval+1] - yValues[interval])/helper.h(interval);
                return yValues[interval] + (x-xValues[interval]) * (C + (x - xValues[interval]) * (B + (x - xValues[interval]) * A));
            }
        };
    }


    public int determineInterval(double [] xValues, double x) {

        if(x < xValues[0] || x > xValues[xValues.length-1]) {
            return -1;
        }

        int i = 0;
        while(!(x >= xValues[i] && x <= xValues[i+1])) ++i;

        return i;

    }
}
