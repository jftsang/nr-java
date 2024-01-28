
package com.snuggy.nr.chapter04;

import static com.snuggy.nr.chapter04.Static.*;

import com.snuggy.nr.util.*;
import java.util.function.DoubleBinaryOperator;
import java.util.function.DoubleUnaryOperator;

public class NRf2 implements DoubleUnaryOperator {
	private NRf3 f3;
	private DoubleBinaryOperator z1;
	private DoubleBinaryOperator z2;
	
	public NRf2(final DoubleBinaryOperator zz1, final DoubleBinaryOperator zz2) {
	    f3 = new NRf3();
	    z1 = (zz1);
	    z2 = (zz2);
    }
	
	public NRf3 f3() {
	    return f3;
	}
	
	public double applyAsDouble(final double y)  // This is G of eq. (4.8.4).
	{
		f3.set_ysav(y);
		try {
			return qgaus(f3,z1.applyAsDouble(f3.xsav(),y),z2.applyAsDouble(f3.xsav(),y));
		} catch (NRException e) {
			throw new RuntimeException(e);
		}
	}
}
