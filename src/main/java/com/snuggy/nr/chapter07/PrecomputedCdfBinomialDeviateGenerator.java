package com.snuggy.nr.chapter07;

import static com.snuggy.nr.chapter06.Static.gammln;
import static com.snuggy.nr.util.Static.Int;
import static com.snuggy.nr.util.Static.SQR;
import static com.snuggy.nr.util.Static.doub_vec;
import static com.snuggy.nr.util.Static.int_vec;
import static java.lang.Math.exp;
import static java.lang.Math.floor;
import static java.lang.Math.log;
import static java.lang.Math.sqrt;

import com.snuggy.nr.util.NRException;

public class PrecomputedCdfBinomialDeviateGenerator extends Binomialdev {

    // Structure for binomial deviates.
    private final boolean flipped;
    private double p;
    @SuppressWarnings("unused")
    private double expnp;
    private int n;
    private final double[] cdf = doub_vec(64);

    private final Random ran;

    public PrecomputedCdfBinomialDeviateGenerator(final int n, final double pp, final long seed)
        throws NRException {
        // Constructor arguments are n, p, and a random sequence seed.
        this.ran = new Ran(seed);

        // Wlog p < 0.5, otherwise flip
        this.flipped = pp <= 0.5;
        this.p = flipped ? 1.0 - pp : pp;
        this.n = n;

        precomputeCdf();

    }

    private void precomputeCdf() throws NRException {
        int j;
        cdf[0] = exp(n * log(1 - p));
        for (j = 1; j < 64; j++) {
            cdf[j] = cdf[j - 1]
                + exp(
                gammln(n + 1.) - gammln(j + 1.) - gammln(n - j + 1.) + j * log(p) + (
                    n - j) * log(1. - p));
        }
    }

    public int dev() {
        // Return a binomial deviate.
        int k, kl, km;
        double y;
        y = ran.getAsDouble();
        kl = -1;
        k = 64;
        while (k - kl > 1) {
            km = (kl + k) / 2;
            if (y < cdf[km]) {
                k = km;
            } else {
                kl = km;
            }
        }

        return flipped ? n - k : k;
    }

}
