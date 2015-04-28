package com.yorg.mownit.lab3.systems;

import org.ejml.simple.SimpleMatrix;

public class JacobiMatrixValue implements VectorFunction {

    @Override
    public SimpleMatrix getValue(SimpleMatrix x) {

        SimpleMatrix result = new SimpleMatrix(3,3);
        double [][] vals = new double[3][3];

        double x1 = x.get(0, 0);
        double x2 = x.get(1, 0);
        double x3 = x.get(2, 0);

        vals[0][0] = 2.0 * x1;
        vals[0][1] = 2.0 * x2;
        vals[0][2] = 1.0;

        vals[1][0] = 4.0 * x1;
        vals[1][1] = -2.0 * x2;
        vals[1][2] = -8.0 * x3;

        vals[2][0] = 2.0 * x1;
        vals[2][1] = 1.0;
        vals[2][2] = 1.0;

        for(int i = 0; i < 3; ++i) {
            for(int j = 0; j < 3; ++j) {
                result.set(i, j, vals[i][j]);
            }
        }

        return result;
    }
}
