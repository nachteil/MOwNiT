package com.yorg.mownit.lab1.util;

import com.yorg.mownit.lab1.math.DoubleMatrix;
import com.yorg.mownit.lab1.math.FloatMatrix;

public interface Exercise {

    FloatMatrix getFloatMatrix(int size);
    DoubleMatrix getDoubleMatrix(int size);

    float getFloatMatrixElement(int i, int j);
    double getDoubleMatrixElement(int i, int j);

    String getDescription();
}
