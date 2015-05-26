package com.yorg.mownit.lab9;

import com.yorg.mownit.commons.Function;

public class Lab9Main {

    private final int numStepsX = 50;
    private final int numStepsT = 700;


    public void run() {

        ProblemParameters parameters = getParameters();
        System.out.println(parameters.getR());

        HeatEquation2DMESSolver solver = new HeatEquation2DMESSolver(numStepsX, numStepsT);
        Animation animation = solver.getExplicitSolutionAsAnimation(parameters);
        animation.display();
    }

    private ProblemParameters getParameters() {

        double A = 4.0;
        double omega = 1.0;

        double tc = 4.0 * Math.PI;
        double l = 10;

        double h = l / (numStepsX-1);
        double k = tc / (numStepsT-1);

        Function initialCondition = (x) -> 0;
        Function boundaryConditionAt0 = (t) -> A * t;
        Function boundaryConditionAtL = (t) -> Math.sin(omega * t);

        ProblemParameters parameters = ProblemParameters.builder()
                .boundaryConditionAt0(boundaryConditionAt0)
                .boundaryConditionAtL(boundaryConditionAtL)
                .h(h)
                .k(k)
                .Tc(tc)
                .initialCondition(initialCondition)
                .L(l)
                .build();

        return parameters;
    }

    public static void main(String[] args) {

        new Lab9Main().run();
    }

}
