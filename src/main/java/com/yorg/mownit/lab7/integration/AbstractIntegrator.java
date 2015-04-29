package com.yorg.mownit.lab7.integration;

import com.yorg.mownit.commons.Function;
import com.yorg.mownit.commons.Range;
import com.yorg.mownit.commons.Timer;
import lombok.Getter;

public abstract class AbstractIntegrator {

    @Getter
    protected static final double integrationStep = 1e-6;
    protected final int degree;

    public AbstractIntegrator(int degree) {
        this.degree = degree;
    }

    protected abstract Quadrature getQuadrature();

    public double integrate(Function f, Range range) {

        int numberOfSteps = (int) ((range.end - range.start) / integrationStep);
        Quadrature quadrature = getQuadrature();

        double currentIntervalStart;
        double currentIntervalEnd;
        double subInterval;

        double integrateSumValue = 0.0;

        Timer timer = new Timer();
        timer.start();

        for(int i = 0; i < numberOfSteps; ++i) {

            currentIntervalStart = range.start + i * integrationStep;
            currentIntervalEnd = currentIntervalStart + integrationStep;

            subInterval = (currentIntervalEnd - currentIntervalStart) / (degree);
            double [] quadratureValues = new double[degree+1];
            for(int j = 0; j <= degree; ++j) {
                double x = currentIntervalStart + subInterval * j;
                quadratureValues[j] = f.getValue(x);
            }
            integrateSumValue += quadrature.getValue(currentIntervalStart, currentIntervalEnd, f, quadratureValues);
        }

        System.out.println("Integration took " + timer.stopAndGet() + " ms");

        return integrateSumValue;
    }

    protected interface Quadrature {
        double getValue(double a, double b, Function f, double... functionValues);
    }
}
