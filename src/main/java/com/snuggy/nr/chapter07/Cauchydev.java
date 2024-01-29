
package com.snuggy.nr.chapter07;

import static com.snuggy.nr.util.Static.*;

/**
 * Structure for Cauchy deviates.
 */
public class Cauchydev implements DoubleDeviateGenerator {

    private Random ran;
    private double mu, sig;

    public Cauchydev(final double mu, final double sig, final long seed) {
        this.ran = new Ran(seed);
        this.mu = (mu);
        this.sig = (sig);
    }

    // Constructor arguments are , , and a random sequence seed.

    public double dev() {
        // Return a Cauchy deviate.
        double v1, v2;
        do { // Find a random point in the unit semicircle.
            v1 = 2.0 * ran.getAsDouble() - 1.0;
            v2 = ran.getAsDouble();
        } while (SQR(v1) + SQR(v2) >= 1. || v2 == 0.);
        return mu + sig * v1 / v2; // Ratio of its coordinates is the tangent of
                                   // a
    } // random angle.

}
