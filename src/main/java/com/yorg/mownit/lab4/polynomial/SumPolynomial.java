package com.yorg.mownit.lab4.polynomial;

import java.util.Collections;

public class SumPolynomial extends Polynomial {

    SumPolynomial(Polynomial ... polynomials) {
        super();
        Collections.addAll(components, polynomials);
    }

    @Override
    public double getCompositeValue(double argument) {
        double result = 0.0d;
        for(Polynomial p : components) {
            result += p.getValue(argument);
        }
        return result;
    }
}
