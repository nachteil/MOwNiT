package com.yorg.mownit.lab3.utils;

import com.yorg.mownit.lab3.utils.Compartment;
import lombok.Getter;

public class SolveParams {

    @Getter private final Compartment compartment;
    @Getter private final double acccuracy;
    @Getter private final int maxIterationCount;

    public SolveParams(Compartment compartment, double acccuracy, int maxIterationCount) {

        this.compartment = compartment;
        this.acccuracy = acccuracy;
        this.maxIterationCount = maxIterationCount;
    }
}
