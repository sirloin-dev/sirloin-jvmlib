/*
 * sirloin-jvmlib
 * Distributed under MIT licence
 */
package testcase.small

import com.sirloin.jvmlib.util.FastCollectedLruCacheImpl
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertAll
import kotlin.math.roundToInt

/**
 * @author Francesco Jo(nimbusob@gmail.com)
 * @since 23 - Nov - 2018
 */
internal class FastCollectedLruCacheImplTest {
    private lateinit var sut: FastCollectedLruCacheImpl<String, Any>

    @DisplayName("When larger than 1ms of TTL is given:")
    @Nested
    inner class WhenPracticalTtlIsGiven {
        @BeforeEach
        fun setup() {
            sut = FastCollectedLruCacheImpl(CACHE_SIZE, Long.MAX_VALUE)
        }

        @Test
        fun `consecutive put eliminates eldest entries`() {
            // given:
            val expectedSoftCacheSize = CACHE_SIZE - (CACHE_SIZE * 0.75f).roundToInt()
            for (i in 1..CACHE_SIZE) {
                sut.put("Num_$i", i)
            }

            // then:
            assertAll(
                { (CACHE_SIZE * 0.75f).roundToInt() shouldBe sut.hardCache.size },
                { sut.hardCache["Num_1"] shouldBe null },
                { sut.hardCache["Num_2"] shouldBe null },
                { expectedSoftCacheSize shouldBe sut.softCache.size }
            )
        }

        @Test
        fun `get causes softref cache entry moved into hard cache`() {
            // given:
            for (i in 1..CACHE_SIZE) {
                sut.put("Num_$i", i)
            }

            // when:
            sut.get("Num_1")

            // then:
            assertAll(
                { sut.softCache["Num_1"] shouldBe null },
                { sut.hardCache.containsKey("Num_1") shouldBe true }
            )
        }

        @Test
        fun `data matches with key is gone from soft and hard cache after remove`() {
            // given:
            val key = "testKey"

            // when:
            sut.put(key, "testValue")

            // and:
            sut.get(key) shouldNotBe null

            // then:
            sut.remove(key)

            // expect:
            sut.get(key) shouldBe null
        }

        @Test
        fun `all data in soft and hard cache are gone after clear`() {
            // given:
            for (i in 1..CACHE_SIZE) {
                sut.put("Num_$i", i)
            }

            // then:
            sut.clear()

            // expect:
            assertAll(
                { sut.hardCache.isEmpty() shouldBe true },
                { sut.softCache.isEmpty() shouldBe true }
            )
        }

        @Test
        fun `expired data is ignored and removed even if it is resides in hard cache`() {
            // given:
            val key = "testKey"

            // when:
            sut.put(key, "testValue")

            // and:
            val cachedData = sut.hardCache[key]

            // then:
            cachedData shouldNotBe null

            // and: "Force expire data in cache with key"
            cachedData!!.expireAt = 0L

            // expect:
            sut.get(key) shouldBe null
        }
    }

    @Test
    fun `cache practically never expires if provided TTL is smaller than 1`() {
        // given:
        sut = FastCollectedLruCacheImpl(CACHE_SIZE, -1L)
        val key = "testKey"

        // when:
        sut.put(key, "testValue")

        // and:
        val cachedData = sut.hardCache[key]

        // then:
        cachedData shouldNotBe null
    }

    companion object {
        private const val CACHE_SIZE = 10
    }
}
