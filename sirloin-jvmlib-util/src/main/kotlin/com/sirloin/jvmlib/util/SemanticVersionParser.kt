package com.sirloin.jvmlib.util

/*
 * sirloin-jvmlib
 * Distributed under MIT licence
 */
object SemanticVersionParser {
    /**
     * Parses given string to Semantic version.
     *
     * @since 2022-05-20
     */
    // https://semver.org/#is-there-a-suggested-regular-expression-regex-to-check-a-semver-string
    private const val PATTERN_SEMVER =
        "^(0|[1-9]\\d*)\\.(0|[1-9]\\d*)\\.(0|[1-9]\\d*)" +
                "(?:-((?:0|[1-9]\\d*|\\d*[a-zA-Z-][0-9a-zA-Z-]*)(?:\\.(?:0|[1-9]\\d*|\\d*[a-zA-Z-][0-9a-zA-Z-]*))*))?" +
                "(?:\\+([0-9a-zA-Z-_]+(?:\\.[0-9a-zA-Z-]+)*))?$"

    fun toSemanticVersion(semVer: String): SemanticVersion? =
        Regex(PATTERN_SEMVER)
            .matchEntire(semVer)?.destructured?.let { (major, minor, patch, preRelease, build) ->
                    SemanticVersion(
                        major = major.toInt(),
                        minor = minor.toInt(),
                        patch = patch.toInt(),
                        preRelease = preRelease,
                        build = build
                    )
            }

    fun isMatchesToSemanticVersion(semVer: String): Boolean =
        Regex(PATTERN_SEMVER)
            .matches(semVer)
}

