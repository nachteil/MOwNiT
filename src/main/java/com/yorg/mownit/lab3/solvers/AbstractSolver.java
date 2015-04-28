package com.yorg.mownit.lab3.solvers;

import com.yorg.mownit.commons.Function;
import com.yorg.mownit.lab3.utils.Result;
import com.yorg.mownit.lab3.utils.SolveParams;
import com.yorg.mownit.lab3.utils.Stopper;


public abstract class AbstractSolver implements ISolver {

    protected final Function function;

    public AbstractSolver(Function function) {
        this.function = function;
    }

    @Override
    public Result solve(SolveParams params, Stopper stopper) {

        double xPrevious = params.getStartPoint();
        double xNext = params.getSecondStartPoint();
        double xNew;

        int i = 0;

        while(!stopper.shouldStop(xPrevious, xNext, function.getValue(xNext), i) && i < params.getMaxIterationCount()) {
            xNew = getNextApproximation(xPrevious, xNext);
            xPrevious = xNext;
            xNext = xNew;
            ++i;
        }

        Result result;
        if(i < params.getMaxIterationCount()) {
            result = new Result.CorrectResult(xNext, function.getValue(xNext), i, xNext - xPrevious);
        } else {
            result = new Result.IncorrectResult("At given precission (" + params.getAccuracy()
                    + ") no result has been found in " + params.getMaxIterationCount() + " iterations");
        }

        return result;
    }

    protected abstract double getNextApproximation(double xNext, double xPrevious);
}
