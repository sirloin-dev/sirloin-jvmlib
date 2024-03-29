/*
 * sirloin-jvmlib
 * Distributed under MIT licence
 */
package testcase.small

import com.sirloin.jvmlib.util.SemanticVersionParser
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertAll
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import java.util.stream.Stream
import kotlin.random.Random

/**
 * @since 2022-05-23
 */
internal class SemanticVersionParserSpec {
    @Test
    fun `Parses to Semantic Version if given string conforms in Semantic Version Pattern`() {
        // given:
        val (major, minor, patch) = Triple(
            Random.nextInt(0, Int.MAX_VALUE),
            Random.nextInt(0, Int.MAX_VALUE),
            Random.nextInt(0, Int.MAX_VALUE)
        )
        val preRelease = "abcde"
        val build = "abcde"

        // when:
        val semanticVersion =
            SemanticVersionParser.toSemanticVersion("${major}.${minor}.${patch}-${preRelease}+${build}")!!

        // expect:
        assertAll(
            { semanticVersion.major shouldBe major },
            { semanticVersion.minor shouldBe minor },
            { semanticVersion.patch shouldBe patch },
            { semanticVersion.preRelease shouldBe preRelease },
            { semanticVersion.build shouldBe build }
        )
    }

    @ParameterizedTest
    @MethodSource("invalidSemanticVersion")
    fun `Parses to null if given string does not conforms in Semantic Version Pattern`(invalidSemanticVersion: String) {
        // given:
        val semanticVersion = SemanticVersionParser.toSemanticVersion(invalidSemanticVersion)

        // expect:
        semanticVersion shouldBe null
    }

    @Test
    fun `Returns true if given string conforms in Semantic Version Pattern`() {
        // given:
        val (major, minor, patch) = Triple(
            Random.nextInt(0, Int.MAX_VALUE),
            Random.nextInt(0, Int.MAX_VALUE),
            Random.nextInt(0, Int.MAX_VALUE)
        )
        val preRelease = "abcde"
        val build = "abcde"

        // when:
        val result =
            SemanticVersionParser.isMatchesToSemanticVersion("${major}.${minor}.${patch}-${preRelease}+${build}")

        // expect:
        result shouldBe true
    }

    @ParameterizedTest
    @MethodSource("invalidSemanticVersion")
    fun `Parses to false if given string does not conforms in Semantic Version Pattern`(invalidSemanticVersion: String) {
        // given:
        val isValidSemVer = SemanticVersionParser.isMatchesToSemanticVersion(invalidSemanticVersion)

        // expect:
        isValidSemVer shouldBe false
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
