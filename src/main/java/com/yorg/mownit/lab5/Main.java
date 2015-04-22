package com.yorg.mownit.lab5;

import com.yorg.mownit.commons.Function;
import com.yorg.mownit.lab5.interpolation.CubicInterpoler;
import org.ejml.simple.SimpleMatrix;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class Main {

    public static final double k = 3;

    public static void main(String[] args) throws IOException {

        Function f = (x) -> x * Math.sin(k * Math.PI / x);

        int N = 10;
        double [] x = new double[N];
        double [] y = new double[N];

        double start = 1.0/3.0;
        double end = 3.0;
        double step = (end-start) / N;

        double _x = start;
        for(int i = 0; i < N; ++i) {
            x[i] = _x;
            _x += step;
            y[i] = f.getValue(_x);
        }

        Function inter = new CubicInterpoler().getNaturalSplineInterpolant(x, y);
        PrintWriter csvWriter = new PrintWriter(new FileWriter("spline.csv"));

        for(double p = start; p < end; p += 0.011) {
            csvWriter.println(p + "," + inter.getValue(p) + "," + f.getValue(p));
        }
        csvWriter.flush();
        SimpleMatrix a = new SimpleMatrix();

    }

}
