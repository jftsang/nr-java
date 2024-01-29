
package com.snuggy.nr.chapter07;


/**
 * Implementation of the highest quality recommended generator. The
 * constructor is called with an integer seed and creates an instance of
 * the generator. The member functions int64, doub, and int32 return the
 * next values in the random sequence, as a variable type indicated by
 * their names. The period of the generator is 3^138 - 1057.
 */
public class Ran implements Random {
    private long u;
    private long v = 4101842887655102017L;
    private long w = 1;

    public Ran(final long j) {
        // Constructor. Call with any integer seed (except value of v above).
        u = j ^ v;
        getAsLong();
        v = u;
        getAsLong();
        w = v;
        getAsLong();
    }

    public long getAsLong() {
        u = u * 2862933555777941757L + 7046029254386353087L;
        v ^= v >>> 17;
        v ^= v << 31;
        v ^= v >>> 8;
        w = 4294957665L * (w & 0xffffffffL) + (w >>> 32);
        long x = u ^ (u << 21);
        x ^= x >>> 35;
        x ^= x << 4;
        return (x + v) ^ w;
    }

}
