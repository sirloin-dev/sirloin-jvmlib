/*
 * sirloin-jvmlib
 * Distributed under MIT licence
 */
package testcase.small

import com.sirloin.jvmlib.util.toByteArray
import com.sirloin.jvmlib.util.toUUID
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import java.util.*

class UuidUtilsTest {
    @Test
    fun `UUID to byteArray length must be 16`() {
        // given:
        val uuid = UUID.randomUUID()

        // when:
        val actual = uuid.toByteArray()

        // then:
        assertEquals(16, actual.size)
    }

    @Test
    fun `byteArray to uuid operates only on 16 bytes-long byteArray`() {
        // given:
        val randomBytes = ByteArray(20).apply {
            Random().nextBytes(this)
        }

        // expect:
        assertThrows<IllegalArgumentException> {
            randomBytes.toUUID()
        }
    }

    @Test
    fun `conversion to byteArray and vice versa must be same`() {
        // given:
        val expected = UUID.randomUUID()

        // when:
        val array = expected.toByteArray()

        // then:
        val actual = array.toUUID()

        // expect:
        assertEquals(expected, actual)
    }
}
