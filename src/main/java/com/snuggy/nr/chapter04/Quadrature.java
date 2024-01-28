package com.snuggy.nr.chapter04;

import com.snuggy.nr.util.*;

/**
 * Abstract base class for elementary quadrature algorithms.
 */
public interface Quadrature {

    int getRefinementLevel();

    void setRefinementLevel(int n);

    default void incrementRefinementLevel() {
        setRefinementLevel(getRefinementLevel() + 1);
    }

    /**
     * Returns the value of the integral at the nth stage of refinement. The function next() must be
     * defined in the derived class.
     */
    double next() throws NRException;

}
