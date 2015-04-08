package com.yorg.mownit.lab2.math;

import org.ejml.simple.SimpleMatrix;

import java.util.Random;

public class VectorUtils {

    public static SimpleMatrix getRandomVactor(int N) {
        Random random = new Random();
        SimpleMatrix vector = new SimpleMatrix(N, 1);
        for(int i = 0; i < N; ++i) {
            vector.set(i, 0, random.nextDouble() > 0.5d ? -1.0 : 1.0);
        }

        return vector;
    }


    public static double euclidianNorm(SimpleMatrix vector) {

        if(vector.numCols() != 1) {
            throw new IllegalArgumentException("Vector expected: number of columns must be 1");
        }

        double sum = 0.0;
        for(int i = 0; i < vector.numRows(); ++i) {
            sum += Math.pow(vector.get(i, 0), 2);
        }
        return Math.sqrt(sum);
    }
}
