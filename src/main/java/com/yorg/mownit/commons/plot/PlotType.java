package com.yorg.mownit.commons.plot;

public enum PlotType {

    LINES("lines"), POINTS("points"), LINESPOINTS("linespoints"), SMOOTH_LINES("points smooth csplines"), BIG_POINTS("points ps 3");

    private final String s;

    PlotType(String s) {
        this.s = s;
    }

    @Override
    public String toString() {
        return s;
    }
}