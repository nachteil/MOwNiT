package com.yorg.mownit.lab5.interpolation;

public class ClampedSplineHelper {

    private final double [] xValues;
    private final double [] yValues;

    private final int N;

    public ClampedSplineHelper(double[] xValues, double[] yValues) {

        this.xValues = xValues;
        this.yValues = yValues;

        N = xValues.length + 1;
    }

    public double h(int i) {
        return xValues[i+1] - xValues[i];
    }

}
