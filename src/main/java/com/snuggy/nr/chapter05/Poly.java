package com.snuggy.nr.chapter05;

import com.snuggy.nr.util.*;
import java.util.function.DoubleUnaryOperator;

public class Poly implements DoubleUnaryOperator {

    // Polynomial function object that binds a reference to a vector of
    // coefficients.
    private final double[] c;

    public Poly(final double[] cc) {
        c = (cc);
    }

    public double applyAsDouble(final double x) {
        // Returns the value of the polynomial at x.
        int j;
        double p = c[j = c.length - 1];
        while (j > 0)
            p = p * x + c[--j];
        return p;
    }

}
