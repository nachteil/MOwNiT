package com.yorg.mownit.lab1;

import com.yorg.mownit.lab1.math.DoubleMatrix;
import com.yorg.mownit.lab1.math.FloatMatrix;

public class TriDiagonalSolver {

    public FloatMatrix solve(FloatMatrix A, FloatMatrix b) {

        int n = A.getNumCols();
        float [] u = new float [n];
        float [] l = new float [n];

        u[0] = A.get(0, 0);

        for(int i = 1; i < n; ++i) {
            l[i] = A.get(i, i-1) / u[i-1];
            u[i] = A.get(i, i) - l[i] * A.get(i-1, i);
        }

        float [] y = new float [n];
        float [] x = new float [n];

        y[0] = b.get(0, 0);
        for(int i = 1; i < n; ++i) {
            y[i] = b.get(i, 0) - l[i] * y[i-1];
        }

        x[n-1] =  y[n-1] / u[n-1];
        FloatMatrix result = new FloatMatrix(n, 1);
        result.set(n-1, 0, x[n-1]);
        for(int i = n-2; i >= 0; --i) {
            x[i] = (y[i] - A.get(i, i+1) * x[i+1]) / u[i];
            result.set(i, 0, x[i]);
        }
        return result;
    }
    
    public DoubleMatrix solve(DoubleMatrix A, DoubleMatrix b) {

        int n = A.getNumCols();
        double [] u = new double [n];
        double [] l = new double [n];

        u[0] = A.get(0, 0);

        for(int i = 1; i < n; ++i) {
            l[i] = A.get(i, i-1) / u[i-1];
            u[i] = A.get(i, i) - l[i] * A.get(i-1, i);
        }

        double [] y = new double [n];
        double [] x = new double [n];

        y[0] = b.get(0, 0);
        for(int i = 1; i < n; ++i) {
            y[i] = b.get(i, 0) - l[i] * y[i-1];
        }

        x[n-1] =  y[n-1] / u[n-1];
        DoubleMatrix result = new DoubleMatrix(n, 1);
        result.set(n-1, 0, x[n-1]);
        for(int i = n-2; i >= 0; --i) {
            x[i] = (y[i] - A.get(i, i+1) * x[i+1]) / u[i];
            result.set(i, 0, x[i]);
        }
        return result;
    }

}
