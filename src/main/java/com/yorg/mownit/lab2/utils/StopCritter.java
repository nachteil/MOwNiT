package com.yorg.mownit.lab2.utils;

import com.yorg.mownit.lab2.Experiment;
import org.ejml.simple.SimpleMatrix;

/**
 * Created by yorg on 25.03.15.
 */
public interface StopCritter {

    public boolean shouldStop(Experiment e);
    public double getNorm(SimpleMatrix m);
    public String getDescription();
}
