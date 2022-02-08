/*
 * sirloin-jvmlib
 * Distributed under MIT licence
 */
package testcase.small

import com.sirloin.jvmlib.net.InetAddresses
import com.sirloin.jvmlib.net.isNullOrEmpty
import com.sirloin.jvmlib.net.toInetAddress
import com.sirloin.jvmlib.net.toIpV6AddressBytes
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource
import java.net.InetAddress

class InetAddressUtilsTest {
    @ParameterizedTest(name = "InetAddress conversion for ''{0}''")
    @ValueSource(strings = ["localhost", "8.8.8.8"])
    fun `InetAddress to bytes conversion and vice versa should be exactly same`(hostname: String) {
        val sourceAddress = InetAddress.getByName(hostname)
        val bytes = sourceAddress.toIpV6AddressBytes()

        assertEquals(sourceAddress, bytes.toInetAddress())
    }

    @Test
    fun `InetAddress#isEmpty works properly on InetAddress#empty result`() {
        val emptyInetAddress = InetAddresses.EMPTY

        assertTrue(emptyInetAddress.isNullOrEmpty())
    }
}
