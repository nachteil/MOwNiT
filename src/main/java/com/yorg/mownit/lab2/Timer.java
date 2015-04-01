package com.yorg.mownit.lab2;

import lombok.Getter;

/**
 * Created by yorg on 25.03.15.
 */
public class Timer {

    private long startTime;
    private long simulationTime = 0L;

    private long lastSimulationResume;
    private long lastSimulationPause;

    private long totalTime;
    private long lastStop;
    private long endTime;

    boolean started = false;
    boolean finished = false;

    public void start() {
        started = true;
        this.startTime = System.currentTimeMillis();
    }

    public void stop() {
        this.endTime = System.currentTimeMillis();
        finished = true;
    }

    public void onSimulationResume() {
        lastSimulationResume = System.currentTimeMillis();
    }

    public void onSimulationPause() {
        lastSimulationPause = System.currentTimeMillis();
        simulationTime +=  lastSimulationPause - lastSimulationResume;
    }

    public long getTotalTime() {
        return endTime - startTime;
    }

    public long getSimulationTime() {
        return simulationTime;
    }
}
