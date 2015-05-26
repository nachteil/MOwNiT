package com.yorg.mownit.commons;

public class Range {

    public Range(double start, double end) {
        this.start = start;
        this.end = end;
    }

    public static Range getRangeX(Point2D [] points) {
        double minx, maxx;
        minx = maxx = points[0].getX();
        for(Point2D point : points) {
            minx = point.getX() < minx ? point.getX() : minx;
            maxx = point.getX() > maxx ? point.getX() : maxx;
        }
        return new Range(minx, maxx);
    }

    public static Range getRangeY(Point2D [] points) {
        double miny, maxy;
        miny = maxy = points[0].getY();
        for(Point2D point : points) {
            miny = point.getY() < miny ? point.getY() : miny;
            maxy = point.getY() > maxy ? point.getY() : maxy;
        }
        return new Range(miny, maxy);
    }

    public final double start;
    public final double end;
}
