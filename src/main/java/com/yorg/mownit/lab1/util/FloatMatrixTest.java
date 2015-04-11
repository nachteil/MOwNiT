package com.yorg.mownit.lab1.util;


import com.yorg.mownit.lab1.math.FloatMatrix;

public class FloatMatrixTest {

    public static void main(String ... args) {

        float [][] array = {
                { 2.0f, 7.0f, 12.0f, 1.0f, 12.0f },
                { 3.0f, 1.0f, 9.0f, 4.0f, -1.0f },
                { 6.0f, 12.0f, 5.0f, 3.0f, 4.0f },
                { 4.0f, 1.0f, 6.0f, 2.0f, 0.0f }
        };

        float [][] arrayWiki = {
                { 1.0f, -1.0f, 2.0f, 2.0f, 0.0f },
                { 2.0f, -2.0f, 1.0f, 0.0f, 1.0f },
                { -1.0f, 1.0f, 1.0f, -2.0f, 1.0f },
                { 2.0f, -2.0f, 4.0f, 0.0f, 2.0f }
        };

        FloatMatrix matrix = new FloatMatrix(4, 5);

        for(int row = 0; row < 4; ++row) {
            for(int col = 0; col < 5; ++col) {
                matrix.set(row, col, arrayWiki[row][col]);
            }
        }

        matrix.performGaussianElimination();

        System.out.println(matrix);

    }

}