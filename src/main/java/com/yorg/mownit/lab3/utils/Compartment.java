package com.yorg.mownit.lab3.utils;

import lombok.Getter;

public class Compartment {

    @Getter private final double start;
    @Getter private final double end;

    public Compartment(double start, double end) {
        this.start = start;
        this.end = end;
    }
}
