package com.yorg.mownit.lab2;

import org.ejml.simple.SimpleMatrix;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

/**
 * Created by yorg on 25.03.15.
 */
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



    public SimpleMatrix getExampleMatrix() {

        double [][] exampleA = {
                { 4, -1, -0.2, 2 },
                { -1, 5, 0, -2 },
                { 0.2, 1, 10, -1 },
                { 0, -2, -1, 4 }
        };

        SimpleMatrix A = new SimpleMatrix(4, 4);

        for(int i = 0; i < 4; ++i) {
            for(int j = 0; j < 4; ++j) {
                A.set(i, j, exampleA[i][j] );
            }
        }

        return A;
    }

    public SimpleMatrix getExampleVector() {

        double [] b = { 30, 0, -10, 5 };


        SimpleMatrix x = new SimpleMatrix(4, 1);

        for(int i = 0; i < 4; ++i) {
            x.set(i, 0, b[i]);
        }
        return x;
    }

}
