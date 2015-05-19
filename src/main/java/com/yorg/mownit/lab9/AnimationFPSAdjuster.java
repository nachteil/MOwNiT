package com.yorg.mownit.lab9;

public class AnimationFPSAdjuster {

    private final int desiredFPS;
    long lastRefreshTimeInMillis;

    public AnimationFPSAdjuster(int FPS) {
        this.desiredFPS = FPS;
    }

    public void notifyUpdate() {
        lastRefreshTimeInMillis = System.currentTimeMillis();
    }

    public void pauseIfNeeded() {
        long waitMillisBetweenRefresh = (long) (1.0 / desiredFPS * 1000);
        long currMillis = System.currentTimeMillis();
        long millisSinceLastRefresh = currMillis - lastRefreshTimeInMillis;
        if(millisSinceLastRefresh < waitMillisBetweenRefresh) {
            try {
                Thread.sleep(waitMillisBetweenRefresh - millisSinceLastRefresh);
            } catch (InterruptedException e) {
                // I've heard there are countries, where one may have his hands cut off for empty catch block...
            }
        }
    }

}
