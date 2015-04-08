package com.yorg.mownit.lab2;

import com.yorg.mownit.lab2.solvers.Solver;
import com.yorg.mownit.lab2.utils.StopCritter;
import com.yorg.mownit.lab2.utils.Timer;
import lombok.Getter;
import org.ejml.simple.SimpleMatrix;

public class Experiment {

    public static final int MAX_ITERATIONS = 10000;

    @Getter
    private double lastIterationProgress = Double.MAX_VALUE;

    @Getter
    private int iterationCount;

    private StopCritter stopCritter;
    private Solver solver;

    @Getter
    private SimpleMatrix A;
    @Getter
    private SimpleMatrix b;

    private Timer timer;

    public Experiment(Solver solver, StopCritter stopCritter) {

        this.solver = solver;
        this.stopCritter = stopCritter;

        this.A = solver.getA();
        this.b = solver.getB();

        this.iterationCount = 1;
        this.timer = new Timer();
    }

    public void start() {

        timer.start();

        while(!stopCritter.shouldStop(this)) {

            timer.onSimulationResume();

            ++iterationCount;
            SimpleMatrix next = solver.getResult(iterationCount);
            SimpleMatrix previous = solver.getResult(iterationCount - 1);

            timer.onSimulationPause();

            lastIterationProgress = stopCritter.getNorm(next.minus(previous));
        }

        timer.stop();
    }

    public SimpleMatrix getResult() {

        if(iterationCount < MAX_ITERATIONS) {
            return solver.getResult(iterationCount);
        } else {
            return null;
        }
    }

    public double getDistanceFromOriginal() {
        return stopCritter.getNorm(A.mult(solver.getResult(iterationCount)).minus(b));
    }

    public long getTotalDuration() {
        return timer.getTotalTime();
    }

    public long getSimulationDuration() {
        return timer.getSimulationTime();
    }
}
