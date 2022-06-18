/*
 * sirloin-jvmlib
 * Distributed under MIT licence
 */
package testcase.small

import com.sirloin.jvmlib.util.SemanticVersionParser
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers.nullValue
import org.hamcrest.core.Is.`is`
import org.junit.jupiter.api.RepeatedTest
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertAll
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import java.util.stream.Stream
import kotlin.random.Random

class SemanticVersionParserSpec {
    @Test
    fun `Parses to Semantic Version if given string conforms in Semantic Version Pattern`() {
        val (major, minor, patch) = Triple(
            Random.nextInt(0, Int.MAX_VALUE),
            Random.nextInt(0, Int.MAX_VALUE),
            Random.nextInt(0, Int.MAX_VALUE)
        )
        val preRelease = "abcde"
        val build = "abcde"
        val semanticVersion =
            SemanticVersionParser.toSemanticVersion("${major}.${minor}.${patch}-${preRelease}+${build}")!!

        assertAll(
            {
                assertThat(semanticVersion.major, `is`(major))
                assertThat(semanticVersion.minor, `is`(minor))
                assertThat(semanticVersion.patch, `is`(patch))
                assertThat(semanticVersion.preRelease, `is`(preRelease))
                assertThat(semanticVersion.build, `is`(build))
            }
        )
    }

    @ParameterizedTest
    @MethodSource("invalidSemanticVersion")
    fun `Parses to null if given string does not conforms in Semantic Version Pattern`(invalidSemanticVersion: String) {
        val semanticVersion = SemanticVersionParser.toSemanticVersion(invalidSemanticVersion)

        assertThat(semanticVersion, `is`(nullValue()))
    }

    @Test
    fun `Returns true if given string conforms in Semantic Version Pattern`() {
        val (major, minor, patch) = Triple(
            Random.nextInt(0, Int.MAX_VALUE),
            Random.nextInt(0, Int.MAX_VALUE),
            Random.nextInt(0, Int.MAX_VALUE)
        )
        val preRelease = "abcde"
        val build = "abcde"
        val result =
            SemanticVersionParser.isMatchesToSemanticVersion("${major}.${minor}.${patch}-${preRelease}+${build}")

        assertThat(result, `is`(true))
    }

    @ParameterizedTest
    @MethodSource("invalidSemanticVersion")
    fun `Parses to false if given string does not conforms in Semantic Version Pattern`(invalidSemanticVersion: String) {
        val isValidSemVer = SemanticVersionParser.isMatchesToSemanticVersion(invalidSemanticVersion)

        assertThat(isValidSemVer, `is`(false))
    }

    companion object {
        @JvmStatic
        fun invalidSemanticVersion(): Stream<Arguments> = Stream.of(
            Arguments.of("12.3"),
            Arguments.of("1.43"),
            Arguments.of("145"),
            Arguments.of("a.6.7"),
            Arguments.of("6.a.4"),
            Arguments.of("8.66.a"),
            Arguments.of("abcde"),
            Arguments.of("a.b.c"),
            Arguments.of("1.2.1.1-a"),
            Arguments.of("1.2.1-+")
        )
    }
}
