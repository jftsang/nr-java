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

public class RatioOfUniformsBinomialDeviateGenerator extends Binomialdev {

    // Structure for binomial deviates.
    private final boolean flipped;
    private double p;
    private double pb;
    private double np;
    private double glnp;
    private double plog;
    private double pclog;
    private double sq;
    @SuppressWarnings("unused")
    private double expnp;
    private int n;
    private final double[] logfact = doub_vec(1024);

    private final Random ran;

    public RatioOfUniformsBinomialDeviateGenerator(final int n, final double pp, final long seed)
        throws NRException {
        // Constructor arguments are n, p, and a random sequence seed.
        this.ran = new Ran(seed);

        // Wlog p < 0.5, otherwise flip
        this.flipped = pp <= 0.5;
        this.p = flipped ? 1.0 - pp : pp;
        this.n = n;
        int j;

        pb = p;

        np = this.n * p;
        glnp = gammln(this.n + 1.);
        plog = log(p);
        pclog = log(1. - p);
        sq = sqrt(np * (1. - p));
        if (this.n < 1024) {
            for (j = 0; j <= this.n; j++) {
                logfact[j] = gammln(j + 1.);
            }
        }
    }


    public int dev() {
        // Return a binomial deviate.
        int j, k, kl, km;
        double y, u, v, u2, v2, b;
        for (; ; ) {
            u = 0.645 * ran.getAsDouble();
            v = -0.63 + 1.25 * ran.getAsDouble();
            v2 = SQR(v);
            // Try squeeze for fast rejection:
            if (v >= 0.) {
                if (v2 > 6.5 * u * (0.645 - u) * (u + 0.2)) {
                    continue;
                }
            } else {
                if (v2 > 8.4 * u * (0.645 - u) * (u + 0.1)) {
                    continue;
                }
            }
            k = Int(floor(sq * (v / u) + np + 0.5));
            if (k < 0) {
                continue;
            }
            u2 = SQR(u);
            // Try squeeze for fast acceptance:
            if (v >= 0.) {
                if (v2 < 12.25 * u2 * (0.615 - u) * (0.92 - u)) {
                    break;
                }
            } else {
                if (v2 < 7.84 * u2 * (0.615 - u) * (1.2 - u)) {
                    break;
                }
            }
            try {
                // Only when we must.
                b = sq * exp(
                    glnp + k * plog + (n - k) * pclog - (n < 1024 ? logfact[k] + logfact[n - k]
                        : gammln(k + 1.) + gammln(n - k + 1.)));
            } catch (NRException e) {
                throw new RuntimeException(e);
            }
            if (u2 < b) {
                break;
            }
        }

        return flipped ? this.n - k : k;
    }

}
