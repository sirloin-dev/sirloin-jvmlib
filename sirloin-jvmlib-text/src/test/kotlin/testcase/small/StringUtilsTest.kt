/*
 * sirloin-jvmlib
 * Distributed under MIT licence
 */
package testcase.small

import com.sirloin.jvmlib.text.isNullOrUnicodeBlank
import com.sirloin.jvmlib.text.matchesIn
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertAll
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import java.util.regex.Pattern
import java.util.stream.Stream

/**
 * @since 2022-02-08
 */
internal class StringUtilsTest {
    @Test
    fun `isUnicodeBlank yields expected result upon given input`() {
        val withInput: (String) -> Boolean = { it.isNullOrUnicodeBlank() }

        assertAll(
            { withInput("") shouldBe true },
            { withInput("   ") shouldBe true },
            { withInput(" a ") shouldBe false },
            { withInput("\u3000 a \u3000") shouldBe false },
            { withInput("\u3000\u3000") shouldBe true }
        )
    }

    @ParameterizedTest(name = "\"{0}\".matchesIn(\"{1}\") ==> ''{2}''")
    @MethodSource("matchesInTestArgsProvider")
    fun `matchesIn test for various inputs`(str: String, pattern: Pattern, expected: Boolean) {
        str.matchesIn(pattern) shouldBe expected
    }

    companion object {
        @Suppress("unused")
        @JvmStatic
        private fun matchesInTestArgsProvider(): Stream<Arguments> {
            return Stream.of(
                Arguments.of("1234567890", Pattern.compile("""\d+"""), true),
                Arguments.of("AMZamz", Pattern.compile("""[A-Z]{3}[a-z]{3}"""), true),
                Arguments.of("1992Apd", Pattern.compile("""\d+$"""), false)
            )
        }
    }
}
