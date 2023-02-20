/*
 * com.sirloin.jvmlib
 * Sir.LOIN Intellectual property. All rights reserved.
 */
package test.com.sirloin.util.number

/**
 * Repeats given [expr] for given N times.
 *
 * This function is maybe useful for such testing patterns as follows:
 *
 * ```
 * 5 times { sut.perform() }
 * ```
 */
infix fun Int.times(expr: (Int) -> Unit) {
    timesOf(expr)
}

/**
 * Repeats given [expr] for given N times and stores its result into an N sized list.
 *
 * This function is maybe useful for such testing patterns as follows:
 *
 * ```
 * val fiveRandomNumbers = 5 timesOf { _ -> Random.nextInt() }
 * ```
 *
 * @param expr Your logic that converts numbers between [0..Int - 1) to given type T.
 */
infix fun <T> Int.timesOf(expr: (Int) -> T): List<T> {
    val results = ArrayList<T>(this)

    (0 until this).forEach {
        results.add(expr(it))
    }

    return results
}
