package com.yorg.mownit.lab4.polynomial;

import com.yorg.mownit.commons.Function;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

public abstract class Polynomial implements Function {

    List<Polynomial> components;

    double scale = 1.0d;
    void setScale(double d) { this.scale = d; }

    public Polynomial() {
        this.components = new ArrayList<>();
    }

    @Override
    public double getValue(double argument) {
        return scale * getCompositeValue(argument);
    }

    abstract double getCompositeValue(double argument);

    public static Polynomial multiply(Polynomial ... polynomials) {
        return new MultiplyPolynomial(polynomials);
    }
    public static Polynomial multiply(Polynomial p, double d) {
        p.setScale(d);
        return p;
    }

    public static Polynomial sum(Polynomial ... polynomials) {
        return new SumPolynomial(polynomials);
    }
}
