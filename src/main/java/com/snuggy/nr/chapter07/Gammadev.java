
package com.snuggy.nr.chapter07;

import static com.snuggy.nr.util.Static.*;
import static java.lang.Math.*;

import com.snuggy.nr.util.*;

public class Gammadev implements DoubleDeviateGenerator {
    private final Normaldev normaldev;

    // Structure for gamma deviates.
    double alpha, oalph, beta;
    double a1, a2;

    public Gammadev(final double alpha, final double beta, final long seed) throws NRException {
        // Constructor arguments are ?, ?, and a random sequence seed.

        normaldev = new Normaldev(0., 1., seed);

        this.alpha = alpha;
        oalph = alpha;
        this.beta = beta;
        if (this.alpha <= 0.)
            throw new NRException("bad alph in Gammadev");

        if (this.alpha < 1.)
            this.alpha += 1.;

        a1 = this.alpha - 1. / 3.;
        a2 = 1. / sqrt(9. * a1);
    }

    public double dev() {
        // Return a gamma deviate by the method of Marsaglia and Tsang.
        double u, v, x;
        do {
            do {
                x = normaldev.dev();
                v = 1. + a2 * x;
            } while (v <= 0.);
            v = v * v * v;
            u = normaldev.dev();
        } while (u > 1. - 0.331 * SQR(SQR(x)) && log(u) > 0.5 * SQR(x) + a1 * (1. - v + log(v))); // Rarely
                                                                                                  // evaluated.
        if (alpha == oalph)
            return a1 * v / beta;
        else { // Case where ? < 1, per Ripley.
            do
                u = normaldev.dev();
            while (u == 0.);
            return pow(u, 1. / oalph) * a1 * v / beta;
        }
    }

}
