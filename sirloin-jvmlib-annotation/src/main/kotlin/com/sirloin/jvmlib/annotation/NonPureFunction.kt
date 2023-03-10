/*
 * com.sirloin.jvmlib
 * Sir.LOIN Intellectual property. All rights reserved.
 */
package com.sirloin.jvmlib.annotation

/**
 * Annotates a programme element which MAY incur a side effect,
 * by modifying state of the given object and/or an object that includes annotated method.
 *
 * @since 2022-02-14
 */
@Target(AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.SOURCE)
annotation class NonPureFunction
