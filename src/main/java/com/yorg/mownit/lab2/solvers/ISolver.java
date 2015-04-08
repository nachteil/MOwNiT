package com.yorg.mownit.lab2.solvers;

import org.ejml.simple.SimpleMatrix;

public interface ISolver {

    public SimpleMatrix getResult(int numberOfIterations);
}
