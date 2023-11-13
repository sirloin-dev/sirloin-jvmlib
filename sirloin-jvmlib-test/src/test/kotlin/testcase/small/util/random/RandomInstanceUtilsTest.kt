/*
 * com.sirloin.jvmlib
 * Sir.LOIN Intellectual property. All rights reserved.
 */
package testcase.small.util.random

import io.kotest.matchers.collections.shouldBeOneOf
import io.kotest.matchers.shouldNotBe
import org.junit.jupiter.api.Test
import test.com.sirloin.util.random.randomEnum

/**
 * @since 2022-07-14
 */
internal class RandomInstanceUtilsTest {
    @Test
    fun `Returns any instance of given enum where no acceptFn`() {
        // then:
        val enumValue = randomEnum<GenericEnum>()

        // expect:
        enumValue shouldBeOneOf (GenericEnum.values().asList())
    }

    @Test
    fun `Returns any instance of given enum which conforms acceptFn`() {
        // then:
        val enumValue = randomEnum<GenericEnum> { it != GenericEnum.UNDEFINED }

        // expect:
        enumValue shouldNotBe GenericEnum.UNDEFINED
    }

    private enum class GenericEnum {
        FIRST,
        SECOND,
        THIRD,
        UNDEFINED;
    }
}
