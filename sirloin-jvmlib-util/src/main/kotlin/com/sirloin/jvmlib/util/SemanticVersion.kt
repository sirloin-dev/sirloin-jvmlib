/*
 * sirloin-jvmlib
 * Distributed under MIT licence
 */
package com.sirloin.jvmlib.util

/**
 * A semantic version definition and implementation.
 *
 * [More documentation](https://semver.org/)
 *
 * @since 2022-05-20
 */
data class SemanticVersion(
    val major: Int,
    val minor: Int,
    val patch: Int,
    val preRelease: String = "",
    val build: String = ""
) : Comparable<SemanticVersion> {
    override fun compareTo(other: SemanticVersion): Int {
        return compareValuesBy(this, other, { it.major }, { it.minor }, { it.patch })
    }
}
