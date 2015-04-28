package com.yorg.mownit.lab4.polynomial;

public class PolynomialBase extends Polynomial {

    private final double a;
    private final double x0;
    private final double b;

    public PolynomialBase(double a, double x0) {
        this(a, x0, 0.0);
    }

    public PolynomialBase(double a, double x0, double b) {
        this.a = a;
        this.x0 = x0;
        this.b = b;
    }

    @Override
    public double getCompositeValue(double argument) {
        return a * (argument - x0) + b;
    }
}
