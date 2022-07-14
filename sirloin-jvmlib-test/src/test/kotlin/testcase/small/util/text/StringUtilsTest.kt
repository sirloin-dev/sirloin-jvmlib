package testcase.small.util.text

import org.hamcrest.CoreMatchers.both
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers.greaterThanOrEqualTo
import org.hamcrest.Matchers.lessThanOrEqualTo
import org.hamcrest.text.IsEmptyString.emptyString
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import test.com.sirloin.util.text.randomFillChars

@Suppress("ClassName")  // For using class name literal as test names
class StringUtilsTest {
    @Nested
    inner class `Exception is raised if` {
        @Test
        fun `min is less than 0`() {
            assertThrows<IllegalArgumentException> { randomFillChars('.', -1) }
        }

        @Test
        fun `max is less than 0`() {
            assertThrows<IllegalArgumentException> { randomFillChars('.', 0, -1) }
        }

        @Test
        fun `max is less than min`() {
            assertThrows<IllegalArgumentException> { randomFillChars('.', 1, 0) }
        }
    }

    @Test
    fun `Returns empty string if min is 0`() {
        // when:
        val chars = randomFillChars('.', 0, 0)

        // expect:
        assertThat(chars, `is`(emptyString()))
    }

    @Test
    fun `Returns filled String if sufficient range is given`() {
        // given:
        val min = 0
        val max = 32

        // when:
        val chars = randomFillChars('.', min, max)

        // expect:
        assertThat(chars.length, `is`(both(greaterThanOrEqualTo(min)).and(lessThanOrEqualTo(max))))
    }
}
