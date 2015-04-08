package com.yorg.mownit.lab2.math;

import org.ejml.simple.SimpleMatrix;

/**
 * Created by yorg on 25.03.15.
 */
public class MatrixUtils {

    public static boolean isJacobiConvergent(SimpleMatrix A) {

        int limit = Math.min(A.numCols(), A.numRows());

        for(int i = 0; i < limit; ++i) {
            double a_ii = A.get(i, i);
            double sum = 0.0d;
            for(int j = 0; j < A.numCols(); ++j ) {
                if(i != j) {
                    sum += Math.abs(A.get(i, j));
                }
            }
            if(Math.abs(a_ii) <= sum) {
                return false;
            }
        }

        return true;
    }
}
