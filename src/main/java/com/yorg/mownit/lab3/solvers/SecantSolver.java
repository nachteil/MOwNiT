package com.yorg.mownit.lab3.solvers;

import com.yorg.mownit.lab3.math.Function;

public class SecantSolver extends AbstractSolver {

    public SecantSolver(Function<Double> function) {
        super(function);
    }

    @Override
    protected double getNextApproximation(double xPrevious, double xNext) {
        return xNext - (function.getValue(xNext) * (xNext - xPrevious)) / (function.getValue(xNext) - function.getValue(xPrevious));
    }
}
