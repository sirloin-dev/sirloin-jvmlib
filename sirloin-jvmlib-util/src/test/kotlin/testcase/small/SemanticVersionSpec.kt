/*
 * com.sirloin.jvmlib
 * Sir.LOIN Intellectual property. All rights reserved.
 */
package testcase.small

import com.sirloin.jvmlib.util.SemanticVersion
import com.sirloin.jvmlib.util.SemanticVersionParser
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertAll

/**
 * Test rules are from:
 * [Backus–Naur Form Grammar for Valid SemVer Versions](https://semver.org/#backusnaur-form-grammar-for-valid-semver-versions)
 *
 * @since 2022-06-18
 */
class SemanticVersionSpec {
    @Test
    fun `Only version core is displayed if there are no metadata`() {
        // given:
        val (major, minor, patch) = Triple(1, 0, 0)
        val version = SemanticVersion(major, minor, patch).toString()

        // expect:
        version.isExpectedTo("$major.$minor.$patch")
    }

    @Test
    fun `Only version core and preRelease are displayed if there is no build`() {
        // given:
        val (major, minor, patch) = Triple(1, 0, 0)
        val preRelease = "alpha-8"
        val version = SemanticVersion(major, minor, patch, preRelease).toString()

        // expect:
        version.isExpectedTo("$major.$minor.$patch-$preRelease")
    }

    @Test
    fun `Only version core and build are displayed if there is no preRelease`() {
        // given:
        val (major, minor, patch) = Triple(1, 0, 0)
        val build = "09abcdef"
        val version = SemanticVersion(major, minor, patch, "", build).toString()

        // expect:
        version.isExpectedTo("$major.$minor.$patch+$build")
    }

    @Test
    fun `Version and metadata are displayed`() {
        // given:
        val (major, minor, patch) = Triple(1, 0, 0)
        val preRelease = "alpha-8"
        val build = "09abcdef"
        val version = SemanticVersion(major, minor, patch, preRelease, build).toString()

        // expect:
        version.isExpectedTo("$major.$minor.$patch-$preRelease+$build")
    }

    private fun String.isExpectedTo(expected: String) {
        assertAll(
            { assertThat(this, `is`(expected)) },
            { assertThat(SemanticVersionParser.isMatchesToSemanticVersion(this), `is`(true)) },
        )
    }
}
