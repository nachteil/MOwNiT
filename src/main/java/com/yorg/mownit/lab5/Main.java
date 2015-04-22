package com.yorg.mownit.lab5;

import com.yorg.mownit.commons.Function;
import com.yorg.mownit.lab5.interpolation.CubicInterpoler;
import org.ejml.simple.SimpleMatrix;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class Main {

    public static void main(String[] args) throws IOException {

        double [] x = new double[10];
        double [] y = new double[10];

        for (int i = 0; i < 10; ++i) {
            x[i] = 1.1 * i;
            y[i] = Math.sin(x[i]);
        }

        Function inter = new CubicInterpoler().getClampedSplineInterpolant(x, y, 0.0, 0.0);
        System.out.println("Got interpoler");
        PrintWriter csvWriter = new PrintWriter(new FileWriter("spline.csv"));

        for(double p = 0.001; p < 12; p += 0.11) {
            csvWriter.println(p + "," + inter.getValue(p));
        }
        csvWriter.flush();
        SimpleMatrix a = new SimpleMatrix();

    }

}
