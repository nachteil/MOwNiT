package com.yorg.mownit.lab3.solvers;

import com.yorg.mownit.lab3.utils.Result;
import com.yorg.mownit.lab3.utils.SolveParams;
import com.yorg.mownit.lab3.utils.Stopper;

public interface ISolver {

    public Result solve(SolveParams params, Stopper stopper);

}
