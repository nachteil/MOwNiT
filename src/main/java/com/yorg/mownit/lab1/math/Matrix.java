package com.yorg.mownit.lab1.math;

public interface Matrix {

    int getNumCols();

    int getNumRows();

    void set(int row, int col, double value);

    double get(int row, int col);

    void performGaussianElimination();
}
