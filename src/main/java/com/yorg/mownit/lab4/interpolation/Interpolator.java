package com.yorg.mownit.lab4.interpolation;

import com.yorg.mownit.commons.Point2D;
import com.yorg.mownit.lab4.polynomial.Polynomial;

public interface Interpolator {

    public Polynomial getInterpolationPolynomial(Point2D... points);
}
