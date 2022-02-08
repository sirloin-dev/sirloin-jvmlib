/*
 * sirloin-jvmlib
 * Distributed under MIT licence
 */
package com.sirloin.jvmlib.time

import java.time.Instant
import java.time.temporal.ChronoUnit

/**
 * Syntactic sugar for removing milliseconds and nanoseconds part of given Instant.
 *
 * @since 2022-02-14
 */
fun Instant?.truncateToSeconds(): Instant? =
    this?.truncatedTo(ChronoUnit.SECONDS)
