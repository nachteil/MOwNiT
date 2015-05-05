package com.yorg.mownit.lab3.utils;

import com.yorg.mownit.commons.Range;
import lombok.Getter;

public class SolveParams {

    @Getter private final Range range;
    @Getter private final double accuracy;
    @Getter private final int maxIterationCount;
    @Getter private final double startPoint;
    @Getter private final double secondStartPoint;

    public SolveParams(Range range, double accuracy, int maxIterationCount, double startPoint, double secondStartPoint) {

        this.range = range;
        this.accuracy = accuracy;
        this.maxIterationCount = maxIterationCount;
        this.startPoint = startPoint;
        this.secondStartPoint = secondStartPoint;
    }
}
