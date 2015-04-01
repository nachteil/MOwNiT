package com.yorg.mownit.lab2;

import com.yorg.mownit.lab2.math.VectorUtils;
import lombok.Getter;
import org.ejml.simple.SimpleMatrix;

/**
 * Created by yorg on 25.03.15.
 */
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
        return "Experiment stops when the euclidian norm between two subsequent iterations' effects drops below " + stopLimit;
    }
}
