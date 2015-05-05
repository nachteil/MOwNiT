package com.yorg.mownit.lab7.integration;

import com.yorg.mownit.commons.Function;

public class GaussIntegrator extends AbstractIntegrator {

    public GaussIntegrator(int n) {
        super(n);
    }

    @Override
    protected Quadrature getQuadrature() {

        Quadrature quadrature;

        ArgScaler scaler = (a, b, t) -> (b + a + t * (b-a)) / 2.0;

        quadrature = (a, b, f, vals) -> {

            double integralSum = 0.0;
            GaussPointsHolder.GaussPoint [] points = GaussPointsHolder.getPoints(degree);
            for(int i = 0; i < degree; ++i) {
                double weight = points[i].getW();
                double scaledArg = scaler.scale(a, b, points[i].getX());
                double fVal = f.getValue(scaledArg);
                integralSum += weight * fVal;
            }
            integralSum *= (b-a)/2;

            return integralSum;
        };

        return quadrature;

    }

    private interface ArgScaler {
        double scale(double a, double b, double t);
    }
}
