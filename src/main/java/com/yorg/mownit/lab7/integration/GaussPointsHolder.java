package com.yorg.mownit.lab7.integration;

import lombok.AllArgsConstructor;
import lombok.Getter;

public class GaussPointsHolder {

    private static final int NUM_POINTS = 5;

    private static final GaussPoint [][] points = new GaussPoint[NUM_POINTS][];

    public static final GaussPoint [] getPoints(int degree) {
        return points[degree-1];
    }

    static {

        for(int i = 0; i < NUM_POINTS; ++i) {
            points[i] = new GaussPoint[i+1];
        }

        points[0][0] = new GaussPoint(0, 2);

        points[1][0] = new GaussPoint(sqrt(1.0/3.0),1);
        points[1][1] = new GaussPoint(-sqrt(1.0/3.0),1);

        points[2][0] = new GaussPoint(0.0, 8.0/9.0);
        points[2][1] = new GaussPoint(sqrt(3.0/5.0), 5.0/9.0);
        points[2][2] = new GaussPoint(-sqrt(3.0/5.0), 5.0/9.0);

        points[3][0] = new GaussPoint(sqrt(3.0/7.0 - 2.0/7.0 * sqrt(6.0/5.0)), (18 + sqrt(30)) / 36.0);
        points[3][1] = new GaussPoint(-sqrt(3.0/7.0 - 2.0/7.0 * sqrt(6.0/5.0)), (18 + sqrt(30)) / 36.0);
        points[3][2] = new GaussPoint(sqrt(3.0/7.0 + 2.0/7.0 * sqrt(6.0/5.0)),(18 - sqrt(30)) / 36.0);
        points[3][3] = new GaussPoint(-sqrt(3.0/7.0 + 2.0/7.0 * sqrt(6.0/5.0)),(18 - sqrt(30)) / 36.0);

        points[4][0] = new GaussPoint(0, 128.0/225.0);
        points[4][1] = new GaussPoint(1.0 / 3.0 * sqrt(5.0 - 2.0 * sqrt(10.0/7)), (332.0 + 13 * sqrt(70.0))/900.0);
        points[4][2] = new GaussPoint(-1.0 / 3.0 * sqrt(5.0 - 2.0 * sqrt(10.0/7)), (332.0 + 13 * sqrt(70.0))/900.0);
        points[4][3] = new GaussPoint(1.0 / 3.0 * sqrt(5.0 + 2.0 * sqrt(10.0/7)), (332.0 - 13 * sqrt(70.0))/900.0);
        points[4][4] = new GaussPoint(-1.0 / 3.0 * sqrt(5.0 + 2.0 * sqrt(10.0/7)), (332.0 - 13 * sqrt(70.0))/900.0);

    }

    public static double sqrt(double x) {
        return Math.sqrt(x);
    }

    @AllArgsConstructor @Getter
    public static class GaussPoint {

        private final double x;
        private final double w;
    }

}
