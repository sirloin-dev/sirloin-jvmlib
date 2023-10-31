/*
 * FJ's utilities
 *
 * Distributed under no licences and no warranty.
 * Use this software at your own risk.
 */
package com.sirloin.jvmlib.util

import java.lang.ref.SoftReference
import java.util.concurrent.ConcurrentHashMap
import kotlin.math.roundToInt

/**
 * A simple LRU cache implementation.
 * This cache is maybe useful for actively accessed memory-consuming objects.
 *
 * Based on initial cache size, stored items which access rate drops lower than 25% will be
 * aggressively cleared from the memory.
 *
 * Note that those data marked as an eviction candidate, and its actual eviction depends on the
 * GC settings of execution environment.
 *
 * Due to its GC-friendly feature, many operations that depends on size such as `size()`,
 * `entrySet()`, etc., could derive inconsistent results; therefore, no such map operations are
 * offered.
 *
 * @author Francesco Jo(nimbusob@gmail.com)
 * @since 19 - Nov - 2018
 */
interface FastCollectedLruCache<K : Any, V> {
    /**
     * @return the previous value associated with a key, or `null` if there was no association.
     */
    fun put(key: K, value: V): V?

    fun get(key: K): V?

    fun remove(key: K): V?

    fun clear()

    companion object {
        private const val DEFAULT_EXPIRE_MILLISECONDS = 30 * 1000L

        /**
         * Creates a new instance of [FastCollectedLruCache] with given parameters.
         * @param maxCapacity the maximum number of entries this cache can hold.
         * @param expireMilliseconds the maximum number of milliseconds this cache can hold.
         * Default is 30 seconds. Value below 1 for no expiration.
         */
        fun <K : Any, V> create(
            maxCapacity: Int,
            expireMilliseconds: Long = DEFAULT_EXPIRE_MILLISECONDS
        ): FastCollectedLruCache<K, V> = FastCollectedLruCacheImpl(
            maxCapacity,
            expireMilliseconds
        )
    }
}

/**
 * @author Francesco Jo(nimbusob@gmail.com)
 * @since 23 - Nov - 2018
 */
internal class FastCollectedLruCacheImpl<K : Any, V>(
    maxCapacity: Int,
    private val expireMilliseconds: Long
) : FastCollectedLruCache<K, V> {
    @Suppress("MagicNumber")
    private val hardCacheSize = (maxCapacity * 0.75f).roundToInt()
    private val softCacheSize = maxCapacity - hardCacheSize

    // @VisibleForTesting
    @Suppress("MagicNumber")
    internal val hardCache = object : LinkedHashMap<K, CachedEntry<V>>(hardCacheSize, 0.75f, true) {
        override fun removeEldestEntry(eldest: MutableMap.MutableEntry<K, CachedEntry<V>>): Boolean {
            if (size > hardCacheSize) {
                softCache[eldest.key] = SoftReference(eldest.value)
                return true
            }

            return false
        }
    }

    // @VisibleForTesting
    internal val softCache = ConcurrentHashMap<K, SoftCachedEntry<V>>(softCacheSize)

    override fun put(key: K, value: V): V? {
        softCache.remove(key)

        synchronized(hardCache) {
            val entry = CachedEntry.create(value, expireMilliseconds)
            return hardCache.put(key, entry)?.data
        }
    }

    override fun get(key: K): V? {
        synchronized(hardCache) {
            hardCache[key]?.takeIf { it.isNotExpiredIn(hardCache, key) }
                ?.let { entry ->
                    return entry.data.also { hardCache.promoteEntry(key, entry) }
                }
        }

        var maybeValue: V? = null
        softCache[key]?.takeIf { it.get() != null }?.get()
            ?.takeIf { it.isNotExpiredIn(softCache, key) }
            ?.let { entry ->
                synchronized(hardCache) { hardCache.promoteEntry(key, entry) }
                maybeValue = entry.data
        }

        softCache.remove(key)
        return maybeValue
    }

    override fun remove(key: K): V? {
        softCache.remove(key)
        synchronized(hardCache) {
            return hardCache.remove(key)?.data
        }
    }

    override fun clear() {
        synchronized(hardCache) {
            hardCache.clear()
        }
        softCache.clear()
    }

    /**
     * Checks expiration of given data, and removes it from given cache if it is expired.
     */
    private fun CachedEntry<V>.isNotExpiredIn(cache: MutableMap<K, *>, key: K): Boolean {
        val isExpired = isExpiredAt(System.currentTimeMillis())

        if (isExpired) {
            cache.remove(key)
        }

        return !isExpired
    }

    /**
     * Moves given entry to the first position of hard cache.
     */
    private fun LinkedHashMap<K, CachedEntry<V>>.promoteEntry(key: K, entry: CachedEntry<V>) {
        this.remove(key)
        this[key] = entry
    }

    // @OpenForTesting
    data class CachedEntry<T>(
        val data: T,
        // @OpenForTesting
        var expireAt: Long
    ) {
        fun isExpiredAt(now: Long) = now >= expireAt

        companion object {
            fun <T> create(data: T, expireMilliseconds: Long): CachedEntry<T> {
                val now = System.currentTimeMillis()

                val expireAt1 = if (expireMilliseconds < 1L) {
                    Long.MAX_VALUE
                } else {
                    now + expireMilliseconds
                }

                val expireAt2 = expireAt1.let {
                    if (it < 1) {
                        Long.MAX_VALUE
                    } else {
                        it
                    }
                }

                return CachedEntry(data, expireAt2)
            }
        }
    }
}

private typealias SoftCachedEntry<T> = SoftReference<FastCollectedLruCacheImpl.CachedEntry<T>>
