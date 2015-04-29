package com.yorg.mownit.commons;

public class Timer {

    private long savedTime;
    private boolean on;

    public Timer() {
        on = false;
    }

    public void start() {
        on = true;
        savedTime = System.currentTimeMillis();
    }

    public long stopAndGet() {

        if(!on) {
            throw new IllegalStateException("Cannot stop when not started");
        }

        on = false;
        return System.currentTimeMillis() - savedTime;
    }

}
