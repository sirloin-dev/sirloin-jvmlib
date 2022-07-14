/*
 * com.sirloin.jvmlib
 * Sir.LOIN Intellectual property. All rights reserved.
 */
package test.com.sirloin.util.random

import kotlin.reflect.KClass

fun <T : Enum<*>> randomEnum(klass: KClass<T>, acceptFn: ((T) -> Boolean)? = null): T {
    val enums = klass.java.enumConstants

    var chosen = enums.random()
    if (acceptFn == null) {
        return chosen
    }

    while (!acceptFn(chosen)) {
        chosen = enums.random()
    }

    return chosen
}
