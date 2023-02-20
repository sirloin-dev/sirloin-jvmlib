/*
 * com.sirloin.jvmlib
 * Sir.LOIN Intellectual property. All rights reserved.
 */
package testcase.small.util.number

import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers.`is`
import org.junit.jupiter.api.Test
import test.com.sirloin.util.number.times
import test.com.sirloin.util.number.timesOf
import kotlin.random.Random

/**
 * @since 2023-02-20
 */
class NumberUtilsTest {
    @Test
    fun `'times' function runs expression for given n times`() {
        // given:
        val n = 1 + Random.nextInt(10)

        // and:
        var repetitions = 0

        // then:
        n times { ++repetitions }

        // expect:
        assertThat(repetitions, `is`(n))
    }

    @Test
    fun `'timesOf' function creates results of expression for given n times`() {
        // given:
        val n = 1 + Random.nextInt(10)

        // then:
        val result = n timesOf { it }

        // expect:
        assertThat(result.size, `is`(n))
    }
}
