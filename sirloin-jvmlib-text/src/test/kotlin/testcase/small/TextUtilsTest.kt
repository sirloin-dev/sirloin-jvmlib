/*
 * sirloin-jvmlib
 * Distributed under MIT licence
 */
package testcase.small

import com.sirloin.jvmlib.text.toCharArray
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert.assertThat
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import java.util.stream.Stream

/**
 * @since 2022-04-14
 */
class TextUtilsTest {
    @MethodSource("toCharArrayArgs")
    @ParameterizedTest(name = "CharSequence.toCharArray returns CharArray that contains same content")
    fun `CharSequence#toCharArray returns CharArray that contains same content`(
        input: CharSequence,
        expected: CharArray
    ) {
        // given:
        val actual = input.toCharArray()

        // expect:
        assertThat(actual, `is`(expected))
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
