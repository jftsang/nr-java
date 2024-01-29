
package com.snuggy.nr.chapter07;

/**
 * Backup generator if Ranq1 has too short a period and Ran is too slow.
 * The period is 8:5  1037.
 */
public class Ranq2 implements Random {

    private long v, w;

    public Ranq2(long j) {
        v = 4101842887655102017L;
        w = 1;
        v ^= j;
        w = getAsLong();
        v = getAsLong();
    }

    public long getAsLong() {
        v ^= v >>> 17;
        v ^= v << 31;
        v ^= v >>> 8;
        w = 4294957665L * (w & 0xffffffffL) + (w >>> 32);
        return v ^ w;
    }

}
