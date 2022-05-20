package com.sirloin.jvmlib.util

object SemanticVersionParser {
    private const val PATTERN_SEMVER =
        "(0|[1-9]\\d*)\\.(0|[1-9]\\d*)\\.(0|[1-9]\\d*)" +
                "(?:-((?:0|[1-9]\\d*|\\d*[a-zA-Z-][0-9a-zA-Z-]*)(?:\\.(?:0|[1-9]\\d*|\\d*[a-zA-Z-][0-9a-zA-Z-]*))*))?" +
                "(?:\\+([0-9a-zA-Z-_]+(?:\\.[0-9a-zA-Z-]+)*))?"

    fun toSemanticVersion(clientSemVer: String): SemanticVersion? =
        Regex(PATTERN_SEMVER)
            .also { if (!it.matches(clientSemVer)) return null }
            .find(clientSemVer)!!
            .destructured.let { (major, minor, patch, preRelease, build) ->
                return SemanticVersion(
                    major = major.toInt(),
                    minor = minor.toInt(),
                    patch = patch.toInt(),
                    preRelease = preRelease,
                    build = build
                )
            }

    fun isMatchesToSemanticVersion(clientSemVer: String): Boolean =
        Regex(PATTERN_SEMVER)
            .matches(clientSemVer)
}

