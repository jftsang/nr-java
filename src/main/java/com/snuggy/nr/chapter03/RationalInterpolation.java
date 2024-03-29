
package com.snuggy.nr.chapter03;

import static com.snuggy.nr.refs.Refs.*;
import static com.snuggy.nr.util.Static.*;
import static java.lang.Math.*;

import com.snuggy.nr.refs.*;
import com.snuggy.nr.util.*;

public class RationalInterpolation extends BaseInterpolation {

    // Diagonal rational function interpolation object. Construct with x and
    // y vectors, and the number m of points to be used locally, then call
    // interp for interpolated values.

    protected double dy;

    public RationalInterpolation(final double[] xv, final double[] yv, final int m) {
        super(xv, $_(yv, 0), m);
        dy = (0.0);
    }

    // Doub rawinterp(Int jl, Doub x);

    public double rawinterp(final int jl, final double x) throws NRException {
        // Given a value x, and using pointers to data xx and yy, this
        // routine returns an interpolated value y, and stores an error
        // estimate dy. The returned value is obtained by mm-point diagonal
        // rational function interpolation on the subrange xx[jl..jl+mm-1].

        final double TINY = 1.0e-99; // A small number.
        int m, i, ns = 0;
        double y, w, t, hh, h, dd;
        final $double xa = $(xx, jl); 
        final $double ya = $(yy, jl); 
        final double[] c = doub_vec(mm), d = doub_vec(mm);
        hh = abs(x - xa.$_(0));
        for (i = 0; i < mm; i++) {
            h = abs(x - xa.$_(i));
            if (h == 0.0) {
                dy = 0.0;
                return ya.$_(i);
            } else if (h < hh) {
                ns = i;
                hh = h;
            }
            c[i] = ya.$_(i);
            d[i] = ya.$_(i) + TINY; // The TINY part is needed to
                                              // prevent a rare
            // zero-over-zero
        } // condition.
        y = ya.$_((ns--));
        for (m = 1; m < mm; m++) {
            for (i = 0; i < mm - m; i++) {
                w = c[i + 1] - d[i];
                h = xa.$_(i + m) - x; // h will never be zero, since
                                                // this was tested in the
                                                // initial
                t = (xa.$_(i) - x) * d[i] / h; // izing loop.
                dd = t - c[i + 1];
                if (dd == 0.0)
                    throw new NRException("Error in routine ratint");
                // This error condition indicates that the interpolating
                // function has
                // a pole at the requested value of x.
                dd = w / dd;
                d[i] = c[i + 1] * dd;
                c[i] = t * dd;
            }
            y += (dy = (2 * (ns + 1) < (mm - m) ? c[ns + 1] : d[ns--]));
        }
        return y;
    }

}
