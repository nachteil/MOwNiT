package com.yorg.mownit.lab2;

import org.ejml.simple.SimpleMatrix;

/**
 * Created by yorg on 25.03.15.
 */
public class Lab2Runner {

    public static void main(String[] args) {

        double [][] exampleA = {
                { 4, -1, -0.2, 2 },
                { -1, 5, 0, -2 },
                { 0.2, 1, 10, -1 },
                { 0, -2, -1, 4 }
        };

        double [] b = { 30, 0, -10, 5 };

        SimpleMatrix A = new SimpleMatrix(4, 4);
        SimpleMatrix x = new SimpleMatrix(4, 1);

        for(int i = 0; i < 4; ++i) {
            for(int j = 0; j < 4; ++j) {
                A.set(i, j, exampleA[i][j] );
            }
            x.set(i, 0, b[i]);
        }

        JacobiSolver solver = new JacobiSolver(A, x);

        System.out.println("Convergent? " + solver.isJacobiConvergent());

        for(int i = 1; i <= 2; ++i) {
            System.out.println(solver.getResult(i));
        }
    }
}
