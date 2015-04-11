package com.yorg.mownit.commons;

import org.ejml.simple.SimpleMatrix;

import java.util.HashMap;
import java.util.Map;

public class ConditionNumber {

    private static SimpleMatrix cacheMatrix = null;

    public static double getConditionNumber(int N, MatrixElementFormula formula) {

//        if(cacheMatrix == null) {
//            cacheMatrix = new SimpleMatrix(1000, 1000);
//            for(int i = 0; i < 1000; ++i) {
//                for(int j = 0; j < 1000; ++j) {
//                    cacheMatrix.set(i, j, formula.getElement(i+1, j+1));
//                }
//            }
//        }
//        SimpleMatrix A = cacheMatrix.extractMatrix(0, N, 0, N);

        SimpleMatrix A = new SimpleMatrix(N, N);
        for(int i = 0; i < N; ++i) {
            for(int j = 0; j < N; ++j) {
                A.set(i, j, formula.getElement(i+1, j+1));
            }
        }

        SimpleMatrix inverted = A.invert();

        double normOfA = getMaxRowSumNorm(A);
        double normOfInvertedA = getMaxRowSumNorm(inverted);

        return normOfA * normOfInvertedA;
    }

    public static double getMaxRowSumNorm(SimpleMatrix matrix) {

        int numCols = matrix.numCols();
        int numRows = matrix.numRows();

        double max = -1.0d;

        for(int i = 0; i < numRows; ++i) {
            double sum = 0;
            for(int j = 0; j < numCols; ++j) {
                sum += Math.abs(matrix.get(i, j));
            }
            max = sum > max ? sum : max;
        }
        return max;
    }

}
