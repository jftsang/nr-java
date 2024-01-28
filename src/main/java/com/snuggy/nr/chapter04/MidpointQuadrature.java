package com.snuggy.nr.chapter04;

import com.snuggy.nr.util.*;
import java.util.function.DoubleUnaryOperator;

public class MidpointQuadrature<T extends DoubleUnaryOperator> implements
    Quadrature {

    private int refinementLevel;

    // Routine implementing the extended midpoint rule.
    protected double a, b, s; // Limits of integration and current value of inte
    protected T funk; // gral.

    public MidpointQuadrature(final T funcc, final double aa, final double bb) {
        // The constructor takes as inputs func, the function or functor to
        // be integrated between limits a and b, also input.
        funk = (funcc);
        a = (aa);
        b = (bb);
        setRefinementLevel(0);
    }

    @Override
    public int getRefinementLevel() {
        return refinementLevel;
    }

    @Override
    public void setRefinementLevel(int n) {
        refinementLevel = n;
    }

    @Override
    public double next() throws NRException {
        // Returns the nth stage of refinement of the extended midpoint rule.
        // On the first call (n=1), the routine returns the crudest estimate of
        // R b
        // a f.x/dx. Subsequent calls set n=2,3,... and
        // improve the accuracy by adding .2=3/  3n-1 additional interior
        // points.
        int it, j;
        double x, tnm, sum, del, ddel;
        incrementRefinementLevel();
        if (getRefinementLevel() == 1) {
            return (s = (b - a) * func(0.5 * (a + b)));
        } else {
            for (it = 1, j = 1; j < getRefinementLevel() - 1; j++)
                it *= 3;
            tnm = it;
            del = (b - a) / (3.0 * tnm);
            ddel = del + del; // The added points alternate in spacing be
            x = a + 0.5 * del; // tween del and ddel.
            sum = 0.0;
            for (j = 0; j < it; j++) {
                sum += func(x);
                x += ddel;
                sum += func(x);
                x += del;
            }
            s = (s + (b - a) * sum / tnm) / 3.0; // The new sum is combined
                                                 // with the old inte
            return s; // gral to give a refined integral.
        }
    }

    public double func(final double x) throws NRException {
        return funk.applyAsDouble(x);
    } // Identity mapping.

}
