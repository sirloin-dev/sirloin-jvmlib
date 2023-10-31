/*
 * sirloin-jvmlib
 * Distributed under MIT licence
 */
package testcase.small

import com.sirloin.jvmlib.text.randomAlphanumeric
import com.sirloin.jvmlib.text.toCharArray
import io.kotest.matchers.ints.shouldBeInRange
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import java.util.stream.Stream

/**
 * @since 2022-04-14
 */
internal class TextUtilsTest {
    @MethodSource("toCharArrayArgs")
    @ParameterizedTest(name = "CharSequence.toCharArray returns CharArray that contains same content")
    fun `CharSequence#toCharArray returns CharArray that contains same content`(
        input: CharSequence,
        expected: CharArray
    ) {
        // given:
        val actual = input.toCharArray()

        // expect:
        actual shouldBe expected
    }

    @DisplayName("randomAlphaNumeric should:")
    @Nested
    inner class RandomAlphaNumeric {
        @Test
        fun `raises exception if min is less than 0`() {
            assertThrows<IllegalArgumentException> { randomAlphanumeric(min = -1) }
        }

        @Test
        fun `raises exception if max is less than 0`() {
            assertThrows<IllegalArgumentException> { randomAlphanumeric(min = 0, max = -1) }
        }

        @Test
        fun `raises exception if max is less than min`() {
            assertThrows<IllegalArgumentException> { randomAlphanumeric(min = 1, max = 0) }
        }

        @Test
        fun `returns empty string if min is 0`() {
            // when:
            val result = randomAlphanumeric(0, 0)

            // expect:
            result shouldBe ""
        }

        @Test
        fun `returns filled String if sufficient range is given`() {
            // given:
            val min = 0
            val max = 32

            // when:
            val chars = randomAlphanumeric(min, max)

            // expect:
            chars.length shouldBeInRange min..max
        }
    }

    companion object {
        @JvmStatic
        fun toCharArrayArgs(): Stream<Arguments> = Stream.of(
            Arguments.of("", charArrayOf()),
            Arguments.of("Sample text", charArrayOf('S', 'a', 'm', 'p', 'l', 'e', ' ', 't', 'e', 'x', 't')),
            Arguments.of("\u1100\u1161", charArrayOf('\u1100', '\u1161')),
            Arguments.of("中国", charArrayOf('\u4E2D', '\u56FD')),
            Arguments.of("💚", charArrayOf('\uD83D', '\uDC9A'))
        )
    }
}
