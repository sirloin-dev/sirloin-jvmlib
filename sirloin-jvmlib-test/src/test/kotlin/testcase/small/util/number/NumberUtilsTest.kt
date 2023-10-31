/*
 * com.sirloin.jvmlib
 * Sir.LOIN Intellectual property. All rights reserved.
 */
package testcase.small.util.number

import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test
import test.com.sirloin.util.number.times
import test.com.sirloin.util.number.timesOf
import kotlin.random.Random

/**
 * @since 2023-02-20
 */
internal class NumberUtilsTest {
    @Test
    fun `'times' function runs expression for given n times`() {
        // given:
        val n = 1 + Random.nextInt(10)

        // and:
        var repetitions = 0

        // then:
        n times { ++repetitions }

        // expect:
        repetitions shouldBe n
    }

    @Test
    fun `'timesOf' function creates results of expression for given n times`() {
        // given:
        val n = 1 + Random.nextInt(10)

        // then:
        val result = n timesOf { it }

        // expect:
        result.size shouldBe n
    }
}
