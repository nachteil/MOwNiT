package com.yorg.mownit.lab2;

import org.ejml.simple.SimpleMatrix;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yorg on 25.03.15.
 */
public class JacobiSolver {

    private SimpleMatrix A;
    private SimpleMatrix b;

    private SimpleMatrix M;
    private SimpleMatrix N;
    private SimpleMatrix L;
    private SimpleMatrix D;
    private SimpleMatrix U;

    List<SimpleMatrix> cachedResults = new ArrayList<>(50);

    public JacobiSolver(SimpleMatrix A, SimpleMatrix b) {

        this.A = A;
        this.b = b;

        L = new SimpleMatrix(A.numRows(), A.numCols());
        D = new SimpleMatrix(A.numRows(), A.numCols());
        U = new SimpleMatrix(A.numRows(), A.numCols());

        initL();
        initD();
        initU();

        N = D.invert();
        M = D.invert().mult(L.plus(U)).scale(-1.0d);

        insertResultZeroBase();
    }

    private void insertResultZeroBase() {

        SimpleMatrix x = new SimpleMatrix(A.numCols(), 1);

        for(int i = 0; i < x.numRows(); ++i) {
            x.set(i, 0, 0.0d);
        }
        cachedResults.add(x);
    }

    public SimpleMatrix getResult(int numberOfIteration) {

        if(numberOfIteration < 1) {
            throw new IllegalArgumentException("Number of iterations must be greater than 0");
        }

        if(numberOfIteration > cachedResults.size() - 1) {
            for(int i = cachedResults.size(); i <= numberOfIteration; ++i) {
                SimpleMatrix previous = cachedResults.get(i - 1);
                SimpleMatrix current = M.mult(previous).plus(N.mult(b));
                cachedResults.add(current);
            }
        }
        return cachedResults.get(numberOfIteration);
    }

    private void initD() {
        for(int row = 0; row < A.numRows(); ++row) {
            for(int col = 0; col < A.numCols(); ++col) {
                if(row == col) {
                    D.set(row, col, A.get(row, col));
                } else {
                    D.set(row, col, 0.0d);
                }
            }
        }
    }

    private void initL() {
        for(int row = 0; row < A.numRows(); ++row) {
            for(int col = 0; col < A.numCols(); ++col) {
                if(row > col) {
                    L.set(row, col, A.get(row, col));
                } else {
                    L.set(row, col, 0.0d);
                }
            }
        }
    }

    private void initU() {
        for(int row = 0; row < A.numRows(); ++row) {
            for(int col = 0; col < A.numCols(); ++col) {
                if(row < col) {
                    U.set(row, col, A.get(row, col));
                } else {
                    U.set(row, col, 0.0d);
                }
            }
        }
    }

    public boolean isJacobiConvergent() {

        int limit = Math.min(A.numCols(), A.numRows());

        for(int i = 0; i < limit; ++i) {
            double a_ii = A.get(i, i);
            double sum = 0.0d;
            for(int j = 0; j < A.numCols(); ++j ) {
                if(i != j) {
                    sum += Math.abs(A.get(i, j));
                }
            }
            if(a_ii <= sum) {
                return false;
            }
        }

        return true;
    }

}
