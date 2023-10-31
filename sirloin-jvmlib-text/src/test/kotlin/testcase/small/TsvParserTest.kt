/*
 * com.sirloin.jvmlib
 * Sir.LOIN Intellectual property. All rights reserved.
 */
package testcase.small

import com.sirloin.jvmlib.text.DUPLICATED_HEADERS_FOUND
import com.sirloin.jvmlib.text.NO_CONTENT_IN_STREAM
import com.sirloin.jvmlib.text.TsvParser
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertAll
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource
import java.io.File
import java.io.IOException
import java.net.URL

/**
 * @since 2022-05-23
 */
@Suppress("ClassName")  // For using class name literal as test names
internal class TsvParserTest {
    @Nested
    inner class `Parse without headers` {
        @Test
        fun `throws IOException if given File is inaccessible`() {
            assertThrows<IOException> { TsvParser.parse(File("")) }
        }

        @Test
        fun `empty lines are filtered as nothing`() {
            // given:
            val resource = "tsvParser/empty_file.tsv".asResource().openStream()

            // then:
            val parsed = TsvParser.parse(resource)

            // expect:
            parsed shouldBe emptyList()
        }

        @Test
        fun `tsv file is parsed`() {
            // given:
            val resource = "tsvParser/no_content.tsv".asResource().openStream()

            // then:
            val parsed = TsvParser.parse(resource)

            // expect:
            assertAll(
                { parsed.size shouldBe 1 },
                { parsed shouldBe listOf(listOf("h1", "h2", "h3")) }
            )
        }

        @Test
        fun `length of each entries are expanded to the longest one`() {
            // given:
            val resource = "tsvParser/various_length.tsv".asResource().openStream()

            // then:
            val parsed = TsvParser.parse(resource)

            // expect:
            assertAll(
                { parsed.size shouldBe 6 },
                {
                    parsed shouldBe listOf(
                        listOf("h1", "h2", "h3", "h4", null),
                        listOf("v1", null, null, null, null),
                        listOf("v2", "v3", null, null, null),
                        listOf("v4", "v5", "v6", "v7", "v8"),
                        listOf(""  , ""  , ""  , ""  , ""  ),
                        listOf("v9", null, null, null, null),
                    )
                }
            )
        }
    }

    @Nested
    inner class `Parse with headers` {
        @Test
        fun `throws IOException if given File is inaccessible`() {
            assertThrows<IOException> { TsvParser.parseWithHeaders(File("")) }
        }

        @ParameterizedTest(name = "throws IOException on {0} that is malformed tsv")
        @ValueSource(strings = ["empty_file.tsv", "no_content.tsv"])
        fun `throws IOException if given File is not parsable`(filename: String) {
            // then:
            val thrown = assertThrows<IOException> {
                TsvParser.parseWithHeaders("tsvParser/$filename".asResource().openStream())
            }

            // expect:
            thrown.message shouldBe NO_CONTENT_IN_STREAM
        }

        @Test
        fun `throws IOException if given File contains duplicated header`() {
            // then:
            val thrown = assertThrows<IOException> {
                TsvParser.parseWithHeaders("tsvParser/duplicate_header.tsv".asResource().openStream())
            }

            // expect:
            thrown.message!!.startsWith(DUPLICATED_HEADERS_FOUND) shouldBe true
        }

        @Test
        fun `tsv file is parsed`() {
            // given:
            val resource = "tsvParser/various_length.tsv".asResource().openStream()

            // then:
            val parsed = TsvParser.parseWithHeaders(resource)

            // expect:
            assertAll(
                { parsed.size shouldBe 5 },
                {
                    parsed shouldBe listOf(
                        linkedMapOf("h1" to "v1", "h2" to null, "h3" to null, "h4" to null),
                        linkedMapOf("h1" to "v2", "h2" to "v3", "h3" to null, "h4" to null),
                        linkedMapOf("h1" to "v4", "h2" to "v5", "h3" to "v6", "h4" to "v7"),
                        linkedMapOf("h1" to ""  , "h2" to ""  , "h3" to ""  , "h4" to ""  ),
                        linkedMapOf("h1" to "v9", "h2" to null, "h3" to null, "h4" to null),
                    )
                }
            )
        }
    }

    private fun String.asResource(): URL {
        val resource = this@TsvParserTest.javaClass.classLoader.getResource(this)

        return resource ?: run {
            val baseUrl = this@TsvParserTest.javaClass.classLoader.getResource("")
            throw IOException("Resource is not found on ${baseUrl}$this")
        }
    }
}
