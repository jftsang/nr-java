package com.snuggy.nr.chapter07;

import static com.snuggy.nr.chapter06.Static.*;
import static com.snuggy.nr.util.Static.*;
import static java.lang.Math.*;

import com.snuggy.nr.util.*;

/**
 * Structure for binomial deviates.
 */
public abstract class Binomialdev implements IntDeviateGenerator {
    public static Binomialdev make(final int n, final double p, final long seed)
        throws NRException {
        if (n <= 64) {
            return new BitParallelBinomialDeviateGenerator(n, p, seed);
        } else if (n * p < 30. || n * (1. - p) < 30.) {
            return new PrecomputedCdfBinomialDeviateGenerator(n, p, seed);
        } else {
            return new RatioOfUniformsBinomialDeviateGenerator(n, p, seed);
        }
    }

    public abstract int dev();

}
