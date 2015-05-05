package com.yorg.mownit.lab4;

import com.yorg.mownit.commons.Function;
import com.yorg.mownit.commons.Point2D;

import java.util.ArrayList;
import java.util.List;

public class Chebyschev {

    public List<Point2D> getChebychevNodes(int N, Function f) {

        List<Point2D> result = new ArrayList<>(N);
        for(int j = 1; j <= N; ++j) {
            double originalX = Math.cos(Math.PI * ((2.0d * j - 1.0d)/(2.0d * N)));
            double scaledX = (originalX + 1.0d) / 2.0d;
            scaledX *= (3.0d - 1.0d/3.0d);
            scaledX += 1.0d/3.0d;
            result.add(new Point2D(scaledX, f.getValue(scaledX)));
        }
        return result;
    }
}
