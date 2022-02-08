/*
 * sirloin-jvmlib
 * Distributed under MIT licence
 */
package test.com.sirloin.annotation

import org.junit.jupiter.api.Tag
import test.com.sirloin.TestSizes

/**
 * Annotates that marked test suite is classified as JUnit Large sized test.
 *
 * We perform large/integration test to confirm combination of programme components are fully functional.
 * Test doubles such as mocks, spies, etc. should be avoided as much as possible on this test stage.
 *
 * @since 2022-02-14
 */
@Tag(TestSizes.LARGE)
annotation class LargeTest
