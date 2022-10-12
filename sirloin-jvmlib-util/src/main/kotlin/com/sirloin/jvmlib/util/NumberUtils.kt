/*
 * sirloin-jvmlib
 * Distributed under MIT licence
 */
package com.sirloin.jvmlib.util

import java.math.BigDecimal

/**
 * Determines that given BigDecimal has only trailing zeros - means that it is an integer.
 */
fun BigDecimal.isIntegerValue() = signum() == 0 || scale() <= 0 || stripTrailingZeros().scale() <= 0
