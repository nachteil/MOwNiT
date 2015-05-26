package com.yorg.mownit.lab5.interpolation;

import com.yorg.mownit.commons.Point2D;
import com.yorg.mownit.lab1.TriDiagonalSolver;
import com.yorg.mownit.commons.Function;
import com.yorg.mownit.lab1.math.Matrix;
import org.ejml.simple.SimpleMatrix;

import java.util.HashMap;
import java.util.Map;

public class CubicInterpoler {

    public Function clamped(Point2D [] points, double fp0, double fpn) {

        int N = points.length;

        double [] h = new double [N -1];
        double [] dY = new double [N -1];

        for(int i = 0; i < N -1; ++i) {
            h[i] = points[i+1].getX() - points[i].getX();
            dY[i] = points[i+1].getY() - points[i].getY();
        }

        SimpleMatrix A = new SimpleMatrix(N, N);
        SimpleMatrix bMatrix = new SimpleMatrix(N, 1);

        for(int i = 1; i < N-1; ++i) {
            A.set(i, i, 2.0*(h[i]+h[i-1]));
            A.set(i, i+1, h[i]);
            A.set(i+1, i, h[i]);

            bMatrix.set(i, 0, 3.0 * dY[i] / h[i] - 3.0 * dY[i - 1] / h[i - 1]);
        }

        A.set(0, 0, 2*h[0]);
        A.set(0, 1, h[0]);
        A.set(1, 0, h[0]);

        A.set(N-1, N-1, 2.0*h[N-2]);

        bMatrix.set(0, 0, 3.0 * dY[0] / h[0] - 3.0 * fp0);
        bMatrix.set(N - 1, 0, 3.0 * fpn - 3.0 * dY[N - 2] / h[N - 2]);

        SimpleMatrix c = new TriDiagonalSolver().solve(A, bMatrix);

        double [] d = new double [N-1];
        double [] b = new double [N-1];

        for(int i = 0; i < N-1; ++i) {
            d[i] = (c.get(i+1, 0)-c.get(i, 0))/(3.0*h[i]);
            b[i] = dY[i]/h[i] - c.get(i, 0)*h[i] - d[i]*h[i]*h[i];
        }

        Function [] splines = new Function[N-1];
        for(int i = 0; i < N-1; ++i) {
            final int finalI = i;
            double xi = points[finalI].getX();
            splines[i] = x ->
                    points[finalI].getY() + b[finalI]*(x-xi) + c.get(finalI, 0)*Math.pow(x-xi, 2) + d[finalI]*Math.pow(x-xi, 3);
        }

        return x ->
                splines[determineInterval(points, x)].getValue(x);
    }

    public Function natural(Point2D [] points) {
        int N = points.length;

        double [] h = new double [N -1];
        double [] dY = new double [N -1];

        for(int i = 0; i < N -1; ++i) {
            h[i] = points[i+1].getX() - points[i].getX();
            dY[i] = points[i+1].getY() - points[i].getY();
        }

        SimpleMatrix A = new SimpleMatrix(N, N);
        SimpleMatrix bMatrix = new SimpleMatrix(N, 1);

        for(int i = 1; i < N-1; ++i) {
            A.set(i, i, 2.0*(h[i]+h[i-1]));
            A.set(i, i+1, h[i]);
            A.set(i+1, i, h[i]);

            bMatrix.set(i, 0, 3.0 * dY[i] / h[i] - 3.0 * dY[i - 1] / h[i - 1]);
        }

        A.set(0, 0, 2*h[0]);
        A.set(0, 1, h[0]);
        A.set(1, 0, h[0]);

        A.set(N-1, N-1, 2.0*h[N-2]);

        bMatrix.set(0, 0, 0.0);
        bMatrix.set(N - 1, 0, 0.0);


        SimpleMatrix c = new TriDiagonalSolver().solve(A, bMatrix);

        double [] d = new double [N-1];
        double [] b = new double [N-1];

        for(int i = 0; i < N-1; ++i) {
            d[i] = (c.get(i+1, 0)-c.get(i, 0))/(3.0*h[i]);
            b[i] = dY[i]/h[i] - c.get(i, 0)*h[i] - d[i]*h[i]*h[i];
        }

        Function [] splines = new Function[N-1];
        for(int i = 0; i < N-1; ++i) {
            final int finalI = i;
            double xi = points[finalI].getX();
            splines[i] = x ->
                    points[finalI].getY() + b[finalI]*(x-xi) + c.get(finalI, 0)*Math.pow(x-xi, 2) + d[finalI]*Math.pow(x-xi, 3);
        }

        return x ->
                splines[determineInterval(points, x)].getValue(x);
    }

    public Function getClampedSplineInterpolant(Point2D [] points, double derivativeAtStart, double derivativeAtEnd) {

        int N = points.length - 1;

        SimpleMatrix matrixA = new SimpleMatrix(N+1, N+1);
        SimpleMatrix vectorB = new SimpleMatrix(N+1, 1);

        ClampedSplineHelper helper = new ClampedSplineHelper(points);

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
            vectorB.set(i, 0, 3 * ((points[i + 1].getY() - points[i].getY()) / helper.h(i) - (points[i].getY() - points[i - 1].getY()) / helper.h(i - 1)));
        }

        vectorB.set(0, 0, 3 * ((points[1].getY() - points[0].getY()) / helper.h(0) - derivativeAtStart));
        vectorB.set(N, 0, 3 * (derivativeAtEnd - ((points[N].getY() - points[N - 1].getY()) / helper.h(N-1))));

        TriDiagonalSolver solver = new TriDiagonalSolver();
        SimpleMatrix zVector = solver.solve(matrixA, vectorB);

        return (x) -> {
            if(x < points[0].getX() || x > points[N].getX()) {
                return 0.0;
            }
            double a, b, c, d;

            int i = determineInterval(points, x);
            a = points[i].getY();
            c = zVector.get(i, 0);
            d = (zVector.get(i+1, 0) - zVector.get(i, 0)) / (3.0 * helper.h(i));
            b = (points[i+1].getY() - a) / helper.h(i);
            b -= helper.h(i) / 3.0 * (2 * c + zVector.get(i+1,0));

            return a + b * (x - points[i].getX()) + c * Math.pow((x - x-points[i].getX()), 2) + d * Math.pow((x - x-points[i].getX()), 3);
        };
    }

    public Function getNaturalSplineInterpolant(Point2D [] points) {

        int N = points.length-1;

        SimpleMatrix matrixA = new SimpleMatrix(N-1, N-1);
        SimpleMatrix b = new SimpleMatrix(N-1, 1);

        NaturalSplineHelper helper = new NaturalSplineHelper(points);

        for(int d = 0; d < N-2; ++d) {
            matrixA.set(d, d, helper.u(d + 1));
            matrixA.set(d, d + 1, helper.h(d + 1));
            matrixA.set(d + 1, d, helper.h(d + 1));
        }
        matrixA.set(N - 2, N - 2, helper.u(N - 1));

        for(int i = 0; i < N-2; ++i) {
            b.set(i, 0, helper.v(i+1));
        }

        TriDiagonalSolver solver = new TriDiagonalSolver();
        SimpleMatrix zVector = solver.solve(matrixA, b);

        return (x) -> {
            if(x < points[0].getX() || x > points[N].getX()) {
                return 0.0;
            }

            int interval = determineInterval(points, x);
            if(interval == -1) {
                return 0.0d;
            } else {
                double zi = interval == 0 ? 0.0 : zVector.get(interval-1, 0);
                double zi1 = interval == N-1 ? 0.0 : zVector.get(interval, 0);

                double A = (zi1 - zi)/(6.0 * helper.h(interval));
                double B = zi / 2.0;
                double C = -(helper.h(interval) * zi1 / 6.0) - helper.h(interval) * zi / 3.0 + (points[interval+1].getY() - points[interval].getY())/helper.h(interval);
                return points[interval].getY() + (x-points[interval].getX()) * (C + (x - points[interval].getX()) * (B + (x - points[interval].getX()) * A));
            }
        };
    }

    public Map<Integer, Integer> cnt = new HashMap<>();

    public int determineInterval(Point2D [] points, double x) {

        if(x < points[0].getX()) {
            return 0;
        }
        if(x > points[points.length-1].getX()) {
            return points.length-1;
        }

        int i = 0;
        while(!(x >= points[i].getX() && x <= points[i+1].getX())) ++i;

        if(cnt.get(i) == null) cnt.put(i, 0);
        cnt.put(i, cnt.get(i)+1);

        return i;
    }
}
