package com.yorg.mownit.commons.datasources;

import com.yorg.mownit.commons.Function;
import com.yorg.mownit.commons.Point2D;

public interface PointSource {
    Point2D[] getFunctionValues(Function f, int numberOfPoints);
}
