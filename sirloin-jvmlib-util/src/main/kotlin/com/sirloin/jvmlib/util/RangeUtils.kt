/*
 * sirloin-jvmlib
 * Distributed under MIT licence
 */
package com.sirloin.jvmlib.util

import java.util.ArrayList

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

fun <T> Int.filledBy(filler: (Int) -> T): List<T> {
    if (this < 1) {
        throw IllegalArgumentException("Size < 1 - Size must be larger than 0")
    }

    return ArrayList<T>(this).apply {
        for (i in 0..this.size) {
            add(filler(i))
        }
    }
}
