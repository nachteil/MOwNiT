package com.yorg.mownit.lab1.util;

import com.yorg.mownit.lab1.math.DoubleMatrix;
import com.yorg.mownit.lab1.math.FloatMatrix;

import java.beans.ExceptionListener;
import java.util.Random;

public class Lab1 {

    private static final Random random = new Random(System.currentTimeMillis());


    private static final Exercise EXERCISE_ONE_INSTANCE = new ExerciseOne();
    private static final Exercise EXERCISE_TWO_INSTANCE = new ExerciseTwo();
    private static final Exercise EXERCISE_THREE_INSTANCE = new ExerciseThree();

    public static Exercise exerciseOne() { return EXERCISE_ONE_INSTANCE; }
    public static Exercise exerciseTwo() { return EXERCISE_TWO_INSTANCE; }
    public static Exercise exerciseThree() { return EXERCISE_THREE_INSTANCE; }

    public static FloatMatrix getRandomFloatVector(int size) {

        FloatMatrix vector = new FloatMatrix(size, 1);
        float value;

        for(int i = 0; i < size; ++i) {
            value = random.nextFloat() < 0.5 ? 1.0f : -1.0f;
            vector.set(i, 0, value);
        }
        return vector;
    }

    public static DoubleMatrix upgradeFloatVectorToDouble(FloatMatrix floatVector) {

        DoubleMatrix result = new DoubleMatrix(floatVector.getNumRows(), 1);

        for(int i = 0; i < floatVector.getNumRows(); ++i) {
            result.set(i, 0, (double)floatVector.get(i, 0));
        }
        return result;
    }

    private static class ExerciseOne extends ExerciseBase {

        @Override
        public double getDoubleMatrixElement(int i, int j) {
            if( i == 1 ) {
                return 1.0d;
            } else {
                return 1.0d / (((double)i) + ((double)j) - 1.0d);
            }
        }

        @Override
        public String getDescription() {
            return "ex_1";
        }
    }

    private static class ExerciseTwo extends ExerciseBase {

        @Override
        public double getDoubleMatrixElement(int i, int j) {
            if(j >= i) {
                return 2.0d * i / j;
            } else {
                return getDoubleMatrixElement(j, i);
            }
        }

        @Override
        public String getDescription() {
            return "ex_2";
        }
    }

    private static class ExerciseThree extends ExerciseBase {

        private static final int k = 4;
        private static final int m = 4;
        
        @Override
        public double getDoubleMatrixElement(int i, int j) {
            
            double element;
            
            if(i == j) {
                element = k;
            } else if (j == i + 1) {
                element = 1 / (i + m);
            } else if (j == i - 1 && i > 1){
                element = k / (i + m + 1);
            } else {
                element = 0;
            }

            return element;
        }

        @Override
        public String getDescription() {
            return "ex_3";
        }
    }

    private static abstract class ExerciseBase implements Exercise {

        @Override
        public FloatMatrix getFloatMatrix(int size) {
            FloatMatrix matrix = new FloatMatrix(size);
            for(int row = 0; row < size; ++row) {
                for(int col = 0; col < size; ++col) {
                    float element = getFloatMatrixElement(row+1, col+1);
                    matrix.set(row, col, element);
                }
            }
            return matrix;
        }

        @Override
        public DoubleMatrix getDoubleMatrix(int size) {
            DoubleMatrix matrix = new DoubleMatrix(size);
            for(int row = 0; row < size; ++row) {
                for(int col = 0; col < size; ++col) {
                    double element = getDoubleMatrixElement(row+1, col+1);
                    matrix.set(row, col, element);
                }
            }
            return matrix;
        }

        @Override
        public float getFloatMatrixElement(int i, int j) {
            return (float) getDoubleMatrixElement(i, j);
        }

        @Override
        public abstract double getDoubleMatrixElement(int i, int j);
    }
}
