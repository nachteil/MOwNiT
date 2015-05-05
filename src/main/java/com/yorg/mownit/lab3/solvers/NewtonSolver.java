package com.yorg.mownit.lab3.solvers;

import com.yorg.mownit.commons.Function;
import com.yorg.mownit.lab3.math.Polynomial;

public class NewtonSolver extends AbstractSolver {

    private final Function derivative;

    public NewtonSolver(Function function, Function derivative) {

        super(function);
        this.derivative = derivative;
    }

    @Override
    protected double getNextApproximation(double xNext, double xPrevious) {
        return xNext - function.getValue(xNext) / derivative.getValue(xNext);
    }
}
