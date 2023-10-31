/*
 * sirloin-jvmlib
 * Distributed under MIT licence
 */
package com.sirloin.jvmlib.text

import com.vdurmont.emoji.EmojiParser
import java.text.BreakIterator

/**
 * Counts actual [Unicode Grapheme count](https://unicode.org/reports/tr29/#Grapheme_Cluster_Boundaries)
 * of given String. For example:
 *
 * | Category       | Literal                              | Length      | Grapheme count |
 * | :---           | :---------                           | :---        | :---           |
 * | Thai composite | บล็อกยูนิโคด                         | 12          | 9              |
 * | Emoji          | 🏴󠁧󠁢󠁷󠁬󠁳󠁿                              | 14          | 1              |
 * | Korean NFD     | \u1100\u1161\u1102\u1161\u1103\u1161 | 6           | 3              |
 *
 * @since 2022-02-14
 */
fun String.unicodeGraphemeCount(): Int {
    var count = 0
    EmojiIterator.iterateOn(this) { ++count }
    return count
}

/**
 * @since 2022-02-14
 */
private object EmojiIterator : EmojiParser() {
    /**
     * [List of invisible Unicode characters](http://unicode.org/faq/unsup_char.html)
     *
     * See also: https://www.fileformat.info/info/unicode/category/Cf/list.htm
     */
    @Suppress("MagicNumber")
    private val INVISIBLE_UNICODE_CHARS = setOf(
        0x00AD,                                                         // Soft hyphen
        0x115F, 0x1160,                                                 // Hangul Jamo fillers
        0x200B,                                                         // Zero-width space
        0x200C, 0x200D,                                                 // Cursive joiners
        0x200E, 0x200F, 0x202A, 0x202B, 0x202C, 0x202D, 0x202E,         // Bidirectional format controls
        0x205F, 0x2061, 0x2062, 0x2063, 0x2064, 0x2065, 0x2066, 0x2067, // General punctuations
        0x2068, 0x2069, 0x206A, 0x206B, 0x206C, 0x206D, 0x206E, 0x206F, // General punctuations (cont'd)
        0xFE00, 0xFE01, 0xFE02, 0xFE03, 0xFE04, 0xFE05, 0xFE06, 0xFE07, // Variation selectors
        0xFE08, 0xFE09, 0xFE0A, 0xFE0B, 0xFE0C, 0xFE0D, 0xFE0E, 0xFE0F, // Variation selectors (cont'd)
        0x2060, 0xFEFF                                                  // Word joiners
    )

    fun iterateOn(seq: String, callback: (String) -> Unit) {
        if (seq.isEmpty()) {
            return
        }

        val it = BreakIterator.getCharacterInstance().apply { setText(seq) }
        var idx = 0

        while (idx < seq.length) {
            val emojiEnd = getEmojiEndPos(seq.toCharArray(), idx)
            if (emojiEnd > -1) {
                callback(seq.substring(idx, emojiEnd))
                idx = emojiEnd
                continue
            }

            val codePoint = seq.codePointAt(idx)

            val oldIdx = idx
            idx = it.following(idx)

            if (!INVISIBLE_UNICODE_CHARS.contains(codePoint)) {
                callback(seq.substring(oldIdx, idx))
            }
        }
    }
}
