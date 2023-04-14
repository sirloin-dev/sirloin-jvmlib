/*
 * com.sirloin.jvmlib
 * Sir.LOIN Intellectual property. All rights reserved.
 */
package testcase.small

import com.sirloin.jvmlib.util.assertSingleOrNull
import com.sirloin.jvmlib.util.subList
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.CoreMatchers.not
import org.hamcrest.CoreMatchers.nullValue
import org.hamcrest.MatcherAssert.assertThat
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import kotlin.random.Random

/**
 * @since 2023-04-10
 */
class CollectionUtilsTest {
    @Test
    fun `single argument taking subList method on List returns a copy of list from given index to end of its size`() {
        // given:
        val times = 3 + Random.nextInt(10)
        val list = IntArray(times) { it }.toList()

        // when: "must be 0 or 1 in case of times is determined to 3"
        val chopIndex = Random.nextInt(2)

        // then:
        val actual = list.subList(chopIndex)

        // expect:
        assertThat(actual.size, `is`(times - chopIndex))
    }

    @DisplayName("assertSingleOrNull should:")
    @Nested
    inner class WhenAssertSingleOrNull {
        @DisplayName("return null if given receiver Collection is empty")
        @Test
        fun returnNull() {
            // given:
            val emptyList = emptyList<Int>()

            // when:
            val actual = emptyList.assertSingleOrNull()

            // then:
            assertThat(actual, `is`(nullValue()))
        }

        @DisplayName("return single element if given receiver Collection is single")
        @Test
        fun returnSingle() {
            // given:
            val emptyList = listOf(Random.nextInt())

            // when:
            val actual = emptyList.assertSingleOrNull()

            // then:
            assertThat(actual, not(nullValue()))
        }

        @DisplayName("throw IllegalArgumentException if given receiver Collection is multiple")
        @Test
        fun throwException() {
            // given:
            val emptyList = listOf(Random.nextInt(), Random.nextInt())

            // expect:
            assertThrows<IllegalArgumentException> {
                emptyList.assertSingleOrNull()
            }
        }
    }
}
