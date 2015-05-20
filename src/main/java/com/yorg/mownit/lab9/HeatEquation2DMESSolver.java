package com.yorg.mownit.lab9;

import com.yorg.mownit.commons.Point2D;
import com.yorg.mownit.lab1.TriDiagonalSolver;
import lombok.SneakyThrows;
import org.ejml.simple.SimpleMatrix;

public class HeatEquation2DMESSolver {

    private final int NUM_STEPS_X;
    private final int NUM_STEPS_T;

    private ProblemParameters currentParams;
    private SimpleMatrix A = null;

    public HeatEquation2DMESSolver(int numStepsX, int numStepsT) {

        this.NUM_STEPS_X = numStepsX;
        this.NUM_STEPS_T = numStepsT;
    }

    @SneakyThrows
    public Animation getExplicitSolutionAsAnimation(ProblemParameters parameters) {

        this.currentParams = parameters;
        Animation resultAnimation = new Animation(parameters);
        double r = parameters.getR();

        Point2D[] solutionPointsForPreviousTime = getInitialPoints(parameters);

        for(int timeStep = 0; timeStep < NUM_STEPS_T; ++timeStep) {

            Point2D [] solutionForCurrentTime = solveExplicitlyForCurrentTime(solutionPointsForPreviousTime, timeStep);

            resultAnimation.addFrame(solutionForCurrentTime);
            solutionPointsForPreviousTime = solutionForCurrentTime;
        }

        return resultAnimation;
    }

    public Animation getImplicitSolutionAsAnimation(ProblemParameters parameters) {

        this.currentParams = parameters;
        Animation resultAnimation = new Animation(parameters);

        Point2D [] solutionForPreviousTime = getInitialPoints(parameters);

        for(int timeStep = 0; timeStep < NUM_STEPS_T; ++timeStep) {

            Point2D [] solutionForCurrentTime = solveImplicitlyForCurrentTime(solutionForPreviousTime, timeStep);

            resultAnimation.addFrame(solutionForCurrentTime);
            solutionForPreviousTime = solutionForCurrentTime;
        }

        return resultAnimation;
    }

    private Point2D[] solveImplicitlyForCurrentTime(Point2D[] solutionForPreviousTime, int timeStep) {

        Point2D [] solutionPointsForCurrentTime = getSolutionArrayInitializedWithBoundaryConiditions(timeStep);

        double r = currentParams.getR();

        SimpleMatrix b = new SimpleMatrix(NUM_STEPS_X-2, NUM_STEPS_X-2);
        for(int i = 0; i < NUM_STEPS_X-2; ++i) {
            b.set(i, i, solutionForPreviousTime[i+1].getY());
        }

        double first = b.get(0, 0);
        double currentTime = getT(timeStep);
        first += r * currentParams.getBoundaryConditionAt0().getValue(currentTime);
        b.set(0, 0, first);

        double last = b.get(NUM_STEPS_X-3, NUM_STEPS_X-3);
        last += currentParams.getBoundaryConditionAtL().getValue(currentTime);
        b.set(NUM_STEPS_X-3, NUM_STEPS_X-3, last);

        SimpleMatrix A = getAMatrix();
        TriDiagonalSolver solver = new TriDiagonalSolver();
        SimpleMatrix solution = solver.solve(A, b);


    }

    private SimpleMatrix getAMatrix() {

        if(A == null) {
            initA();
        }
        return A;
    }

    private void initA() {
        A = new SimpleMatrix(NUM_STEPS_X-2, NUM_STEPS_X-2);

        double r = currentParams.getR();
        double a = (1.0 + 2.0*r);

        for(int i = 0; i < NUM_STEPS_X-2; ++i) {
            A.set(i, i, a);
        }

        for(int i = 0; i < NUM_STEPS_X-3; ++i) {
            A.set(i, i+1, -r);
            A.set(i+1, i, -r);
        }
    }

    private Point2D [] solveExplicitlyForCurrentTime(Point2D[] solutionPointsForPreviousTime, int timeStep) {
        Point2D[] solutionPointsForCurrentTime = getSolutionArrayInitializedWithBoundaryConiditions(timeStep);

        for(int spaceStep = 1; spaceStep < NUM_STEPS_X-1; ++spaceStep) {
            double x = getX(spaceStep);
            double y = getSolutionForCurrentTimeAndSpace(solutionPointsForPreviousTime, spaceStep);
            solutionPointsForCurrentTime[spaceStep] = new Point2D(x, y);
        }
        return solutionPointsForCurrentTime;
    }

    private Point2D[] getSolutionArrayInitializedWithBoundaryConiditions(int timeStep) {
        Point2D [] solutionPointsForCurrentTime = new Point2D[NUM_STEPS_X];
        solutionPointsForCurrentTime[0] = getValueAtBoundary0(timeStep);
        solutionPointsForCurrentTime[NUM_STEPS_X-1] = getValueAtBoundaryL(timeStep);
        return solutionPointsForCurrentTime;
    }

    private Point2D getValueAtBoundary0(int timeStep) {
        double currentTime = getT(timeStep);
        double currentValueAtBoundary0 = currentParams.getBoundaryConditionAt0().getValue(currentTime);
        return new Point2D(0, currentValueAtBoundary0);
    }

    private Point2D getValueAtBoundaryL(int timeStep) {
        double currentTime = getT(timeStep);
        double currentValueAtBoundaryL = currentParams.getBoundaryConditionAtL().getValue(currentTime);
        return new Point2D(currentParams.getL(), currentValueAtBoundaryL);
    }

    private double getSolutionForCurrentTimeAndSpace(Point2D[] solutionPointsForPreviousTime, int spaceStep) {
        double r = currentParams.getR();
        double y = (1.0-2.0* r) * solutionPointsForPreviousTime[spaceStep].getY();
        y += r * solutionPointsForPreviousTime[spaceStep-1].getY();
        y += r * solutionPointsForPreviousTime[spaceStep+1].getY();
        return y;
    }

    private Point2D[] getInitialPoints(ProblemParameters parameters) {
        Point2D [] solutionPointsForPreviousTime = new Point2D[NUM_STEPS_X];
        for(int i = 0; i < NUM_STEPS_X; ++i) {
            double x = getX(i);
            double y = parameters.getInitialCondition().getValue(x);
            solutionPointsForPreviousTime[i] = new Point2D(x, y);
        }
        return solutionPointsForPreviousTime;
    }

    private double getX(int numberOfStep) {
        return currentParams.getH() * numberOfStep;
    }

    private double getT(int numberOfStep) {
        return currentParams.getK() * numberOfStep;
    }

}
