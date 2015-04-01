package com.yorg.mownit.lab3.solvers;

import com.yorg.mownit.lab3.utils.Result;
import com.yorg.mownit.lab3.utils.SolveParams;
import com.yorg.mownit.lab3.utils.Stopper;

/**
 * Created by yorg on 01.04.15.
 */
public interface ISolver {

    public Result solve(SolveParams params, Stopper stopper);

}
