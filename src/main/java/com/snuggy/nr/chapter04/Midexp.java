
package com.snuggy.nr.chapter04;

import static java.lang.Math.*;

import com.snuggy.nr.util.*;
import java.util.function.DoubleUnaryOperator;

public class Midexp<T extends DoubleUnaryOperator> extends MidpointQuadrature<T> {

    // This routine is an exact replacement for midpnt, except that bb
    // is assumed to be infinite (value passed not actually used). It
    // is assumed that the function func decreases exponentially rapidly
    // at infinity.

    public double func(final double x) throws NRException {
        return super.funk.applyAsDouble(-log(x)) / x; // Effect the change of
                                                  // variable.
    }

    public Midexp(final T funcc, final double aa, final double bb) {
        super(funcc, aa, bb);
        super.a = 0.0;
        super.b = exp(-aa);
    }

}
