/*
 * com.sirloin.jvmlib
 * Sir.LOIN Intellectual property. All rights reserved.
 */
package test.com.sirloin.util.random

inline fun <reified T : Enum<T>> randomEnum(acceptFn: ((T) -> Boolean) = { true }): T {
    val enums = enumValues<T>()

    var chosen = enums.random()
    while (!acceptFn(chosen)) {
        chosen = enums.random()
    }

    return chosen
}
