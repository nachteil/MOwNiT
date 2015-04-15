package com.yorg.mownit.lab1;

import com.yorg.mownit.lab1.math.DoubleMatrix;
import com.yorg.mownit.lab1.math.FloatMatrix;
import org.ejml.simple.SimpleMatrix;

public class TriDiagonalSolver {

    public FloatMatrix solve(FloatMatrix A, FloatMatrix b) {

        int n = A.getNumCols();
        float [] u = new float [n];
        float [] l = new float [n];

        u[0] = A.get(0, 0);

        for(int i = 1; i < n; ++i) {
            l[i] = A.get(i, i-1) / u[i-1];
            u[i] = A.get(i, i) - l[i] * A.get(i-1, i);
        }

        float [] y = new float [n];
        float [] x = new float [n];

        y[0] = b.get(0, 0);
        for(int i = 1; i < n; ++i) {
            y[i] = b.get(i, 0) - l[i] * y[i-1];
        }

        x[n-1] =  y[n-1] / u[n-1];
        FloatMatrix result = new FloatMatrix(n, 1);
        result.set(n-1, 0, x[n-1]);
        for(int i = n-2; i >= 0; --i) {
            x[i] = (y[i] - A.get(i, i+1) * x[i+1]) / u[i];
            result.set(i, 0, x[i]);
        }
        return result;
    }

    private SimpleMatrix generalSolve(MatrixValue A, MatrixValue b) {

        int n = A.getNumCols();
        double [] u = new double [n];
        double [] l = new double [n];

        u[0] = A.get(0, 0);

        for(int i = 1; i < n; ++i) {
            l[i] = A.get(i, i-1) / u[i-1];
            u[i] = A.get(i, i) - l[i] * A.get(i-1, i);
        }

        double [] y = new double [n];
        double [] x = new double [n];

        y[0] = b.get(0, 0);
        for(int i = 1; i < n; ++i) {
            y[i] = b.get(i, 0) - l[i] * y[i-1];
        }

        x[n-1] =  y[n-1] / u[n-1];
        SimpleMatrix delegate = new SimpleMatrix(n, 1);
        delegate.set(n-1, 0, x[n-1]);
        for(int i = n-2; i >= 0; --i) {
            x[i] = (y[i] - A.get(i, i+1) * x[i+1]) / u[i];
            delegate.set(i, 0, x[i]);
        }
        return delegate;
    }

    public DoubleMatrix solve(DoubleMatrix A, DoubleMatrix b) {

        DoubleMatrix result = new DoubleMatrix(A.getNumRows(), 1);
        MatrixValue aFacade = new MatrixValue() {
            @Override
            public double get(int row, int col) {
                return A.get(row, col);
            }

            @Override
            public int getNumCols() {
                return A.getNumCols();
            }
        };

        MatrixValue bFacade = new MatrixValue() {
            @Override
            public double get(int row, int col) {
                return b.get(row, col);
            }

            @Override
            public int getNumCols() {
                return b.getNumCols();
            }
        };

        SimpleMatrix solved = generalSolve(aFacade, bFacade);

        for(int i = 0; i < A.getNumRows(); ++i) {
            result.set(i, 0, solved.get(i, 0));
        }

        return result;
    }

    public SimpleMatrix solve(SimpleMatrix A, SimpleMatrix b) {

        MatrixValue aFacade = new MatrixValue() {
            @Override
            public double get(int row, int col) {
                return A.get(row, col);
            }

            @Override
            public int getNumCols() {
                return A.numCols();
            }
        };

        MatrixValue bFacade = new MatrixValue() {
            @Override
            public double get(int row, int col) {
                return b.get(row, col);
            }

            @Override
            public int getNumCols() {
                return b.numCols();
            }
        };

        return generalSolve(aFacade, bFacade);
    }

    public static interface MatrixValue {
        double get(int row, int col);
        int getNumCols();
    }

}
