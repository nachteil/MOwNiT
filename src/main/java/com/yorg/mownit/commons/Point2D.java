package com.yorg.mownit.commons;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public class Point2D {

    @Getter private final double x;
    @Getter private final double y;

    @Override
    public String toString() {
        return String.format("(%.2f,%.2f)", x, y);
    }
}
