package com.sirloin.jvmlib.util

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
