/*
 * sirloin-jvmlib
 * Distributed under MIT licence
 */
package test.com.sirloin.util.text

import java.util.*

/**
 * Constructs a random length of String snippet filled with [fillChar]. Length of created String is
 * between [min] and [max] if both are positive numbers.
 *
 * Default [min] is 0, [max] is 32.
 *
 * @throws IllegalArgumentException if [min] and/or [max] is negative number.
 * @throws IllegalArgumentException if [max] < [min].
 * @since 2022-02-14
 */
fun randomFillChars(fillChar: Char, min: Int = 0, max: Int = 32): String {
    if (min < 0 || max < 0) {
        throw IllegalArgumentException("max: '$max', min: '$min'. Both must be larger than 0.")
    }

    if (max < min) {
        throw IllegalArgumentException("max: '$max' < min : '$min'")
    }

    val length = (min..max).random()

    if (length == 0) {
        return ""
    }

    return String(CharArray(length).apply {
        Arrays.fill(this, fillChar)
    })
}
