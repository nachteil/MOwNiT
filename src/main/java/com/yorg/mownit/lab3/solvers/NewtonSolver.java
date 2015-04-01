package com.yorg.mownit.lab3.solvers;

import com.yorg.mownit.lab3.math.Function;
import com.yorg.mownit.lab3.math.Polynomial;

/**
 * Created by yorg on 01.04.15.
 */
public class NewtonSolver extends AbstractSolver {

    private final Polynomial polynomialFuntion;
    private final Polynomial derivative;

    public NewtonSolver(Function<Double> function) {

        super(function);

        if(! (function instanceof Polynomial)) {
            throw new IllegalArgumentException("Function must be a polynomial for this solver");
        }
        this.polynomialFuntion = (Polynomial) function;
        this.derivative = polynomialFuntion.getFirstDerivative();
    }

    @Override
    protected double getNextApproximation(double xNext, double xPrevious) {
        return xNext - polynomialFuntion.getValue(xNext) / derivative.getValue(xNext);
    }
}
