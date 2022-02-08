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
 * Annotates that marked test suite is classified as JUnit Small sized test.
 *
 * We perform small/unit test to confirm single programme component is fully functional.
 * We instrument this kind of tests very frequently during development stage -
 * therefore it should be small, fast and concise.
 *
 * Introducing test doubles such as mocks, spies, etc. for speed and stability is encouraged on this stage.
 *
 * @since 2022-02-14
 */
@Tag(TestSizes.SMALL)
annotation class SmallTest
