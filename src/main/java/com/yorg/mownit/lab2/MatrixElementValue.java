package com.yorg.mownit.lab2;

import sun.reflect.generics.reflectiveObjects.NotImplementedException;

/**
 * Created by yorg on 25.03.15.
 */
public class MatrixElementValue {

    private final MatrixElementType type;

    public MatrixElementValue(MatrixElementType type) {
        this.type = type;
    }


    public double getElementValue(int row, int col, int N, int m, int k) {

        double result;

        switch (type) {
            case A:
                result = getATypeElement(row, col, N, m, k);
                break;
            case B:
                result = getBTypeElement(row, col, N, m, k);
                break;
            case C:
                result = getCTypeElement(row, col, N, m, k);
                break;
            default:
                throw new IllegalArgumentException("Unknown type");
        }

        return result;
    }

    private double getATypeElement(int row, int col, int N, int m, int k) {

        double result;

        if(row == col) {
            result = k;
        } else {
            // TODO: is N dimension of the matrix?
            result = ((double) m) / (N - row - col + 0.5);
        }

        return result;
    }

    private double getBTypeElement(int row, int col, int N, int m, int k) {
        throw new NotImplementedException();
    }

    private double getCTypeElement(int row, int col, int N, int m, int k) {
        throw new NotImplementedException();

    }

}
