package com.yorg.mownit.lab6;

import com.yorg.mownit.commons.Point2D;
import com.yorg.mownit.lab1.math.DoubleMatrix;
import com.yorg.mownit.lab4.polynomial.Function;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.ejml.simple.SimpleMatrix;

import java.util.HashMap;
import java.util.Map;

@AllArgsConstructor
public class Approximator {

    private final Point2D [] points;
    private final int approximationOrder;

    private final Map<Integer, Double> sMap = new HashMap<>();
    private final Map<Integer, Double> tMap = new HashMap<>();

    public Function discretePolynomialApproximation() {

        int m = approximationOrder;

        SimpleMatrix S = new SimpleMatrix(m+1, m+1);
        SimpleMatrix t = new SimpleMatrix(m+1, 1);

        for(int row = 0; row <= m; ++row) {
            for(int col = 0; col <= m; ++col) {
                S.set(row, col, getS(row+col));
            }
        }

        for(int row = 0; row <= m; ++row) {
            t.set(row, 0, getT(row));
        }

        SimpleMatrix A = S.solve(t);

        return x -> {
            double sum = 0;
            for(int i = 0; i <= m; ++i) {
                sum += A.get(i, 0) * Math.pow(x, i);
            }
            return sum;
        };
    }

    private double getS(int k) {

        Double s = sMap.get(k);

        if(s == null) {
            s = 0.0;
            for (Point2D point : points) {
                s += Math.pow(point.getX(), k);
            }
            sMap.put(k, s);
        }
        return s;
    }

    private double getT(int k) {

        Double t = tMap.get(k);

        if(t == null) {
            t = 0.0;
            for (Point2D point : points) {
                t += point.getY() * Math.pow(point.getX(), k);
            }
            tMap.put(k, t);
        }

        return t;
    }

}
