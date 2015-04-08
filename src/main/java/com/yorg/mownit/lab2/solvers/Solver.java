package com.yorg.mownit.lab2.solvers;

import lombok.Getter;
import org.ejml.simple.SimpleMatrix;

import java.util.ArrayList;
import java.util.List;

public abstract class Solver implements ISolver {

    @Getter SimpleMatrix A;
    @Getter SimpleMatrix b;

    SimpleMatrix L;
    SimpleMatrix D;
    SimpleMatrix U;

    List<SimpleMatrix> cachedResults = new ArrayList<>(50);

    public Solver(SimpleMatrix A, SimpleMatrix b) {

        this.A = A;
        this.b = b;

        L = new SimpleMatrix(A.numRows(), A.numCols());
        D = new SimpleMatrix(A.numRows(), A.numCols());
        U = new SimpleMatrix(A.numRows(), A.numCols());

        initL();
        initD();
        initU();


        insertResultZeroBase();
    }

    private void insertResultZeroBase() {

        SimpleMatrix x = new SimpleMatrix(A.numCols(), 1);

        for(int i = 0; i < x.numRows(); ++i) {
            x.set(i, 0, 0.0d);
        }
        cachedResults.add(x);
    }

    @Override
    public SimpleMatrix getResult(int numberOfIterations) {

        if(numberOfIterations < 1) {
            throw new IllegalArgumentException("Number of iterations must be greater than 0");
        }

        if(numberOfIterations > cachedResults.size() - 1) {
            for(int i = cachedResults.size(); i <= numberOfIterations; ++i) {
                SimpleMatrix previous = cachedResults.get(i - 1);
                SimpleMatrix current = getNextIterationMatrix(previous);
                cachedResults.add(current);
            }
        }
        return cachedResults.get(numberOfIterations);
    }

    public double getSpectralRadius() {
        double result = calculateNormElement(0);
        for(int i = 1; i < A.numCols(); ++i) {
            double k = calculateNormElement(i);
            result = k > result ? k : result;
        }
        return result;
    }

    public boolean isJacobiConvergent() {
        return getSpectralRadius() < 1.0d;
    }

    abstract SimpleMatrix getNextIterationMatrix(SimpleMatrix previous);

    private double calculateNormElement(int i) {
        double sum = 0.0d;
        for(int j = 0; j < A.numCols(); ++j) {
            sum += (i == j) ? 0.0d : Math.abs(A.get(i,j));
        }
        return sum / Math.abs(A.get(i,i));
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
}
