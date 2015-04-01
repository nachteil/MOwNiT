package com.yorg.mownit.lab3.solvers;

import com.yorg.mownit.lab3.math.Function;
import com.yorg.mownit.lab3.utils.Result;
import com.yorg.mownit.lab3.utils.SolveParams;

/**
 * Created by yorg on 01.04.15.
 */
public abstract class AbstractSolver {
    protected final Function<Double> function;

    public AbstractSolver(Function<Double> function) {
        this.function = function;
    }

    public Result solve(SolveParams params) {

        double xPrevious = params.getCompartment().getStart();
        double xNext = params.getCompartment().getEnd();
        double xNew;

        int i = 0;

        while(i++ < params.getMaxIterationCount() && Math.abs(function.getValue(xNext)) > params.getAcccuracy()) {
            xNew = getNextApproximation(xPrevious, xNext);
            xPrevious = xNext;
            xNext = xNew;
            System.out.println("Iteration: " + i);
        }

        Result result;

        if(i < params.getMaxIterationCount()) {
            result = new Result.CorrectResult(xNext);
        } else {
            result = new Result.IncorrectResult("At given precission (" + params.getAcccuracy()
                    + ") no result has been found in " + params.getMaxIterationCount() + " iterations");
        }

        return result;
    }

    protected abstract double getNextApproximation(double xNext, double xPrevious);
}
