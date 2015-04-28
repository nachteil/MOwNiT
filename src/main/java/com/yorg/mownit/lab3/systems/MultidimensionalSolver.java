package com.yorg.mownit.lab3.systems;

import com.yorg.mownit.commons.VectorUtils;
import com.yorg.mownit.lab3.solvers.AbstractSolver;
import org.ejml.simple.SimpleMatrix;

public class MultidimensionalSolver {

    VectorFunction F;
    VectorFunction J;
    private int ITERATION_LIMIT;

    public MultidimensionalSolver() {
        F = new MyFunction();
        J = new JacobiMatrixValue();
    }

    public SimpleMatrix solve(SimpleMatrix startingVector) {

        int iterationCounter = 0;
        SimpleMatrix previous;
        SimpleMatrix next = new SimpleMatrix(startingVector);

        ITERATION_LIMIT = 128;

        do {
            ++iterationCounter;

            previous = next;

            SimpleMatrix jacobiValue = J.getValue(previous);
            SimpleMatrix functionValue = F.getValue(previous);

//            jacobiValue = jacobiValue.invert();
//            next = previous.minus(jacobiValue.mult(functionValue));

            SimpleMatrix residuum = jacobiValue.solve(functionValue);
            next = previous.minus(residuum);

        } while (iterationCounter < ITERATION_LIMIT && VectorUtils.euclidianNorm(F.getValue(next)) > 1E-5);

        if(iterationCounter == ITERATION_LIMIT) {
            return null;
        } else {
            return next;
        }
    }
}
