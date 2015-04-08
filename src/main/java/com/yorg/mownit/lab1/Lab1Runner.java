package com.yorg.mownit.lab1;

import java.util.Random;

/**
 * Created by yorg on 18.03.15.
 */
public class Lab1Runner {

    private static float getMatrixElementForIJ(int i, int j) {
        if( i == 1 ) {
            return 1.0f;
        } else {
            return 1.0f / (i + j - 1);
        }
    }

    public static void main(String[] args) {

        final int N = 3;
        final float onesRatio = 0.5f;

        Random random = new Random(System.currentTimeMillis());

        FloatMatrix A = new FloatMatrix(N, N);
        FloatMatrix x = new FloatMatrix(N, 1);

        for(int i = 0; i < N; ++i) {
            for(int j = 0; j < N; ++j) {
                A.set(i, j, getMatrixElementForIJ(i+1, j+1));
            }
        }

        for(int i = 0; i < N; ++i) {
            float value = random.nextFloat() < onesRatio ? 1.0f : -1.0f;
            x.set(i, 0, value);
        }

        FloatMatrix multiplicationResultMatrix = FloatMatrix.multiply(A, x);

        FloatMatrix extendedMatrix = new FloatMatrix(N, N+1);

        for(int i = 0; i < N; ++i) {
            for(int j = 0; j < N; ++j) {
                extendedMatrix.set(i, j, A.get(i, j));
            }
        }

        for(int i = 0; i < N; ++i) {
            extendedMatrix.set(i, N, multiplicationResultMatrix.get(i, 0));
        }

        extendedMatrix.performGaussianElimination();

        float distance = 0.0f;
        for(int i = 0; i < N; ++i) {
            distance += Math.pow(extendedMatrix.get(i, N) - x.get(i, 0), 2);
        }
        distance = (float) Math.sqrt(distance);

        System.out.println("A:");
        System.out.println(A);
        System.out.println("x:");
        System.out.println(x);
        System.out.println("multiplicationResultMatrix:");
        System.out.println(multiplicationResultMatrix);
        System.out.println("Reduced echelon:");
        System.out.println(extendedMatrix);
        System.out.println("Vectors distance: " + distance);
    }

}
