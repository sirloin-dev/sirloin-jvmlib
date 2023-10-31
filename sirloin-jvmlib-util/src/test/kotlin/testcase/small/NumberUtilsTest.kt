/*
 * sirloin-jvmlib
 * Distributed under MIT licence
 */
package testcase.small

import com.sirloin.jvmlib.util.isIntegerValue
import io.kotest.matchers.shouldBe
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import java.math.BigDecimal
import java.util.stream.Stream

/**
 * @author Francesco Jo(nimbusob@gmail.com)
 * @since 2022-10-12
 */
internal class NumberUtilsTest {
    @ParameterizedTest(name = "BigDecimal#isIntegerValue returns correct values as expected for inputs")
    @MethodSource("BigDecimal_isIntegerValueInputs")
    fun `BigDecimal#isIntegerValue returns correct values as expected for inputs`(
        input: String, expected: Boolean
    ) {
        // given:
        val number = BigDecimal(input)

        // then:
        val actual = number.isIntegerValue()

        // expect:
        actual shouldBe expected
    }

    companion object {
        @Suppress("TestFunctionName")
        @JvmStatic
        fun BigDecimal_isIntegerValueInputs(): Stream<Arguments> = Stream.of(
            Arguments.of("1.1", false),
            Arguments.of("1.0", true),
            Arguments.of("1", true),
            Arguments.of("0.0", true),
            Arguments.of("-1", true),
            Arguments.of("-1.0", true),
            Arguments.of("-1.1", false)
        )
    }
}
