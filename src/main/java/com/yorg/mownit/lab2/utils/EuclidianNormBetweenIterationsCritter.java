package com.yorg.mownit.lab2.utils;

import com.yorg.mownit.lab2.Experiment;
import com.yorg.mownit.lab2.math.VectorUtils;
import lombok.Getter;
import org.ejml.simple.SimpleMatrix;

public class EuclidianNormBetweenIterationsCritter implements StopCritter {

    @Getter
    private final double stopLimit;

    public EuclidianNormBetweenIterationsCritter(double stopLimit) {
        this.stopLimit = stopLimit;
    }

    @Override
    public boolean shouldStop(Experiment e) {
        return e.getLastIterationProgress() < stopLimit;
    }

    @Override
    public double getNorm(SimpleMatrix m) {
        return VectorUtils.euclidianNorm(m);
    }

    @Override
    public String getDescription() {
        return String.format("Experiment stops when the euclidian norm between two subsequent iterations' effects drops below %2.3e", stopLimit);
    }
}
