/*
 * com.sirloin.jvmlib
 * Sir.LOIN Intellectual property. All rights reserved.
 */
package testcase.small

import com.sirloin.jvmlib.util.subList
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert.assertThat
import org.junit.jupiter.api.Test
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
}
