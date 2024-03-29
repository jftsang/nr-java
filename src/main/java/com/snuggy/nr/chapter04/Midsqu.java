
package com.snuggy.nr.chapter04;

import static java.lang.Math.*;

import com.snuggy.nr.util.*;
import java.util.function.DoubleUnaryOperator;

public class Midsqu<T extends DoubleUnaryOperator> extends MidpointQuadrature<T> {

    // This routine is an exact replacement for midpnt, except that
    // it allows for an inverse square-root singularity in the integrand
    // at the upper limit bb.
    private double borig;

    public double func(final double x) throws NRException {
        return 2.0 * x * super.funk.applyAsDouble(borig - x * x); // Effect the change of
                                                         // variable.
    }

    public Midsqu(final T funcc, final double aa, final double bb) {
        super(funcc, aa, bb);
        borig = (bb);
        super.a = 0;
        super.b = sqrt(bb - aa);
    }

}
