/*
 * Copyright 2022 Sir.loin inc. All rights reserved.
 *
 * Sir.loin inc. intellectual property.
 * Use of this software is subject to licence terms.
 */
package test.com.sirloin.annotation

import org.junit.jupiter.api.Tag
import test.com.sirloin.TestSizes

/**
 * Annotates that marked test suite is classified as JUnit Medium sized test.
 *
 * Medium test covers as same range as Small tests; however we can construct test logic directly access to
 * controllable external environments like network, database, filesystem, etc.
 *
 * @since 2022-02-14
 */
@Tag(TestSizes.MEDIUM)
annotation class MediumTest
