package com.yorg.mownit.lab9;

import com.yorg.mownit.commons.Function;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class ProblemParameters {

    private Function initialCondition;
    private Function boundaryConditionAt0;
    private Function boundaryConditionAtL;
    private double Tc;
    private double L;
    private double k;
    private double h;

    public double getR() {
        return k / (h*h);
    }
}
