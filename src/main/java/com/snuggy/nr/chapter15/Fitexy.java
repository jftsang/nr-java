
package com.snuggy.nr.chapter15;

import static com.snuggy.nr.chapter09.Static.*;
import static com.snuggy.nr.chapter14.Static.*;
import static com.snuggy.nr.refs.Refs.*;
import static com.snuggy.nr.util.Static.*;
import static java.lang.Math.*;

import com.snuggy.nr.chapter06.*;
import com.snuggy.nr.chapter10.*;
import com.snuggy.nr.refs.*;
import com.snuggy.nr.util.*;
import java.util.function.DoubleUnaryOperator;

public class Fitexy {

    // Object for tting a straight line a C bx to a set of points .xi ; yi
    // / with errors in both xi and yi , respectively sigx and sigy. Call the
    // constructor to calculate the t. The answers are then available as the
    // variables a, b, siga, sigb, chi2, and q. Output quantities a and b
    // make y D aCbx minimize 2, whose value is returned as chi2. The 2
    // probability is returned as q, a small value indicating a poor t
    // (sometimes indicating underestimated errors). The standard errors on
    // a and b, siga and sigb, are not meaningful if either (i) the t is poor,
    // or (ii) b is so large that the data are consistent with a vertical
    // (in nite b) line. If siga and sigb are returned as BIG, then the data
    // are consistent with all values of b.

    private double a, b, siga, sigb, chi2, q; // Answers.
    private int ndat;
    private final double[] xx, yy, sx, sy, ww; // Variables that communicate
                                               // with
    // Chixy.
    private $double aa = $(0.0);
    private $double offs = $(0.0);

    public double a() {
        return a;
    }

    public double b() {
        return b;
    }

    public double chi2() {
        return chi2;
    }

    public double q() {
        return q;
    }

    public double siga() {
        return siga;
    }

    public double sigb() {
        return sigb;
    }

    public Fitexy(final double[] x, final double[] y, final double[] sigx, final double[] sigy) throws NRException {
        // Constructor. Call with the input data x[0..ndat-1], y[0..ndat-1],
        // sigx[0..ndat-1], and sigy[0..ndat-1].
        ndat = (x.length);
        xx = doub_vec(ndat);
        yy = doub_vec(ndat);
        sx = doub_vec(ndat);
        sy = doub_vec(ndat);
        ww = doub_vec(ndat);
        final double POTN = 1.571000, BIG = 1.0e30, ACC = 1.0e-6;
        final double PI = 3.141592653589793238;
        Gamma gam = new Gamma();
        Brent brent = new Brent(ACC);
        Chixy chixy = new Chixy(xx, yy, sx, sy, ww, aa, offs); // Instantiate
                                                               // a
                                                               // Chixy
                                                               // and
                                                               // bind
                                                               // it to
        int j; // our variables.
        $double varx = $(0.0), vary = $(0.0), d1 = $(0.0), d2 = $(0.0), dum1 = $(0.0);
        double amx;
        double amn;
        double[] ang = new double[7];
        double[] ch = new double[7];
        double scale;
        double bmn;
        double bmx;
        double r2;
        avevar(x, dum1, varx); // Find the x and y variances, and scale
        // the data into the global variables for communication with the
        // function chixy.
        avevar(y, dum1, vary);
        scale = sqrt(varx.$() / vary.$());
        for (j = 0; j < ndat; j++) {
            xx[j] = x[j];
            yy[j] = y[j] * scale;
            sx[j] = sigx[j];
            sy[j] = sigy[j] * scale;
            ww[j] = sqrt(SQR(sx[j]) + SQR(sy[j])); // Use both x and y weights
                                                   // in rst
        } // trial t.
        Fitab fit = new Fitab(xx, yy, ww);
        b = fit.b(); // Trial t for b.
        offs.$(ang[0] = 0.0); // Construct several angles for reference
        // points, and make b an angle.
        ang[1] = atan(b);
        ang[3] = 0.0;
        ang[4] = ang[1];
        ang[5] = POTN;
        for (j = 3; j < 6; j++)
            ch[j] = chixy.applyAsDouble(ang[j]);
        // Bracket the 2 minimum and then locate it with brent.
        brent.bracket(ang[0], ang[1], chixy);
        ang[0] = brent.ax();
        ang[1] = brent.bx();
        ang[2] = brent.cx();
        ch[0] = brent.fa();
        ch[1] = brent.fb();
        ch[2] = brent.fc();
        b = brent.minimize(chixy);
        chi2 = chixy.applyAsDouble(b);
        a = aa.$();
        q = gam.gammq(0.5 * (ndat - 2), chi2 * 0.5); // Compute 2 probability.
        r2 = 0.0;
        for (j = 0; j < ndat; j++)
            r2 += ww[j]; // Save the inverse sum of weights at
        r2 = 1.0 / r2; // the minimum.
        bmx = bmn = BIG; // Now, nd standard errors for b as
        // points where 2
        offs.$(chi2 + 1.0); // D 1.
        for (j = 0; j < 6; j++) { // Go through saved values to bracket
            // the desired roots. Note periodicity in slope angles.
            if (ch[j] > offs.$()) {
                d1.$(abs(ang[j] - b));
                while (d1.$() >= PI)
                    d1.$(d1.$() - PI);
                d2.$(PI - d1.$());
                if (ang[j] < b)
                    SWAP(d1, d2);
                if (d1.$() < bmx)
                    bmx = d1.$();
                if (d2.$() < bmn)
                    bmn = d2.$();
            }
        }
        if (bmx < BIG) { // Call zbrent to nd the roots.
            bmx = zbrent(chixy, b, b + bmx, ACC) - b;
            amx = aa.$() - a;
            bmn = zbrent(chixy, b, b - bmn, ACC) - b;
            amn = aa.$() - a;
            sigb = sqrt(0.5 * (bmx * bmx + bmn * bmn)) / (scale * SQR(cos(b)));
            siga = sqrt(0.5 * (amx * amx + amn * amn) + r2) / scale; // Error in
                                                                     // a has
                                                                     // additional
                                                                     // piece
        } else
            sigb = siga = BIG; // r2.
        a /= scale; // Unscale the answers.
        b = tan(b) / scale;
    }

    public void SWAP(double[] x, double[] y) {
        double t = x[0];
        x[0] = y[0];
        y[0] = t;
    }

    public void SWAP($double x, $double y) {
        double t = x.$();
        x.$(y.$());
        y.$(t);
    }

    class Chixy implements DoubleUnaryOperator {
        // Captive functor of Fitexy, returns the value of .2 offs/ for the
        // slope b=tan(bang). Scaled data and offs are communicated via bound
        // references.
        private final double[] xx, yy, sx, sy, ww;
        private $double aa, offs;

        public Chixy(final double[] xxx, final double[] yyy, final double[] ssx, final double[] ssy,
                final double[] www, final $double aaa, final $double ooffs) {
            xx = (xxx);
            yy = (yyy);
            sx = (ssx);
            sy = (ssy);
            ww = (www);
            aa = aaa;
            offs = (ooffs);
        }

        // Constructor. Bind references back to Fitexy.

        public double applyAsDouble(final double bang) {
            // The function as seen by Brent and zbrent.
            final double BIG = 1.0e30;
            int j, nn = xx.length;
            double ans, avex = 0.0, avey = 0.0, sumw = 0.0, b;
            b = tan(bang);
            for (j = 0; j < nn; j++) {
                ww[j] = SQR(b * sx[j]) + SQR(sy[j]);
                sumw += (ww[j] = (ww[j] < 1.0 / BIG ? BIG : 1.0 / ww[j]));
                avex += ww[j] * xx[j];
                avey += ww[j] * yy[j];
            }
            avex /= sumw;
            avey /= sumw;
            aa.$(avey - b * avex);
            for (ans = -offs.$(), j = 0; j < nn; j++)
                ans += ww[j] * SQR(yy[j] - aa.$() - b * xx[j]);
            return ans;
        }
    }

}
