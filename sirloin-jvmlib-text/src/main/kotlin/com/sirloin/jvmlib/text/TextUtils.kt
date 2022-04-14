/*
 * sirloin-jvmlib
 * Distributed under MIT licence
 */
package com.sirloin.jvmlib.text

/**
 * Simplified version of:
 *
 * ```
 * input.run { CharSequence(length) { get(it) } }
 * ```
 *
 * where `input` is a type of [CharSequence].
 *
 * @since 2022-04-14
 */
fun CharSequence.toCharArray(): CharArray {
    return if (isEmpty()) {
        charArrayOf()
    } else {
        CharArray(length) { get(it) }
    }
}
