
package com.snuggy.nr.chapter17;

import static com.snuggy.nr.util.Static.*;

import com.snuggy.nr.refs.*;
import com.snuggy.nr.util.*;

public abstract class StepperBase implements IStepperBase {
    
    // The Odeint object doesn't know in advance which specific stepper object
    // it will be instantiated with. It does, however, rely on the fact that
    // the stepper object will be derived from, and thus have the methods in,
    // this StepperBase object, which serves as the base class for all
    // subsequent ODE algorithms in this chapter:

    // Base class for all ODE algorithms.
    protected $double x;
    protected double xold; // Used for dense output.
    protected final double[] y, dydx;
    protected double atol, rtol;
    protected boolean dense;
    protected double hdid; // Actual stepsize accomplished by the step routine.
    protected double hnext; // Stepsize predicted by the controller for the next
                            // step.
    protected double EPS;
    protected int n, neqn; // neqn D n except for StepperStoerm.
    protected final double[] yout, yerr; // New value of y and error estimate.

    public StepperBase(final double[] yy, final double[] dydxx, 
                        final $double xx, final double atoll,
                        final double rtoll, boolean dens) throws NRException {
        x = xx;
        y = yy;
        dydx = dydxx;
        atol = (atoll);
        rtol = (rtoll);
        dense = (dens);
        n = (y.length);
        neqn = (n);
        yout = doub_vec(n);
        yerr = doub_vec(n);
    }
    // Input to the constructor are the dependent variable vector y[0..n-1]
    // and its derivative dydx[0..n-1] at the starting value of the independent
    // variable x. Also input are the absolute and relative tolerances, atol
    // and rtol, and the boolean dense, which is true if dense output is
    // required.

}
