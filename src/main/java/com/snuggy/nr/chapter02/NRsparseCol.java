
package com.snuggy.nr.chapter02;

import static com.snuggy.nr.util.Static.*;

public class NRsparseCol {
    // parse vector data structure.
    protected int nrows; // Number of rows.
    protected int nvals; // Maximum number of nonzeros.
    protected int[] row_ind; // Row indices of nonzeros.
    protected double[] val; // Array of nonzero values.
    
    public final double[] val() {
        return val;
    }
    
    public int nvals() {
        return nvals;
    }
    
    public final int[] row_ind() {
        return row_ind;
    }
    
    public NRsparseCol(final int m, final int nnvals) {
        nrows = (m);
        nvals = (nnvals);
        row_ind = int_vec(nnvals);
        val = doub_vec(nnvals);
    } // Constructor. Initializes vector to zero.

    public void resize(final int m, final int nnvals) {
        nrows = m;
        nvals = nnvals;
        row_ind = int_vec(nnvals);
        val = doub_vec(nnvals);
    }
}
