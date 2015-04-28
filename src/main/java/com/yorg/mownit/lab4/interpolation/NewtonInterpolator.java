package com.yorg.mownit.lab4.interpolation;

import com.yorg.mownit.commons.Point2D;
import com.yorg.mownit.lab4.polynomial.Polynomial;
import com.yorg.mownit.lab4.polynomial.PolynomialBase;

import java.util.Arrays;

public class NewtonInterpolator implements Interpolator {

    @Override
    public Polynomial getInterpolationPolynomial(Point2D ... points) {

        Polynomial [] bases = new Polynomial[points.length];

        for(int i = 0; i < points.length; ++i) {
            bases[i] = new PolynomialBase(1.0, points[i].getX());
        }

        double y0 = points[0].getY();
        Polynomial p = new PolynomialBase(0.0, 0.0, y0);

        for(int i = 1; i < points.length; ++i) {

            double x_k = points[i].getX();
            double y_k = points[i].getY();

            Polynomial product = Polynomial.multiply(Arrays.copyOfRange(bases, 0, i));
            double c = (y_k - p.getValue(x_k)) / product.getValue(x_k);
            p = Polynomial.sum(p, Polynomial.multiply(product, c));
        }

        return p;
    }
}
