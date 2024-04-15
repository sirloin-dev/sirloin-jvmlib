/*
 * com.sirloin.jvmlib
 * Sir.LOIN Intellectual property. All rights reserved.
 */
package com.sirloin.jvmlib.util

import kotlin.enums.EnumEntries
import kotlin.reflect.KProperty

/**
 * This function is exactly same as `Foo.from` as described below:
 *
 * ```
 * enum class Foo(val code: Any) {
 *     BAR_1(1),
 *     BAR_A("A");
 *
 *     companion object {
 *         fun from(code: Any?) = values().firstOrNull { it.code == code }
 *             ?: throw IllegalArgumentException("Cannot convert '${code}' as ${Foo::class.simpleName}")
 *     }
 * }
 * ```
 *
 * where [field] must be one of publicly accessible of [T].
 *
 * Note that this method would be slow if there are many(20+) fields in enum since time complexity of this method
 * is O(N).
 *
 * @since 07-07-2022
 */
inline fun <reified T : Enum<T>> EnumEntries<T>.firstOrThrow(field: KProperty<*>, value: Any?): T {
    return this.firstOrNull { enumElement ->
        T::class.java.getDeclaredField(field.name).also { it.isAccessible = true }.get(enumElement) == value
    } ?: throw IllegalArgumentException("Cannot convert '${value}' as ${T::class.simpleName}")
}

/**
 * Works as the same as [firstOrThrow]; however this function is for some situations where basic comparing by value
 * does not meet the requirements. For example:
 *
 * ```
 * enum class Month(val code: String) {
 *     JANUARY("January"),
 *     FEBRUARY("February");
 * }
 *
 * // This will throw IllegalArgumentException:
 * Month.entries.firstOrThrow(Month::code, "january")
 *
 * // This will work:
 * Month.entries.firstOrThrow(Month::code) { it.toString().lowercase() == "january" }
 * ```
 */
inline fun <reified T : Enum<T>> EnumEntries<T>.firstOrThrow(
    field: KProperty<*>,
    predicate: (Any) -> Boolean
): T {
    val values = ArrayList<Any>()

    return this.firstOrNull { enumElement ->
        val value = T::class.java.getDeclaredField(field.name).also { it.isAccessible = true }.get(enumElement)
        values.add(value)
        predicate(value)
    } ?: throw IllegalArgumentException("None of any values${values} matches the predicate.")
}
