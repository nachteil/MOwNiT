package com.yorg.mownit.lab6;

import com.yorg.mownit.commons.Point2D;
import com.yorg.mownit.commons.plot.DataSeries;
import com.yorg.mownit.commons.plot.Plot;

import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {

        List<Point2D> points = new ArrayList<>();

        for(double x = 0.0; x < 10; x += 0.1) {
            Point2D point = new Point2D(x, Math.sin(x * 0.6) + 1);
            points.add(point);
        }

        Approximator approximator = new Approximator(points.toArray(new Point2D [points.size()]), 8);
        approximator.discretePolynomialApproximation();

        Plot plot = Plot.newPlot()
                .withTitle("Approximation")
                .withType(Plot.Type.LINESPOINTS)
                .withXLabel("x")
                .withYLabel("y")
                .withXRange(0.0, 11.0)
                .withYRange(-2.0, 4.0)
                .build();

        DataSeries series = plot.newDataSeries("Points");
        series.addData(points.toArray(new Point2D [points.size()]));

        System.out.println(plot.plot());
    }
}
