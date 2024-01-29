package com.snuggy.nr.chapter07;

import static com.snuggy.nr.util.Static.int_vec;
import static java.lang.Math.floor;

public class BitParallelBinomialDeviateGenerator extends Binomialdev {

    // Structure for binomial deviates.
    private final boolean flipped;
    private double p;
    private double pb;
    private int n;
    private long uz;
    private long uo;
    private long rltp;
    private final int[] pbits = int_vec(5);
    private Random ran;

    public BitParallelBinomialDeviateGenerator(final int n, final double pp, final long seed) {
        // Constructor arguments are n, p, and a random sequence seed.
        this.ran = new Ran(seed);

        // Wlog p < 0.5, otherwise flip
        this.flipped = pp <= 0.5;
        this.p = flipped ? 1.0 - pp : pp;
        this.n = n;
        int j;

        pb = p;

        uz = 0;
        uo = 0xffffffffffffffffL;
        rltp = 0;
        for (j = 0; j < 5; j++) {
            pbits[j] = 1 & (int) (pb *= 2.);
        }
        pb -= floor(pb); // Leading bits of p (above) and remaining
    }

    public int dev() {
        int j, k;
        long unfin = uo; // Mark all bits as 'unfinished.'
        for (j = 0; j < 5; j++) { // Compare with first five bits of p.
            long diff = unfin & (ran.getAsLong() ^ (pbits[j] != 0 ? uo : uz)); // Mask
            // of
            // diff.
            if (pbits[j] != 0) {
                rltp |= diff; // Set bits to 1, meaning ran < p.
            } else {
                rltp = rltp & ~diff; // Set bits to 0, meaning ran > p.
            }
            unfin = unfin & ~diff; // Update unfinished status.
        }
        k = 0; // Now we just count the events.
        for (j = 0; j < n; j++) {
            if ((unfin & 1) != 0) {
                if (ran.getAsDouble() < pb) {
                    ++k;
                }
            } // Clean up unresolved cases,
            else {
                if ((rltp & 1) != 0) {
                    ++k;
                }
            } // or use bit answer.
            unfin >>= 1;
            rltp >>= 1;
        }
        return flipped ? this.n - k : k;
    }

}
