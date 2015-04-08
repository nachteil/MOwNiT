package com.yorg.mownit.lab2;

import org.ejml.simple.SimpleMatrix;

public class Lab2 {

    MatrixElementType type;

    public Lab2(MatrixElementType type) {
        this.type = type;
    }

    public SimpleMatrix getMatrix(int N, int m, int k) {

        SimpleMatrix matrix = new SimpleMatrix(N, N);
        MatrixElementValue valueProducer = new MatrixElementValue(type);

        for(int i = 1; i <= N; ++i) {
            for(int j = 1; j <= N; ++j) {

                int rowIndex = i - 1;
                int colIndex = j - 1;

                double ijElement = valueProducer.getElementValue(i, j, N, m, k);
                matrix.set(rowIndex, colIndex, ijElement);

            }
        }
        return matrix;
    }

}
