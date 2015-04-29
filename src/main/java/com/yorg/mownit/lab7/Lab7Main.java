package com.yorg.mownit.lab7;

import com.yorg.mownit.commons.Function;
import com.yorg.mownit.commons.Range;
import com.yorg.mownit.lab7.integration.AbstractIntegrator;
import com.yorg.mownit.lab7.integration.GaussIntegrator;
import com.yorg.mownit.lab7.integration.NewtonCotesIntegrator;

public class Lab7Main {

    public void run() {

        AbstractIntegrator integrator = new GaussIntegrator(2);
        Function f = Math::sin;

        Range range = new Range(0, Math.PI);
        double integrate = integrator.integrate(f, range);

        System.out.println("Integral of sin(x) in [0;PI] = " + integrate);
    }

    public static void main(String[] args) {

        new Lab7Main().run();

    }

}
