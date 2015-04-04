package com.yorg.mownit.lab3.utils;

import lombok.Getter;

public class SolveParams {

    @Getter private final Compartment compartment;
    @Getter private final double accuracy;
    @Getter private final int maxIterationCount;

    public SolveParams(Compartment compartment, double accuracy, int maxIterationCount) {

        this.compartment = compartment;
        this.accuracy = accuracy;
        this.maxIterationCount = maxIterationCount;
    }
}
