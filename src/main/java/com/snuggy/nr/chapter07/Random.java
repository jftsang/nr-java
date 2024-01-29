
package com.snuggy.nr.chapter07;

import java.util.function.DoubleSupplier;
import java.util.function.IntSupplier;
import java.util.function.LongSupplier;

public interface Random extends DoubleSupplier, IntSupplier, LongSupplier {

    /**
     * Generate random double from 0 to 1.
     * @return random double from 0 to 1
     */
    default double getAsDouble() {
        long arg = getAsLong();
        // Make sure it's in 0 to 1 rather than -0.5 to 0.5.
        // Save the right bit and shift to the right.
        int bit = (int) (arg & 0x0000000000000001L);
        arg >>>= 1;
        double r = 5.42101086242752217E-20 * arg;
        r *= 2.0;
        r += 5.42101086242752217E-20 * bit;
        return r;
    }

    /**
     * Return 32-bit random integer.
     */
    default int getAsInt() {
        return (int) getAsLong();
    }
}
