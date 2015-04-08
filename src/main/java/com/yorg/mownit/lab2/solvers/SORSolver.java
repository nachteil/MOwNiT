package com.yorg.mownit.lab2.solvers;

import org.ejml.simple.SimpleMatrix;

public class SORSolver extends Solver {

    private double omega;

    private SimpleMatrix first;
    private SimpleMatrix second;

    public SORSolver(SimpleMatrix A, SimpleMatrix b, double omega) {

        super(A, b);

        this.omega = omega;

        first = D.plus(L.scale(omega)).invert();
        first = first.mult(D.scale(1-omega).minus(U.scale(omega)));

        second = D.plus(L.scale(omega)).invert().scale(omega);
    }

    @Override
    SimpleMatrix getNextIterationMatrix(SimpleMatrix previous) {
        return first.mult(previous).plus(second.mult(b));
    }
}
