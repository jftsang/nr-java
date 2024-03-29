
package com.snuggy.nr.chapter06;

import static com.snuggy.nr.util.Static.*;
import static java.lang.Math.*;

import com.snuggy.nr.util.*;

public class Erf {

    // Object for error function and related functions.
    static final int ncof = 28;
    private static final double[] erf_cof = { -1.3026537197817094, 6.4196979235649026e-1, 1.9476473204185836e-2,
            -9.561514786808631e-3, -9.46595344482036e-4, 3.66839497852761e-4, 4.2523324806907e-5, -2.0278578112534e-5,
            -1.624290004647e-6, 1.303655835580e-6, 1.5626441722e-8, -8.5238095915e-8, 6.529054439e-9, 5.059343495e-9,
            -9.91364156e-10, -2.27365122e-10, 9.6467911e-11, 2.394038e-12, -6.886027e-12, 8.94487e-13, 3.13092e-13,
            -1.12708e-13, 3.81e-16, 7.106e-15, -1.523e-15, -9.4e-17, 1.21e-16, -2.8e-17 };


    // static final double[] cof = doub_arr(28]; // Initialization at end of
    // struct.

    public double erf(final double x) throws NRException {
        // Return erf.x/ for any x.
        if (x >= 0.)
            return 1.0 - erfccheb(x);
        else
            return erfccheb(-x) - 1.0;
    }

    public double erfc(final double x) throws NRException {
        // Return erfc.x/ for any x.
        if (x >= 0.)
            return erfccheb(x);
        else
            return 2.0 - erfccheb(-x);
    }

    public double erfccheb(final double z) throws NRException {
        // Evaluate equation (6.2.16) using stored Chebyshev coefficients. User
        // should not call directly.
        int j;
        double t, ty, tmp, d = 0., dd = 0.;
        if (z < 0.)
            throw new NRException("erfccheb requires nonnegative argument");
        t = 2. / (2. + z);
        ty = 4. * t - 2.;
        for (j = ncof - 1; j > 0; j--) {
            tmp = d;
            d = ty * d - dd + erf_cof[j];
            dd = tmp;
        }
        return t * exp(-z * z + 0.5 * (erf_cof[0] + ty * d) - dd);
    }

    public double inverfc(final double p) throws NRException {
        // Inverse of complementary error function. Returns x such that
        // erfc.x/ D p for argument p between 0 and 2.
        double x, err, t, pp;
        if (p >= 2.0)
            return -100.; // Return arbitrary large pos or neg value.
        if (p <= 0.0)
            return 100.;
        pp = (p < 1.0) ? p : 2. - p;
        t = sqrt(-2. * log(pp / 2.)); // Initial guess:
        x = -0.70711 * ((2.30753 + t * 0.27061) / (1. + t * (0.99229 + t * 0.04481)) - t);
        for (int j = 0; j < 2; j++) {
            err = erfc(x) - pp;
            x += err / (1.12837916709551257 * exp(-SQR(x)) - x * err); // Halley.
        }
        return (p < 1.0 ? x : -x);
    }

    public double inverf(final double p) throws NRException {
        return inverfc(1. - p);
    }

    // Inverse of the error function. Returns x such that erf.x/ D p for
    // argument p between 1 and 1.

}
