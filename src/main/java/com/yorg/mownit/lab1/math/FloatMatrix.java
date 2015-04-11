package com.yorg.mownit.lab1.math;

import java.util.Formatter;

public class FloatMatrix {

    private final float [][] array;
    private final int numCols;
    private final int numRows;
    private static final float ZERO_LIMIT = 1e-6f;

    public static FloatMatrix multiply(FloatMatrix a, FloatMatrix b) {

        if(a.getNumCols() != b.getNumRows()) {
            throw new IllegalArgumentException("");
        }

        FloatMatrix result = new FloatMatrix(a.getNumRows(), b.getNumCols());

        for(int row = 0; row < a.getNumRows(); ++row) {
            for(int col = 0; col < b.getNumCols(); ++col) {
                float value = 0.0f;
                for(int k = 0; k < a.getNumCols(); ++k) {
                    value += a.get(row, k) * b.get(k, col);
                }
                result.set(row, col, value);
            }
        }

        return result;
    }

    public FloatMatrix(int rows, int cols) {
        array = new float[rows][cols];
        numCols = cols;
        numRows = rows;
    }

    public FloatMatrix(int n) {
        array = new float [n][n];
        numCols = numRows = n;
    }

    public int getNumCols() {
        return numCols;
    }

    public int getNumRows() {
        return numRows;
    }

    public void set(int row, int col, float value) {
        array[row][col] = value;
    }

    public float get(int row, int col) {
        return array[row][col];
    }

    public void performGaussianElimination() {

        transformToRowEchelonForm();
        reduceRowEchelonForm();
    }

    private void reduceRowEchelonForm() {
        for(int i = 0; i < numRows; ++i) {
            for(int j = i-1; j >= 0; --j) {
                if(!isFloatingPointZero(array[j][i]) && !isFloatingPointZero(array[i][i])) {
                    float f = array[j][i] / array[i][i];
                    for(int k = i; k < numCols; ++k) {
                        array[j][k] -= f * array[i][k];
                    }
                }
            }
        }
    }

    private void transformToRowEchelonForm() {

        for(int i = 0; i < numRows; ++i) {
            normalizeRowByLeadingElement(i);
            reduceRowsBelow(i);
        }
    }

    private void reduceRowsBelow(int i) {

        for(int k = i + 1; k < numRows; ++k) {

            if(array[k][i] != 0) {
                float f = array[k][i];
                for(int m = i; m < numCols; ++m) {
                    array[k][m] -= f * array[i][m];
                }
            }
        }
    }

    private void normalizeRowByLeadingElement(int i) {

        float factor = array[i][i];

        if(factor != 0) {
            for(int k = i; k < numCols; ++k) {
                array[i][k] /= factor;
            }
        }
    }

    private void swapRows(int m, int n) {

        for (int k = 0; k < numCols; ++k) {
            float swapBuffer = array[m][k];
            array[m][k] = array[n][k];
            array[n][k] = swapBuffer;
        }
    }

    private int getLeadingElementsRowForNthColumn(int n) {

        int leadingElementsRowIndexForNthColumn = n;

        for(int j = n; j < numRows; ++j) {
            if(array[j][n] > array[leadingElementsRowIndexForNthColumn][n]) {
                leadingElementsRowIndexForNthColumn = j;
            }
        }
        return leadingElementsRowIndexForNthColumn;
    }

    @Override
    public String toString() {

        Formatter formatter = new Formatter();

        StringBuffer buffer = new StringBuffer();

        for(int row = 0; row < numRows; ++row) {
            for(int col = 0; col < numCols; ++col) {
                if(col >= numRows) {
                    buffer.append("  |");
                }
                buffer.append(String.format("%7.3f ", array[row][col]));
            }
            buffer.append("\n");
        }
        return buffer.toString();
    }

    private boolean isFloatingPointZero(float k) {
        return Math.abs(k - 0.0f) < ZERO_LIMIT;
    }

}
