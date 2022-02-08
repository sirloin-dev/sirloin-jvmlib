/*
 * sirloin-jvmlib
 * Distributed under MIT licence
 */
package com.sirloin.jvmlib.util

/**
 * Creates an integer filled array from range notations. For example:
 *
 * ```
 * (5..10).toArray() = [5, 6, 7, 8, 9, 10]
 * ```
 *
 * @since 2022-02-14
 */
fun ClosedRange<Int>.toArray() = Array((endInclusive - start) + 1) { i -> start + i }
