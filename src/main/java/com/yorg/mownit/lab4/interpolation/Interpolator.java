package com.yorg.mownit.lab4.interpolation;

import com.yorg.mownit.lab4._2d.Point;
import com.yorg.mownit.lab4.polynomial.Polynomial;

public interface Interpolator {

    public Polynomial getInterpolationPolynomial(Point ... points);
}
