package com.yorg.mownit.commons.plot;

import com.yorg.mownit.commons.Point2D;
import lombok.Getter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class DataSeries {

    @Getter public final List<Point2D> points;
    @Getter public final String description;
    @Getter private final PlotType type;

    DataSeries(String description, PlotType type) {
        this.description = description;
        this.type = type;
        points = new ArrayList<>();
    }

    public void addData(Point2D ... point2Ds) {
        Collections.addAll(points, point2Ds);
    }


}
