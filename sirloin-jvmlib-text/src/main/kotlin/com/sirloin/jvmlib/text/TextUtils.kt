/*
 * sirloin-jvmlib
 * Distributed under MIT licence
 */
package com.sirloin.jvmlib.text

import kotlin.random.Random

private const val ALPHANUMERIC_LETTERS = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz"

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

fun randomAlphanumeric(min: Int = 0, max: Int = 32): String {
    if (min < 0 || max < 0) {
        throw IllegalArgumentException("max: '$max', min: '$min'. Both must be larger than 0.")
    }

    if (max < min) {
        throw IllegalArgumentException("max: '$max' < min : '$min'")
    }

    val length = (min..max).random()

    return if (length == 0) {
        ""
    } else {
        (1..length)
            .map { Random.nextInt(0, ALPHANUMERIC_LETTERS.length).let { ALPHANUMERIC_LETTERS[it] } }
            .joinToString("")
    }
}
