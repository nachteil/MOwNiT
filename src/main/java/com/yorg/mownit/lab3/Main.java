package com.yorg.mownit.lab3;

import com.yorg.mownit.lab3.math.Function;
import com.yorg.mownit.lab3.math.Polynomial;
import com.yorg.mownit.lab3.solvers.AbstractSolver;
import com.yorg.mownit.lab3.solvers.SecantSolver;
import com.yorg.mownit.lab3.utils.Compartment;
import com.yorg.mownit.lab3.utils.Result;
import com.yorg.mownit.lab3.utils.SolveParams;
import lombok.SneakyThrows;

import java.io.InputStream;
import java.util.Properties;

public class Main {

    private static final String PRECISSION_PROPERTY_KEY = "accuracy";
    private static final String MAX_ITERATIONS_PROPERTY_KEY = "maxIterations";

    public static void main(String ... args) {

        Properties properties = getProperties();

        double precission = Double.valueOf(properties.getProperty(PRECISSION_PROPERTY_KEY));
        int maxIterations = Integer.valueOf(properties.getProperty(MAX_ITERATIONS_PROPERTY_KEY));

        Function<Double> identityFunction = (x) -> x * x - 1;
        Compartment compartment = new Compartment(0, 2);
        SolveParams params = new SolveParams(compartment, precission, maxIterations);

        AbstractSolver solver = new SecantSolver(identityFunction);
        Result result = solver.solve(params);

        if(result.isCorrect()) {
            System.out.println(result.getResult());
        } else {
            System.out.println("No result has been found");
        }

        System.out.println(new Polynomial(3, 2, 8));
    }

    @SneakyThrows
    private static Properties getProperties() {

        Properties properties = new Properties();
        InputStream inputStream = Main.class.getClassLoader().getResourceAsStream("lab3/lab3.properties");
        properties.load(inputStream);
        return properties;
    }

}
