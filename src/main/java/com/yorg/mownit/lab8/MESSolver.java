package com.yorg.mownit.lab8;

import com.yorg.mownit.commons.Function;
import com.yorg.mownit.commons.Point2D;
import com.yorg.mownit.commons.Range;
import com.yorg.mownit.commons.plot.Plot;
import com.yorg.mownit.lab1.TriDiagonalSolver;
import lombok.Getter;
import lombok.Setter;
import org.ejml.simple.SimpleMatrix;

public class MESSolver {

    public Plot getSolutionPlot(int numOfSteps, MESParams params) {

        SimpleMatrix A = new SimpleMatrix(numOfSteps, numOfSteps);
        SimpleMatrix y;
        SimpleMatrix b = new SimpleMatrix(numOfSteps, 1);

        double h = (params.endValue.getX() - params.startValue.getX()) / (1 + numOfSteps);

        Function p = params.p;
        Function q = params.q;
        Function r = params.r;

        Point2D boundaryStart = params.startValue;
        Point2D boundaryEnd = params.endValue;

        // initialize main matrix A
        for(int i = 0; i < numOfSteps; ++i) {
            for(int j = 0; j < numOfSteps; ++j) {
                A.set(i, j, 0.0);
            }
        }

        for(int i = 0; i < numOfSteps; ++i) {
            double xi = boundaryStart.getX() + (i+1)*h;
            double val = -4.0 + 2.0*h*h*q.getValue(xi);
            A.set(i, i, val);
        }

        for(int i = 0; i < numOfSteps-1; ++i) {
            double xi = boundaryStart.getX() + (i+1)*h;
            double val = 2.0 + p.getValue(xi) * h;
            A.set(i, i+1, val);
        }

        for(int i = 1; i < numOfSteps; ++i) {
            double xi = boundaryStart.getX() + (i+1)*h;
            double val = 2.0 - p.getValue(xi) * h;
            A.set(i, i-1, val);
        }

        // initialize vector b
        for(int i = 0; i < numOfSteps; ++i) {
            double xi = boundaryStart.getX() + h*(i+1);
            double bVal = -2.0 * r.getValue(xi) * h*h;
            b.set(i, 0, bVal);
        }

        double xi = boundaryStart.getX() + h;
        double bVal = -2.0 * r.getValue(xi) * h*h - (2.0 - h*p.getValue(xi))*boundaryStart.getY();
        b.set(0, 0, bVal);

        xi = boundaryEnd.getX() - h;
        bVal = -2.0 * r.getValue(xi) * h*h - (2.0 - h*p.getValue(xi))*boundaryEnd.getY();
        b.set(numOfSteps-1, 0, bVal);

        TriDiagonalSolver solver = new TriDiagonalSolver();
        y = solver.solve(A, b);

        Point2D [] solutionPoints = new Point2D [numOfSteps+2];
        solutionPoints[0] = new Point2D(boundaryStart.getX(), boundaryStart.getY());
        solutionPoints[numOfSteps+1] = new Point2D(boundaryEnd.getX(), boundaryEnd.getY());
        for(int i = 1; i <= numOfSteps; ++i) {
            solutionPoints[i] = new Point2D(boundaryStart.getX()+h*i, y.get(i-1, 0));
        }

        Range mainRange = new Range(boundaryStart.getX(), boundaryEnd.getX());
        Plot plot = Plot.newPlot()
                .withFunctionPlotRange(mainRange)
                .withXRange(getPlottingRangeWithMargins(mainRange))
                .withPlotFileName("mes.png")
                .withTitle("MES Solution")
                .withXLabel("x")
                .withYLabel("y")
                .build();

        plot.addPointsPlot(solutionPoints, "MES Solution points");
        return plot;
    }

    private Range getPlottingRangeWithMargins(Range range) {
        double width = range.end - range.start;
        double from = range.start - width * 0.2;
        double to = range.end + width * 0.2;
        return new Range(from, to);
    }

    public static class MESParams {
        @Getter @Setter Point2D startValue;
        @Getter @Setter Point2D endValue;
        @Getter @Setter Function p;
        @Getter @Setter Function q;
        @Getter @Setter Function r;
    }
}
