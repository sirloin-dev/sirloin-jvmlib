/*
 * sirloin-jvmlib
 * Distributed under MIT licence
 */
package testcase.small

import com.sirloin.jvmlib.text.unicodeGraphemeCount
import io.kotest.matchers.shouldBe
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import java.util.stream.Stream

/**
 * @since 2022-02-08
 */
internal class EmojiUtilsTest {
    @ParameterizedTest(name = "\"{1}\" glyph count must be: {0}")
    @MethodSource("testUnicodeGraphemeCountArgs")
    fun `unicodeGraphemeCount for various inputs`(length: Int, charSeq: String) {
        length shouldBe charSeq.unicodeGraphemeCount()
    }

    companion object {
        @JvmStatic
        @Suppress("unused")
        fun testUnicodeGraphemeCountArgs(): Stream<Arguments> {
            return Stream.of(
                Arguments.of(0, ""),
                // Thai NFC case
                Arguments.of(28, "I 💚 Thai(บล็อกยูนิโคด) language"),
                // Emoji (7 characters for 1 glyph)
                Arguments.of(1, "🏴󠁧󠁢󠁷󠁬󠁳󠁿"),
                // Chinese
                Arguments.of(14, "威爾士國旗看起來像： 🏴󠁧󠁢󠁷󠁬󠁳󠁿🏴󠁧󠁢󠁷󠁬󠁳󠁿🏴󠁧󠁢󠁷󠁬󠁳󠁿"),
                // Japanese
                Arguments.of(10, "日本🇯🇵 大好き💚️!!"),
                // Korean NFD case
                Arguments.of(3, "\u1100\u1161\u1102\u1161\u1103\u1161")
            )
        }
    }
}
