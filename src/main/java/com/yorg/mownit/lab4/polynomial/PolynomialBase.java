package com.yorg.mownit.lab4.polynomial;

public class PolynomialBase extends Polynomial {

    private final double a;
    private final double x0;

    public PolynomialBase(double a, double x0) {
        this.a = a;
        this.x0 = x0;
    }

    @Override
    public double getCompositeValue(double argument) {
        return a * (argument - x0);
    }
}
