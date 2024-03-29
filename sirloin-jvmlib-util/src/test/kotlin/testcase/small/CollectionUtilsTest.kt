/*
 * com.sirloin.jvmlib
 * Sir.LOIN Intellectual property. All rights reserved.
 */
package testcase.small

import com.sirloin.jvmlib.util.assertSingleOrNull
import com.sirloin.jvmlib.util.sample
import com.sirloin.jvmlib.util.subList
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import kotlin.random.Random

/**
 * @since 2023-04-10
 */
internal class CollectionUtilsTest {
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
        actual.size shouldBe (times - chopIndex)
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
            actual shouldBe null
        }

        @DisplayName("return single element if given receiver Collection is single")
        @Test
        fun returnSingle() {
            // given:
            val emptyList = listOf(Random.nextInt())

            // when:
            val actual = emptyList.assertSingleOrNull()

            // then:
            actual shouldNotBe null
        }

        @DisplayName("throw IllegalArgumentException if given receiver Collection is multiple")
        @Test
        fun throwException() {
            // given:
            val emptyList = listOf(Random.nextInt(), Random.nextInt())

            // expect:
            assertThrows<IllegalArgumentException> { emptyList.assertSingleOrNull() }
        }
    }

    @DisplayName("sample should:")
    @Nested
    inner class WhenAssertSample {
        private val iterable = ArrayList<Int>()

        @BeforeEach
        fun setUp() {
            val times = 3 + Random.nextInt(10)
            iterable.addAll(IntArray(times) { it }.toList())
        }

        @AfterEach
        fun tearDown() {
            iterable.clear()
        }

        @Test
        fun `return a list of random elements on given Iterable`() {
            // given:
            val sampleSize = Random.nextInt(iterable.size)

            // then:
            val actual = iterable.sample(sampleSize)

            // expect:
            actual.size shouldBe sampleSize
        }

        @Test
        fun `contain elements of the original Iterable`() {
            // then:
            val actual = iterable.sample(iterable.size)

            // expect:
            actual.sorted() shouldBe iterable.sorted()
        }
    }
}
