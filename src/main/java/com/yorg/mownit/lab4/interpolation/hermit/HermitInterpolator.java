package com.yorg.mownit.lab4.interpolation.hermit;

import com.yorg.mownit.commons.Function;
import com.yorg.mownit.commons.Point2D;
import com.yorg.mownit.lab4.interpolation.Interpolator;
import com.yorg.mownit.lab4.polynomial.Polynomial;
import com.yorg.mownit.lab4.polynomial.PolynomialBase;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class HermitInterpolator implements Interpolator {

    private final Function derivative;

    public HermitInterpolator(Function derivative) {
        this.derivative = derivative;
    }

    @Override
    public Polynomial getInterpolationPolynomial(Point2D... points) {

        DividedDifference difference = new DividedDifference(derivative, points);

        List<Polynomial> sumList = new ArrayList<>(points.length);
        List<Polynomial> productList = new ArrayList<>(points.length);

        for(int j = 1; j < points.length; ++j) {

            productList.clear();

            double coefficient = difference.get(0, j);

            for(int k = 0; k < j; ++k) {
                productList.add(new PolynomialBase(1.0, points[k].getX()));
            }
            Polynomial product = Polynomial.multiply(productList.toArray(new Polynomial[productList.size()]));
            product = Polynomial.multiply(product, coefficient);

            sumList.add(product);
        }

        Polynomial constantFactor = new PolynomialBase(0.0, 0.0, points[0].getY());
        sumList.add(constantFactor);

        return Polynomial.sum(sumList.toArray(new Polynomial[sumList.size()]));
    }
}
