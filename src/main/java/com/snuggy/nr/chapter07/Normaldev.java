
package com.snuggy.nr.chapter07;

import static com.snuggy.nr.util.Static.*;
import static java.lang.Math.*;

public class Normaldev {

    // Structure for normal deviates.
    private double mu, sig;
    private Random ran;

    public Normaldev(final double mu, final double sig, final long seed ) {
        this.ran = new Ran(seed);
        this.mu = mu;
        this.sig = sig;
    }

    public double dev() {
        // Return a normal deviate.
        double u, v, x, y, q;
        do {
            u = ran.getAsDouble();
            v = 1.7156 * (ran.getAsDouble() - 0.5);
            x = u - 0.449871;
            y = abs(v) + 0.386595;
            q = SQR(x) + y * (0.19600 * y - 0.25472 * x);
        } while (q > 0.27597 && (q > 0.27846 || SQR(v) > -4. * log(u) * SQR(u)));
        return mu + sig * v / u;
    }

}
