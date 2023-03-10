/*
 * com.sirloin.jvmlib
 * Sir.LOIN Intellectual property. All rights reserved.
 */
package com.sirloin.jvmlib.annotation

/**
 * Annotates a programme element which should be non-open for design, but declared as open for creating
 * proxy and/or mocks during tests.
 *
 * Read [kotlin-allopen](https://kotlinlang.org/docs/all-open-plugin.html) compiler plugin document for details.
 *
 * @since 2022-02-14
 */
@Target(
    AnnotationTarget.CLASS,
    AnnotationTarget.FIELD,
    AnnotationTarget.CONSTRUCTOR,
    AnnotationTarget.FUNCTION,
    AnnotationTarget.PROPERTY,
    AnnotationTarget.TYPE,
)
@Retention(AnnotationRetention.SOURCE)
annotation class OpenForTesting
