package com.yorg.mownit.lab4.polynomial;

import java.util.Collections;

public class MultiplyPolynomial extends Polynomial {

    MultiplyPolynomial(Polynomial ... polynomials) {
        super();
        Collections.addAll(components, polynomials);
    }

    @Override
    public double getCompositeValue(double argument) {
        double result = 1.0d;
        for(Polynomial p : components) {
            result *= p.getValue(argument);
        }
        return result;
    }
}
