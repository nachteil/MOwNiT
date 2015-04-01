package com.yorg.mownit.lab3.utils;

public interface Result {

    public double getResult();
    public boolean isCorrect();

    public static class CorrectResult implements Result {

        private double result;

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
