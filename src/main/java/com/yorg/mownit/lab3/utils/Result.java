package com.yorg.mownit.lab3.utils;

import lombok.Getter;
import lombok.Setter;

public interface Result {

    public double getResult();
    public boolean isCorrect();

    public int getNumIterations();
    public double getLastFunctionValue();
    public double getLastXDiff();

    public static class CorrectResult implements Result {

        private double result;

        @Getter @Setter private double lastFunctionValue;
        @Getter @Setter private int numIterations;
        @Getter @Setter public double lastXDiff;

        public CorrectResult(double result) {
            this.result = result;
        }

        @Override
        public boolean isCorrect() {
            return true;
        }

        @Override
        public double getResult() {
            return result;
        }
    }

    public static class IncorrectResult implements Result {

        @Getter @Setter private double lastFunctionValue;
        @Getter @Setter private int numIterations;
        @Getter @Setter public double lastXDiff;

        private final String errorMessage;

        public IncorrectResult(String errorMessage) {
            this.errorMessage = errorMessage;
        }

        @Override
        public boolean isCorrect() {
            return false;
        }

        @Override
        public double getResult() {
            throw new UnsupportedOperationException("There's no correct result. Reason: " + errorMessage);
        }


    }

}
