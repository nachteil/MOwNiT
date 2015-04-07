package com.yorg.mownit.lab3.solvers;

import com.yorg.mownit.lab3.math.Function;
import com.yorg.mownit.lab3.utils.Result;
import com.yorg.mownit.lab3.utils.SolveParams;
import com.yorg.mownit.lab3.utils.Stopper;

<<<<<<< HEAD
public abstract class AbstractSolver {

=======
public abstract class AbstractSolver implements ISolver {
>>>>>>> 2fa125236316dcd7bdf131695d8fd2164d94a15f
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

<<<<<<< HEAD
        while(i++ < params.getMaxIterationCount() && Math.abs(function.getValue(xNext)) > params.getAccuracy()) {
            xNew = getNextApproximation(xPrevious, xNext);
            xPrevious = xNext;
            xNext = xNew;
=======
        while(! stopper.shouldStop(xPrevious, xNext, function.getValue(xNext), i)) {
            xNew = getNextApproximation(xPrevious, xNext);
            xPrevious = xNext;
            xNext = xNew;
            ++i;
>>>>>>> 2fa125236316dcd7bdf131695d8fd2164d94a15f
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
