/*
 * com.sirloin.jvmlib
 * Sir.LOIN Intellectual property. All rights reserved.
 */
package testcase.small

import com.sirloin.jvmlib.util.firstOrThrow
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test

/**
 * @since 2022-07-08
 */
internal class EnumUtilsTest {
    @Test
    fun `firstOrThrow is exactly same as custom implementation of enum reverse-side lookup function`() {
        // given:
        val expected = Foo.from("A")

        // then:
        val actual = Foo.entries.firstOrThrow(Foo::code, "A")

        // expect:
        actual shouldBe expected
    }
}

private enum class Foo(val code: Any) {
    BAR_1(1),
    BAR_A("A");

    companion object {
        fun from(code: Any?): Foo = entries.firstOrNull { it.code == code }
            ?: throw IllegalArgumentException("Cannot convert '${code}' as ${Foo::class.simpleName}")
    }
}
