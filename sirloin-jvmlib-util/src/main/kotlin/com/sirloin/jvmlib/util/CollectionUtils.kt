/*
 * com.sirloin.jvmlib
 * Sir.LOIN Intellectual property. All rights reserved.
 */
package com.sirloin.jvmlib.util

/**
 * Returns a view of the portion of this list between the specified [startIndex] and continues to the end of the list.
 * The returned list is shallow-copied of receiver List, so non-structural changes in the returned list
 * are reflected in this list, and vice-versa.
 *
 * Structural changes in the base list make the behaviour of the view undefined.
 */
fun <T> List<T>.subList(startIndex: Int): List<T> = subList(startIndex, this.size)
