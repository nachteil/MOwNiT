package com.yorg.mownit.lab3.solvers;

import com.yorg.mownit.lab3.math.Function;
import com.yorg.mownit.lab3.utils.Result;
import com.yorg.mownit.lab3.utils.SolveParams;
import com.yorg.mownit.lab3.utils.Stopper;


public abstract class AbstractSolver implements ISolver {

    protected final Function<Double> function;

    public AbstractSolver(Function<Double> function) {
        this.function = function;
    }

    @Override
    public Result solve(SolveParams params, Stopper stopper) {

        double xPrevious = params.getCompartment().getStart();
        double xNext = params.getCompartment().getEnd();
        double xNew;

        int i = 0;

        while(! stopper.shouldStop(xPrevious, xNext, function.getValue(xNext), i)) {
            xNew = getNextApproximation(xPrevious, xNext);
            xPrevious = xNext;
            xNext = xNew;
            ++i;
        }

        Result result;
        System.out.println(i);
        if(i < params.getMaxIterationCount()) {
            result = new Result.CorrectResult(xNext);

        } else {
            result = new Result.IncorrectResult("At given precission (" + params.getAccuracy()
                    + ") no result has been found in " + params.getMaxIterationCount() + " iterations");
        }

        return result;
    }

    protected abstract double getNextApproximation(double xNext, double xPrevious);
}
