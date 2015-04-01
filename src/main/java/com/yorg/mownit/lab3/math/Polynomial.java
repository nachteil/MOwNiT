package com.yorg.mownit.lab3.math;

import lombok.Getter;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class Polynomial implements Function<Double> {

    private List<PolynomialElement> elements;

    private final Comparator<PolynomialElement> comparator = (o1, o2) -> o1.n - o2.n;

    @Getter
    private int degree;

    public Polynomial(List<PolynomialElement> elements) {

        this.elements = new ArrayList<>();
        this.elements.addAll(elements);
    }

    public Polynomial(double ... coefficients) {
        this.elements = new ArrayList<>(coefficients.length);
        int n = coefficients.length - 1;
        for(double c : coefficients) {
            this.elements.add(new PolynomialElement(c, n--));
        }
    }

    private void calculateDegree() {

        degree = elements.size() - 1;
        elements.sort(comparator);

        while(degree >= 0 && elements.get(degree).a < 10e-10) {
            --degree;
        }
    }

    public Polynomial getFirstDerivative() {

        List<PolynomialElement> derivativeElements = this.elements.stream()
                .filter(e -> e.a > 10e-10 && e.n > 0)
                .map(e -> new PolynomialElement(e.a * e.n, e.n - 1))
                .collect(Collectors.toList());

        return new Polynomial(derivativeElements);
    }

    @Override
    public Double getValue(double x) {

        double sum = 0.0d;
        for(PolynomialElement e : elements) {
            sum += e.a * Math.pow(x, e.n);
        }
        return sum;
    }

    public static class PolynomialElement {

        @Getter private final int n;
        @Getter private final double a;

        public PolynomialElement(double a, int n) {
            this.a = a;
            this.n = n;
        }
    }

    @Override
    public String toString() {
        elements.sort(comparator);
        int n = elements.size() - 1;

        StringBuilder builder = new StringBuilder();

        for(int i = n; i >= 0; --i) {
            PolynomialElement e = elements.get(i);
            if(e.a > 10e-8) {
                builder.append(e.a);
                if(e.n > 0) {
                    builder.append(" x^").append(e.n);
                }
                builder.append(" +");
            }
        }
        if(builder.length() > 2) {
            builder.replace(builder.length() - 2, builder.length(), "");
        }
        return builder.toString();
    }
}
