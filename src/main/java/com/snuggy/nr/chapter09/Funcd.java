
package com.snuggy.nr.chapter09;

import com.snuggy.nr.util.*;
import java.util.function.DoubleUnaryOperator;

public interface Funcd extends DoubleUnaryOperator {
    double df(final double x);
}
