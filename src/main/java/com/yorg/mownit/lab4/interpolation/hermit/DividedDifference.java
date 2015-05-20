package com.yorg.mownit.lab4.interpolation.hermit;

import com.yorg.mownit.commons.Function;
import com.yorg.mownit.commons.Point2D;
import com.yorg.mownit.commons.datasources.DoubledPointSource;
import com.yorg.mownit.lab4.interpolation.Interpolator;

import java.util.HashMap;
import java.util.Map;

public class DividedDifference {

    private final Function derivative;

    private final Map<Integer, Map<Integer, Double>> cache = new HashMap<>();
    private final Point2D [] points;

    public DividedDifference(Function derivative, Point2D [] points) {

        this.derivative = derivative;
        this.points = points;
    }

    public double get(int from, int to) {
        return getInternal(from, to);
    }

    private double getInternal(int from, int to) {

        double result;

        Map<Integer, Double> fromMap = cache.get(from);
        if(fromMap == null) {
            fromMap = new HashMap<>();
            cache.put(from, fromMap);
        }

        Double cacheResult = fromMap.get(to);
        if(cacheResult == null) {

            if(from == to) {
                result = points[from].getY();
            } else if(points[from].getX() == points[to].getX()) {
                result = derivative.getValue(points[from].getX());
            } else {
                result = (get(from+1, to) - get(from, to-1)) / (points[to].getX() - points[from].getX());
            }

            cacheResult = result;
            fromMap.put(to, result);
        }

        return cacheResult;
    }

}
