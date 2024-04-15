/*
 * com.sirloin.jvmlib
 * Sir.LOIN Intellectual property. All rights reserved.
 */
package test.com.sirloin.util.random

inline fun <reified T : Enum<T>> randomEnum(acceptFn: ((T) -> Boolean) = { true }): T =
    enumValues<T>().randomButOnly(acceptFn)

inline fun <T> Array<T>.randomButOnly(acceptFn: (T) -> Boolean): T =
    loopUntil({ random() }, acceptFn)

inline fun <T> Collection<T>.randomButOnly(acceptFn: (T) -> Boolean): T =
    loopUntil({ random() }, acceptFn)

inline fun <T> loopUntil(valueProvider: () -> T, acceptFn: (T) -> Boolean): T {
    var chosen = valueProvider()
    while (!acceptFn(chosen)) {
        chosen = valueProvider()
    }

    return chosen
}
