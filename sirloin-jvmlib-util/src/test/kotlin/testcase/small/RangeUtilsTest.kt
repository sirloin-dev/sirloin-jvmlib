/*
 * sirloin-jvmlib
 * Distributed under MIT licence
 */
package testcase.small

import com.sirloin.jvmlib.util.toArray
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers.`is`
import org.junit.jupiter.api.Test

class RangeUtilsTest {
    @Test
    fun `Integer range could be transformed as series of numbers`() {
        // given:
        val expected = arrayOf(1, 2, 3, 4, 5, 6, 7)
        val range = 1..7

        // when:
        val actual = range.toArray()

        // then:
        assertThat(actual.size, `is`(7))
        assertThat(actual, `is`(expected))
    }
}
