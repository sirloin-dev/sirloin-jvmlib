/*
 * sirloin-jvmlib
 * Distributed under MIT licence
 */
package com.sirloin.jvmlib.util

/**
 * Wrap your sensitive values, password, access token, etc. with this class to prevent it being
 * exposed to outside world via [toString] method, especially those properties of Kotlin data class.
 *
 * @since 2022-02-14
 */
class ProtectedProperty<T> @JvmOverloads constructor(
    val value: T,
    private val scrubText: String = DEFAULT_SCRUB_TEXT
) {
    override fun toString() = scrubText

    companion object {
        private const val DEFAULT_SCRUB_TEXT = "[PROTECTED]"
    }
}
