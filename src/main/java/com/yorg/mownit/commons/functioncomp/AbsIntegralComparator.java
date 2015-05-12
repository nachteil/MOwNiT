package com.yorg.mownit.commons.functioncomp;

import com.yorg.mownit.commons.Function;
import com.yorg.mownit.commons.Range;

public class AbsIntegralComparator implements FunctionComparator {

    public static double step = 1e-4;

    private final Range range;

    public AbsIntegralComparator(Range range) {
        this.range = range;
    }

    @Override
    public double compare(Function a, Function b) {

        double result = 0.0;

        for(double x = range.start; x <= range.end; x += step) {
            result += Math.abs(a.getValue(x) - b.getValue(x)) * step;
        }

        return result;

    }
}
