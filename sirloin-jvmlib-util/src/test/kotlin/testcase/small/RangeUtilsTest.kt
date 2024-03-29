/*
 * sirloin-jvmlib
 * Distributed under MIT licence
 */
package testcase.small

import com.sirloin.jvmlib.util.filledBy
import com.sirloin.jvmlib.util.toArray
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertAll
import org.junit.jupiter.api.assertThrows
import java.util.*

/**
 * @since 2022-02-08
 */
internal class RangeUtilsTest {
    @Test
    fun `Integer range could be transformed as series of numbers`() {
        // given:
        val expected = arrayOf(1, 2, 3, 4, 5, 6, 7)
        val range = 1..7

        // when:
        val actual = range.toArray()

        // then:
        assertAll(
            { actual.size shouldBe 7 },
            { actual shouldBe expected }
        )
    }

    @DisplayName("To use filledBy:")
    @Nested
    inner class FilledByTest {
        @DisplayName("Receiver number must be a positive integer.")
        @Test
        fun receiverMustBePositiveInt() {
            // given:
            val times = -1 * Random().nextInt(Int.MAX_VALUE)

            // expect:
            assertThrows<IllegalArgumentException> {
                times.filledBy {}
            }
        }

        @DisplayName("Creates a list which is filled by given filler function.")
        @Test
        fun listFilledByFunctionIsCreated() {
            // given:
            val times = 1 + Random().nextInt(50)

            // then:
            val actual = times.filledBy { it }

            // expect:
            actual.size shouldBe times
        }
    }
}
