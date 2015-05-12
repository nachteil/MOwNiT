package com.yorg.mownit.commons.functioncomp;

import com.yorg.mownit.commons.Function;
import com.yorg.mownit.commons.Range;
import com.yorg.mownit.commons.functioncomp.FunctionComparator;

public class MaxDiffComparator implements FunctionComparator {

    public static double step = 1e-8;

    private final Range range;

    public MaxDiffComparator(Range range) {
        this.range = range;
    }

    @Override
    public double compare(Function a, Function b) {

        double result = a.getValue(range.start) - b.getValue(range.start);
        result = Math.abs(result);

        for(double x = range.start; x <= range.end; x += step) {
            double curr = Math.abs(a.getValue(x) - b.getValue(x));
            result = curr > result ? curr : result;
        }

        return result;
    }
}
