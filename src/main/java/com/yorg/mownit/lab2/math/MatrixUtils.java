package com.yorg.mownit.lab2.math;

import org.ejml.simple.SimpleEVD;
import org.ejml.simple.SimpleMatrix;

public class MatrixUtils {

    public static boolean isJacobiConvergent(SimpleMatrix A) {

        int limit = Math.min(A.numCols(), A.numRows());

        for(int i = 0; i < limit; ++i) {
            double a_ii = A.get(i, i);
            double sum = 0.0d;
            for(int j = 0; j < A.numCols(); ++j ) {
                if(i != j) {
                    sum += Math.abs(A.get(i, j));
                }
            }
            if(Math.abs(a_ii) <= sum) {
                return false;
            }
        }
        return true;
    }

    public static double getIterationMatrixSpectralRadius(SimpleMatrix A) {
        SimpleMatrix L = initL(A);
        SimpleMatrix D = initD(A);
        SimpleMatrix U = initU(A);
        SimpleMatrix iterationMatrix = D.invert().mult(L.plus(U)).scale(-1.0d);
        SimpleEVD eig = iterationMatrix.eig();
        double result = -1;
        for(int i = 0; i < eig.getNumberOfEigenvalues(); ++i) {
            result = Math.max(result, Math.abs(eig.getEigenvalue(i).getReal()));
        }
        return result;
    }

    private static SimpleMatrix initD(SimpleMatrix A) {
        SimpleMatrix D = new SimpleMatrix(A.numRows(), A.numCols());
        for(int row = 0; row < A.numRows(); ++row) {
            for(int col = 0; col < A.numCols(); ++col) {
                if(row == col) {
                    D.set(row, col, A.get(row, col));
                } else {
                    D.set(row, col, 0.0d);
                }
            }
        }
        return D;
    }

    private static SimpleMatrix initL(SimpleMatrix A) {
        SimpleMatrix L = new SimpleMatrix(A.numRows(), A.numCols());
        for(int row = 0; row < A.numRows(); ++row) {
            for(int col = 0; col < A.numCols(); ++col) {
                if(row > col) {
                    L.set(row, col, A.get(row, col));
                } else {
                    L.set(row, col, 0.0d);
                }
            }
        }
        return L;
    }

    private static SimpleMatrix initU(SimpleMatrix A) {
        SimpleMatrix U = new SimpleMatrix(A.numRows(), A.numCols());
        for(int row = 0; row < A.numRows(); ++row) {
            for(int col = 0; col < A.numCols(); ++col) {
                if(row < col) {
                    U.set(row, col, A.get(row, col));
                } else {
                    U.set(row, col, 0.0d);
                }
            }
        }
        return U;
    }
}
