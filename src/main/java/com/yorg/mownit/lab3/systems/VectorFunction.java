package com.yorg.mownit.lab3.systems;

import org.ejml.simple.SimpleMatrix;

public interface VectorFunction {

    public SimpleMatrix getValue(SimpleMatrix x);
}
