
package com.snuggy.nr.chapter05;

import static com.snuggy.nr.chapter04.Static.*;

import com.snuggy.nr.chapter06.*;
import com.snuggy.nr.util.*;
import java.util.function.DoubleUnaryOperator;

public class LevinExample {

    /**
     * Integrand for (5.3.22).
     */
    static class AFunc implements DoubleUnaryOperator {
        public double applyAsDouble(final double x) {
            if (x == 0.0)
                return 0.0;
            else {
                Bessel bess = new Bessel();
                try {
                    return x * bess.jnu(0.0, x) / (1.0 + x * x);
                } catch (NRException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

    public static void main(String[] args) throws NRException {
        // This sample program shows how to use the Levin u transformation to
        // evaluate an oscillatory integral, equation (5.3.22).
        final double PI = 3.141592653589793;
        int nterm = 12;
        double beta = 1.0, a = 0.0, b = 0.0, sum = 0.0;
        Levin series = new Levin(100, 0.0);
        for (int n = 0; n <= nterm; n++) {
            b += PI;
            double s = qromb(new AFunc(), a, b, 1.e-8);
            a = b;
            sum += s;
            double omega = (beta + n) * s; // Use u transformation.
            double ans = series.next(sum, omega, beta);
            System.out.println("N = " + n + ", Sum = " + sum + ", Levin Sum = " + ans);
        }
    }
}
