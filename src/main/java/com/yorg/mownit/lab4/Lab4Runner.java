package com.yorg.mownit.lab4;

import com.yorg.mownit.lab4._2d.Point;
import com.yorg.mownit.lab4.interpolation.Interpolator;
import com.yorg.mownit.lab4.interpolation.LagrangeInterpolator;
import com.yorg.mownit.lab4.polynomial.Function;
import com.yorg.mownit.lab4.polynomial.Polynomial;
import lombok.SneakyThrows;

import javax.swing.*;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

public class Lab4Runner extends JFrame {

    public static final double a = 1.0d/3.0d;
    public static final double b = 3.0d;
    public static final int N = 20;
    public static final double k = 3;

    private void createGUI() {
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(800, 600);
    }

    public double getMaxDiff(Polynomial intepolation, Function f) {

        double diff = intepolation.getValue(a) - f.getValue(a);
        diff = Math.abs(diff);

        for(double d = a; d <= b + 1e-5; d += 0.001d) {
            double curr = intepolation.getValue(d) - f.getValue(d);
            curr = Math.abs(diff);
            diff = curr > diff ? curr : diff;
        }

        return diff;
    }

    @SneakyThrows
    private void createPlot() {

        Function f = (x) -> x * Math.sin(k * Math.PI / x);

        Interpolator lagrangeInterpolator = new LagrangeInterpolator();
        double step = (b-a) / (N-1);
        List<Point> points = new ArrayList<>(N);
        for(double x = a; x <= b + 1e-5; x += step) {
            points.add(new Point(x, f.getValue(x)));
        }

        Polynomial approximation = lagrangeInterpolator.getInterpolationPolynomial(points.toArray(new Point[points.size()]));

        System.out.println("Starting interpolation on regular nodes");
        PrintWriter out = new PrintWriter(new FileOutputStream("interpolation_reg.csv"));
        for(Point p : points) {
            out.println(p.getX() + "," + approximation.getValue(p.getX()));
        }
        out.flush();

        out = new PrintWriter(new FileOutputStream("function.csv"));
        for(double d = a; d <= b; d += 0.01) {
            out.println(d + "," + approximation.getValue(d));
        }
        out.flush();
        System.out.println("Maximal difference between function and interpolant: " + getMaxDiff(approximation, f));

        points = new Chebyschev().getChebychevNodes(N, f);
        approximation = lagrangeInterpolator.getInterpolationPolynomial(points.toArray(new Point[points.size()]));

        System.out.println("Starting interpolation on Chebyschev nodes");
        out = new PrintWriter(new FileOutputStream("interpolation_cheb.csv"));
        for(Point p : points) {
            out.println(p.getX() + "," + approximation.getValue(p.getX()));
        }
        out.flush();
        System.out.println("Maximal difference between function and interpolant: " + getMaxDiff(approximation, f));


    }

    public Lab4Runner() {
//        createGUI();
        createPlot();
    }

    public static void main(String[] args) {

        new Lab4Runner();

    }

}
