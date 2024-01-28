
package com.snuggy.nr.chapter04;

import static com.snuggy.nr.chapter04.Static.*;

import com.snuggy.nr.util.*;
import java.util.function.DoubleBinaryOperator;
import java.util.function.DoubleUnaryOperator;

public class NRf1 implements DoubleUnaryOperator {

    private DoubleUnaryOperator y1;
    private DoubleUnaryOperator y2;
    private NRf2 f2;

    public NRf1(final DoubleUnaryOperator yy1, final DoubleUnaryOperator yy2,
        final DoubleBinaryOperator z1, final DoubleBinaryOperator z2) {
        y1 = (yy1);
        y2 = (yy2);
        f2 = new NRf2(z1, z2);
    }

    public NRf2 f2() {
        return f2;
    }

    public double applyAsDouble(final double x) {
        // This is H of eq. (4.8.5).
        f2.f3().set_xsav(x);
        try {
            return qgaus(f2, y1.applyAsDouble(x), y2.applyAsDouble(x));
        } catch (NRException e) {
            throw new RuntimeException(e);
        }
    }

}
