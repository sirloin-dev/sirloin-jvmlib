package testcase.small

import com.sirloin.jvmlib.util.SemanticVersionParser
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.core.Is.`is`
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.RepeatedTest
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertAll
import kotlin.random.Random

class SemanticVersionParserSpec {
    @DisplayName("정규식과 일치하는 Semantic Version은 parse에 성공한다. ")
    @RepeatedTest(100)
    fun parseSemanticVersion() {
        val major = Random.nextInt(0, Int.MAX_VALUE)
        val minor = Random.nextInt(0, Int.MAX_VALUE)
        val patch = Random.nextInt(0, Int.MAX_VALUE)
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

    @DisplayName("정규식과 일치하지 않는 Semantic Version은 null을 반환한다. ")
    @Test
    fun parseSemanticVersionByStrangeSemVer() {
        val allFalseResult = listOf(
            "12.3",
            "1.43",
            "145",
            "a.6.7",
            "6.a.4",
            "8.66.a",
            "abcde",
            "a.b.c",
            "1.1.1.1"
        ).all {
            !SemanticVersionParser.isMatchesToSemanticVersion(it)
        }

        assertThat(allFalseResult, `is`(true))
    }

    @DisplayName("정규식과 Semantic Version 규격이 정확히 일치한다.")
    @RepeatedTest(100)
    fun validateSemVerSpec() {
        val major = Random.nextInt(0, Int.MAX_VALUE)
        val minor = Random.nextInt(0, Int.MAX_VALUE)
        val patch = Random.nextInt(0, Int.MAX_VALUE)
        val preRelease = "abcde"
        val build = "abcde"
        val result =
            SemanticVersionParser.isMatchesToSemanticVersion("${major}.${minor}.${patch}-${preRelease}+${build}")

        assertThat(result, `is`(true))
    }

}