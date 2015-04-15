package com.yorg.mownit.lab5.interpolation;

public class NaturalSplineHelper {

    private final double [] xValues;
    private final double [] yValues;

    private final int N;

    public NaturalSplineHelper(double[] xValues, double[] yValues) {

        this.xValues = xValues;
        this.yValues = yValues;

        N = xValues.length - 1;
    }

    double u(int i) {
        return 2 * (h(i) + h(i-1));
    }

    double b(int i) {
        return 6 * (yValues[i+1] - yValues[i]) / h(i);
    }

    double v(int i) {
        return b(i) - b(i-1);
    }

    double h(int i) {
        return xValues[i+1] - xValues[i];
    }

}
