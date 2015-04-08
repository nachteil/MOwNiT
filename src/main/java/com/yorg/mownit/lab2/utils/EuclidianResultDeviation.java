package com.yorg.mownit.lab2.utils;

import com.yorg.mownit.lab2.Experiment;
import com.yorg.mownit.lab2.math.VectorUtils;
import org.ejml.simple.SimpleMatrix;

public class EuclidianResultDeviation implements StopCritter {

    private final double precission;

    public EuclidianResultDeviation(double precission) {
        this.precission = precission;
    }

    @Override
    public boolean shouldStop(Experiment e) {

        SimpleMatrix A = e.getA();
        SimpleMatrix b = e.getB();

        SimpleMatrix vecDiff = A.mult(e.getResult()).minus(b);
        return VectorUtils.euclidianNorm(vecDiff) < precission;
    }

    @Override
    public double getNorm(SimpleMatrix m) {
        return VectorUtils.euclidianNorm(m);
    }

    @Override
    public String getDescription() {
        return "Result between calculated and real result";
    }
}
