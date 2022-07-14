/*
 * com.sirloin.jvmlib
 * Sir.LOIN Intellectual property. All rights reserved.
 */
package testcase.small.util.random

import org.hamcrest.CoreMatchers.*
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers.oneOf
import org.junit.jupiter.api.RepeatedTest
import org.junit.jupiter.api.Test
import test.com.sirloin.util.random.randomEnum

/**
 * @since 2022-07-14
 */
@Suppress("ClassName")  // For using class name literal as test names
class RandomInstanceUtilsTest {
    @Test
    fun `Returns any instance of given enum where no acceptFn`() {
        // then:
        val enumValue = randomEnum(GenericEnum::class)

        // expect:
        assertThat(enumValue, oneOf(*GenericEnum.values()))
    }

    // chances to select all elements = (1/4) ^ 4 = 1/256
    @RepeatedTest(256)
    fun `Returns any instance of given enum which conforms acceptFn`() {
        // then:
        val enumValue = randomEnum(GenericEnum::class) { it != GenericEnum.UNDEFINED }

        // expect:
        assertThat(enumValue, not(GenericEnum.UNDEFINED))
    }

    enum class GenericEnum {
        FIRST,
        SECOND,
        THIRD,
        UNDEFINED;
    }
}
