package com.yorg.mownit.lab7.integration;

public class NewtonCotesIntegrator extends AbstractIntegrator {

    public NewtonCotesIntegrator(int degree) {
        super(degree);
    }

    @Override
    protected Quadrature getQuadrature() {

        Quadrature quadrature;

        switch(degree) {
            case 1:
                quadrature = (a, b, f, vals) -> {
                    if(vals.length != 2) {
                        throw new IllegalArgumentException("Quadrature of 2nd degree expects two function values");
                    }
                    return (b-a)/2 * (vals[1] + vals[0]);
                };
                break;
            case 2:
                quadrature = (a, b, f, vals) -> {
                    if(vals.length != 3) {
                        throw new IllegalArgumentException("Quadrature of 3rd degree expects three function values");
                    }
                    return (b-a)/6 * (vals[2] + 4.0 * vals[1] + vals[0]);
                };
                break;
            case 3:
                quadrature = (a, b, f, vals) -> {
                    if(vals.length != 4) {
                        throw new IllegalArgumentException("Quadrature of 4th degree expects four function values");
                    }
                    return (b-a)/8 * (vals[3] + 3.0 * vals[2] + 3.0 * vals[1] + vals[0]);
                };
                break;
            case 4:
                quadrature = (a, b, f, vals) -> {
                    if(vals.length != 5) {
                        throw new IllegalArgumentException("Quadrature of 5th degree expects five function values");
                    }
                    return (b-a)/90 * (7.0 * vals[4] + 32.0 * vals[3] + 12.0 * vals[2] + 32.0 * vals[1] + 7.0 * vals[0]);
                };
                break;

            default:
                throw new IllegalArgumentException("Only supports Newton-Cotes qudratures up to degree 4");
        }

        return quadrature;
    }

}
