package com.yorg.mownit.lab3.systems;

import org.ejml.simple.SimpleMatrix;

public class MyFunction implements VectorFunction {

    @Override
    public SimpleMatrix getValue(SimpleMatrix x) {

        SimpleMatrix result = new SimpleMatrix(3, 1);

        double x1 = x.get(0, 0);
        double x2 = x.get(1, 0);
        double x3 = x.get(2, 0);

        double v1, v2, v3;

        v1 = (x1 * x1) + (x2 * x2) + (x3);
        v2 = (2.0 * x1 * x1) - (x2 * x2) - (4.0 * x3 * x3);
        v3 = (x1 * x1) + (x2) + (x3);

        result.set(0, 0, v1 - 1.0);
        result.set(1, 0, v2 - 2.0);
        result.set(2, 0, v3 - 1.0);

        return result;
    }

}
