package com.yorg.mownit.lab2.solvers;

import org.ejml.simple.SimpleMatrix;

public class JacobiSolver extends Solver {

    SimpleMatrix M;
    SimpleMatrix N;

    public JacobiSolver(SimpleMatrix A, SimpleMatrix b) {

        super(A, b);

        N = D.invert();
        M = N.mult(L.plus(U)).scale(-1.0d);
    }

    @Override
    SimpleMatrix getNextIterationMatrix(SimpleMatrix previous) {
        return M.mult(previous).plus(N.mult(b));
    }
}
