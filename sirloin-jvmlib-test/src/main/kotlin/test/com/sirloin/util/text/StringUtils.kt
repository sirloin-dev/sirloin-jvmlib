/*
 * sirloin-jvmlib
 * Distributed under MIT licence
 */
package test.com.sirloin.util.text

import java.util.*
import kotlin.math.abs
import kotlin.random.Random

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

/**
 * Gets the next random decimal String literal from the random number generator in the specified range -
 * [from] (inclusive) and [until] (exclusive) bounds.
 *
 * @throws IllegalArgumentException if [from] is greater than or equal to [until].
 */
fun randomNumeral(
    from: Long = 0,
    until: Long = 9,
    digits: Int = 0
): String {
    val realDigits = until.toString().length
    val padStart = digits >= realDigits

    return Random.nextLong(from, until).let {
        val numerals = it.toString()

        return@let if (padStart) {
            if (it < 0) {
                "-${abs(it).toString().padStart(digits, '0')}"
            } else {
                numerals.padStart(digits, '0')
            }
        } else {
            numerals
        }
    }
}
