package com.yorg.mownit.lab1.util;

import com.yorg.mownit.lab1.math.DoubleMatrix;
import com.yorg.mownit.lab1.math.FloatMatrix;

public class MatrixUtils {

    public static FloatMatrix getExtendedFloatMatrix(FloatMatrix matrix, FloatMatrix vector) {

        if(matrix.getNumRows() != vector.getNumRows()) {
            throw new IllegalArgumentException("Matrix and vector must have the same number of rows");
        }

        int numRows = matrix.getNumRows();
        int numCols = matrix.getNumCols();
        FloatMatrix extendedMatrix = new FloatMatrix(numRows, numCols+1);

        for(int row = 0; row < numRows; ++row) {
            for(int col = 0; col < numCols; ++col) {
                extendedMatrix.set(row, col, matrix.get(row, col));
            }
        }

        for(int i = 0; i < numRows; ++i) {
            extendedMatrix.set(i, numRows, vector.get(i, 0));
        }

        return extendedMatrix;
    }

    public static DoubleMatrix getExtendedDoubleMatrix(DoubleMatrix matrix, DoubleMatrix vector) {

        if(matrix.getNumRows() != vector.getNumRows()) {
            throw new IllegalArgumentException("Matrix and vector must have the same number of rows");
        }

        int numRows = matrix.getNumRows();
        int numCols = matrix.getNumCols();
        DoubleMatrix extendedMatrix = new DoubleMatrix(numRows, numCols+1);

        for(int row = 0; row < numRows; ++row) {
            for(int col = 0; col < numCols; ++col) {
                extendedMatrix.set(row, col, matrix.get(row, col));
            }
        }

        for(int i = 0; i < numRows; ++i) {
            extendedMatrix.set(i, numRows, vector.get(i, 0));
        }

        return extendedMatrix;
    }

    public static float getVectorDistance(FloatMatrix x, FloatMatrix extendedMatrix) {
        float distance = 0.0f;
        int n = x.getNumRows();
        for(int i = 0; i < n; ++i) {
            distance += Math.pow(extendedMatrix.get(i, n) - x.get(i, 0), 2);
        }
        distance = (float) Math.sqrt(distance);
        return distance;
    }
    
    public static double getVectorDistance(DoubleMatrix x, DoubleMatrix extendedMatrix) {
        float distance = 0.0f;
        int n = x.getNumRows();
        for(int i = 0; i < n; ++i) {
            distance += Math.pow(extendedMatrix.get(i, n) - x.get(i, 0), 2);
        }
        distance = (float) Math.sqrt(distance);
        return distance;
    }
}
