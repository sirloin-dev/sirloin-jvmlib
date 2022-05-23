/*
 * sirloin-jvmlib
 * Distributed under MIT licence
 */
package com.sirloin.jvmlib.text

import java.io.*
import java.lang.Integer.max

internal const val NO_CONTENT_IN_STREAM = "No content in given TSV InputStream."
internal const val DUPLICATED_HEADERS_FOUND = "There are duplicated header values in:"

/**
 * Parses TSV file as list of map. For example:
 *
 * ```
 * h1	h2	h3
 * v1	v2	v3
 * v4	v5	v6
 * ```
 *
 * parsed as:
 *
 * ```
 * [
 *   { "h1" : "v1", "h2" : "v2", "h3" : "v3" },
 *   { "h1" : "v4", "h2" : "v5", "h3" : "v6" },
 * ]
 * ```
 *
 * Note that JSON representation of parsed result is for better comprehension.
 *
 * @since 2022-05-23
 */
object TsvParser {

    /**
     * @throws java.io.IOException if given File is not parsable, not readable, and/or not found.
     */
    fun parse(tsvFile: File): List<List<String?>> = FileInputStream(tsvFile).use {
        parse(it)
    }

    /**
     * Parses given input stream as list of string map.
     * Note that `tsvInputStream` is fully consumed and closed after parse.
     *
     * @throws java.io.IOException if `tsvInputStream` is not parsable, and/or not readable.
     */
    fun parse(tsvInputStream: InputStream): List<List<String?>> = tsvInputStream.use {
        var longestLength = 0
        val parsed = BufferedReader(InputStreamReader(tsvInputStream)).readLines()
            .filter { it.isNotEmpty() }
            .map {
                val split = it.split("\t")
                longestLength = max(longestLength, split.size)

                return@map split
            }

        if (parsed.isEmpty()) {
            return parsed
        }

        return@use parsed.map {
            if (it.size == longestLength) {
                it
            } else {
                it.toMutableList<String?>().apply {
                    while (size < longestLength) {
                        add(null)
                    }
                }
            }
        }
    }

    /**
     * @throws java.io.IOException if given File is not parsable, not readable, and/or not found.
     */
    fun parseWithHeaders(tsvFile: File): List<Map<String, String?>> = FileInputStream(tsvFile).use {
        parseWithHeaders(it)
    }

    /**
     * Parses given input stream as list of string map.
     * Note that `tsvInputStream` is fully consumed and closed after parse.
     *
     * @throws java.io.IOException if `tsvInputStream` is not parsable, and/or not readable.
     */
    fun parseWithHeaders(tsvInputStream: InputStream): List<Map<String, String?>> {
        val lines = parse(tsvInputStream)

        if (lines.size < 2) {
            throw IOException(NO_CONTENT_IN_STREAM)
        }

        val headers = lines[0].filterNotNull()
        val headerLength = headers.size
        if (headers.toSet().size != headerLength) {
            throw IOException("$DUPLICATED_HEADERS_FOUND $headers")
        }

        val parsedRows = ArrayList<Map<String, String?>>()
        for (rowIdx in 1 until lines.size) {
            parsedRows.add(LinkedHashMap<String, String?>().apply {
                val row = lines[rowIdx]
                for (colIdx in headers.indices) {
                    put(headers[colIdx], row[colIdx])
                }
            })
        }

        return parsedRows
    }
}
