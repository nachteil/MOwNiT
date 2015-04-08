package com.yorg.mownit.lab1;

import java.util.Formatter;

public class DoubleMatrix {

    private final double [][] array;
    private final int numCols;
    private final int numRows;
    private static final double ZERO_LIMIT = 10e-6;

    public static DoubleMatrix multiply(DoubleMatrix a, DoubleMatrix b) {

        if(a.getNumCols() != b.getNumRows()) {
            throw new IllegalArgumentException("");
        }

        DoubleMatrix result = new DoubleMatrix(a.getNumRows(), b.getNumCols());

        for(int row = 0; row < a.getNumRows(); ++row) {
            for(int col = 0; col < b.getNumCols(); ++col) {
                double value = 0.0;
                for(int k = 0; k < a.getNumCols(); ++k) {
                    value += a.get(row, k) * b.get(k, col);
                }
                result.set(row, col, value);
            }
        }

        return result;
    }

    public DoubleMatrix(int rows, int cols) {
        array = new double[rows][cols];
        numCols = cols;
        numRows = rows;
    }

    public DoubleMatrix(int n) {
        array = new double [n][n];
        numCols = numRows = n;
    }

    public int getNumCols() {
        return numCols;
    }

    public int getNumRows() {
        return numRows;
    }

    public void set(int row, int col, double value) {
        array[row][col] = value;
    }

    public double get(int row, int col) {
        return array[row][col];
    }

    public void performGaussianElimination() {

        orderRowsByLeadingElements();
//        System.out.println("After ordering:\n");
//        System.out.println(this);
        transformToRowEchelonForm();
//        System.out.println("After echelon:\n");
//        System.out.println(this);
        reduceRowEchelonForm();
//        System.out.println("After reduced echelon:\n");
//        System.out.println(this);
    }

    private void reduceRowEchelonForm() {
        for(int i = 0; i < numRows; ++i) {
            for(int j = i-1; j >= 0; --j) {
                if(!isDoubleingPointZero(array[j][i]) && !isDoubleingPointZero(array[i][i])) {
                    double f = array[j][i] / array[i][i];
                    /*if(Double.isNaN(f)) {
                        System.out.println("NaN:\ni=" + i + ", j=" + j);
                        System.out.println("array[j][i]=" + array[j][i] + ", " + "array[i][i]=" + array[i][i]);
                        System.out.println(this);
                    }*/
//                    System.out.println("Not zero: " + array[i][i]);
                    for(int k = i; k < numCols; ++k) {
                        array[j][k] -= f * array[i][k];
                    }
                }
                /*System.out.println("After loop:\ni=" + i + ", j=" + j);
                System.out.println("array[j][i]=" + array[j][i] + ", " + "array[i][i]=" + array[i][i]);
                System.out.println(this);*/
            }
        }
    }

    private void transformToRowEchelonForm() {

        for(int i = 0; i < numRows; ++i) {
            normalizeByLeadingElement(i);
            reduceRowsBelow(i);
        }
    }

    private void reduceRowsBelow(int i) {

        for(int k = i + 1; k < numRows; ++k) {

            if(array[k][i] != 0) {
                double f = array[k][i];
                for(int m = i; m < numCols; ++m) {
                    array[k][m] -= f * array[i][m];
                }
            }
        }
    }

    private void normalizeByLeadingElement(int i) {

        double factor = array[i][i];

        if(factor != 0) {
            for(int k = i; k < numCols; ++k) {
                array[i][k] /= factor;
            }
        }
    }

    private void orderRowsByLeadingElements() {

        for(int i = 0; i < numRows; ++i) {

            int leadingElementsRowIndexForNthColumn = getLeadingElementsRowForNthColumn(i);
            if(leadingElementsRowIndexForNthColumn != i) {
                swapRows(i, leadingElementsRowIndexForNthColumn);
            }
        }

    }

    private void swapRows(int m, int n) {

        for (int k = 0; k < numCols; ++k) {
            double swapBuffer = array[m][k];
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

    private boolean isDoubleingPointZero(double k) {
        return Math.abs(k - 0.0f) < ZERO_LIMIT;
    }

}
