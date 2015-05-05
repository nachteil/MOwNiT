package com.yorg.mownit.lab4.interpolation;

import com.yorg.mownit.commons.Point2D;
import com.yorg.mownit.lab4.polynomial.Polynomial;
import com.yorg.mownit.lab4.polynomial.PolynomialBase;

public class LagrangeInterpolator implements Interpolator {

    @Override
    public Polynomial getInterpolationPolynomial(Point2D... points) {
        int n = points.length - 1;
        Polynomial [] sumElements = new Polynomial[n+1];
        for(int i = 0; i <= n; ++i) {
            sumElements[i] = getProduct(i, points);
            Polynomial.multiply(sumElements[i], points[i].getY());
        }
        return Polynomial.sum(sumElements);
    }

    private Polynomial getProduct(int i, Point2D[] points) {

        int n = points.length - 1;
        Polynomial [] factors = new Polynomial[n];
        int counter = 0;
        for(int j = 0; j <= n; ++j) {
            if(j != i) {
                double a = 1.0d;
                a /= (points[i].getX() - points[j].getX());
                factors[counter++] = new PolynomialBase(a, points[j].getX());
            }
        }
        return Polynomial.multiply(factors);
    }
}
