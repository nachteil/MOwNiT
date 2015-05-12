package com.yorg.mownit.lab7.integration;

import com.yorg.mownit.commons.Function;
import com.yorg.mownit.commons.Range;
import com.yorg.mownit.commons.Timer;
import lombok.Getter;

public abstract class AbstractIntegrator {

    @Getter
//    protected static final double integrationStep = 1e-6;
    protected final int degree;

    public AbstractIntegrator(int degree) {
        this.degree = degree;
    }

    protected abstract Quadrature getQuadrature();

    public double integrate(Function f, Range range, int numberOfIntervals) {

//        int numberOfSteps = (int) ((range.end - range.start) / integrationStep);
        Quadrature quadrature = getQuadrature();

        double currentIntervalStart;
        double currentIntervalEnd;
        double subInterval;

        double integrateSumValue = 0.0;

        double intervalWidth = (range.end - range.start) / numberOfIntervals;

        Timer timer = new Timer();
        timer.start();

        for(int i = 0; i < numberOfIntervals; ++i) {

            currentIntervalStart = range.start + i * intervalWidth;
            currentIntervalEnd = currentIntervalStart + intervalWidth;

            subInterval = (currentIntervalEnd - currentIntervalStart) / (degree);
            double [] quadratureValues = new double[degree+1];
            for(int j = 0; j <= degree; ++j) {
                double x = currentIntervalStart + subInterval * j;
                quadratureValues[j] = f.getValue(x);
            }
            integrateSumValue += quadrature.getValue(currentIntervalStart, currentIntervalEnd, f, quadratureValues);
        }

//        System.out.println("Integration took " + timer.stopAndGet() + " ms");

        return integrateSumValue;
    }

    protected interface Quadrature {
        double getValue(double a, double b, Function f, double... functionValues);
    }
}
