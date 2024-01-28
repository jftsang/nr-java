
package com.snuggy.nr.chapter04;

import com.snuggy.nr.util.*;
import java.util.function.DoubleUnaryOperator;

public class NRf3 implements DoubleUnaryOperator {
    
	private double xsav,ysav;
	private DoubleTernaryOperator func3d;
	
	public void set_func3d(final DoubleTernaryOperator func3d) {
	    this.func3d = func3d;
	}
	
	public void set_ysav(final double ysav) {
	    this.ysav = ysav;
	}
	
	public double xsav() {
	    return xsav;
	}
	
	public void set_xsav(final double xsav) {
	    this.xsav = xsav;
	}
	
	public double applyAsDouble(final double z)  {
		// The integrand f.x;y;z/ applyAsDoubleuated at fixed x and y.
		return func3d.applyAsDouble(xsav,ysav,z);
	}

}
