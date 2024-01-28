
package com.snuggy.nr.util;

@FunctionalInterface
public interface DoubleTernaryOperator {
    double applyAsDouble(final double x, final double y, final double z);
}
