package com.yorg.mownit.lab9;

import com.yorg.mownit.commons.Function;

public class Lab9Main {

    public void run() {

        double A = 2.0;
        double omega = 1.0;

        int numStepsX = 30;
        int numStepsT = 1000;

        double tc = 0.3;
        double l = 1;

        double h = l / (numStepsX-1);
        double k = tc / (numStepsT-1);

        Function initialCondition = (x) -> x;
        Function boundaryConditionAt0 = (t) -> 1;
        Function boundaryConditionAtL = (t) -> 1;

        ProblemParameters parameters = ProblemParameters.builder()
                .boundaryConditionAt0(boundaryConditionAt0)
                .boundaryConditionAtL(boundaryConditionAtL)
                .h(h)
                .k(k)
                .Tc(tc)
                .initialCondition(initialCondition)
                .L(l)
                .build();

        System.out.println(parameters.getR());

        HeatEquation2DMESSolver solver = new HeatEquation2DMESSolver(numStepsX, numStepsT);
        Animation animation = solver.getSolutionAsAnimation(parameters);
        animation.display();
    }

    public static void main(String[] args) {

        new Lab9Main().run();
    }

}