package com.yorg.mownit.lab8;

import com.yorg.mownit.commons.Function;
import com.yorg.mownit.commons.Point2D;
import com.yorg.mownit.commons.Range;
import com.yorg.mownit.commons.plot.Plot;

public class Lab8Main {

    private static final double k = 2;
    private static final double m = 3;

    private static final DERightSideFunction rightSideFunction =
            (x, y) -> k * m * y * Math.sin(m*x) + k*k * m * Math.sin(m*x) * Math.cos(m*x);

    private static final Function exactSolution =
            (x) -> Math.exp(-k * Math.cos(m*x)) - k * Math.cos(m*x) + 1.0;


    private Lab8Main() {

        int [] sizes = {10, 25, 50, 100, 200, 500};
        for(int i : sizes) {
            plotKuta(i);
            plotEuler(i);
            plotMES(i);
        }
    }

    private void plotMES(int numberOfSteps) {

        double k = 3.0;
        double m = 3.0;

        double start = 0;
        double end = (2.0 * Math.PI + k) / m;

        Function mesExactSolution = (x) -> -k*Math.sin(m*x)+k*x;

        Point2D boundaryStart = new Point2D(start, mesExactSolution.getValue(start));
        Point2D boundaryEnd = new Point2D(end, mesExactSolution.getValue(end));

        MESSolver solver = new MESSolver();
        MESSolver.MESParams params = new MESSolver.MESParams();

        Function p = (x) -> 0.0;
        Function q = (x) -> m*m;
        Function r = (x) -> -m*m*k*x;

        params.setStartValue(boundaryStart);
        params.setEndValue(boundaryEnd);
        params.setP(p);
        params.setQ(q);
        params.setR(r);

        Plot plot = solver.getSolutionPlot(numberOfSteps, params);
        plot.addFunctionPlot(mesExactSolution, "Exact solution");

        plot.plotWithWindow();
    }

    private void plotKuta(int numberOfSteps) {

        double start = 3.0 * Math.PI / 2.0;
        double end = 3.0 * Math.PI;
        Range range = new Range(start, end);
        double initialValue = exactSolution.getValue(range.start);

        AbstractSolver solver = new RungeKutaSolver(rightSideFunction, numberOfSteps);
        Plot plot = solver.getSolutionPlot(range, initialValue);

        plot.addFunctionPlot(exactSolution, "Exact solution");
        plot.plotWithWindow();
    }

    private void plotEuler(int numberOfSteps) {

        double start = 3.0 * Math.PI / 2.0;
        double end = 3.0 * Math.PI;
        Range range = new Range(start, end);
        double initialValue = exactSolution.getValue(range.start);

        AbstractSolver solver = new EulerSolver(rightSideFunction, numberOfSteps);
        Plot plot = solver.getSolutionPlot(range, initialValue);

        plot.addFunctionPlot(exactSolution, "Exact solution");
        plot.plotWithWindow();
    }


    public static void main(String[] args) {
        new Lab8Main();
    }


}
