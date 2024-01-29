
package com.snuggy.nr.chapter07;


/**
 * Recommended generator for everyday use. The period is
 * 1:8  1019.
 */
public class Ranq1 implements Random {

    private long v;

    public Ranq1(long j) {
        v = 4101842887655102017L;
        v ^= j;
        v = getAsLong();
    }

    public long getAsLong() {
        v ^= v >>> 21;
        v ^= v << 35;
        v ^= v >>> 4;
        return v * 2685821657736338717L;
    }
}
