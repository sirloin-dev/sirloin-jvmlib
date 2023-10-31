/*
 * sirloin-jvmlib
 * Distributed under MIT licence
 */
package testcase.small

import com.sirloin.jvmlib.net.InetAddresses
import com.sirloin.jvmlib.net.isNullOrEmpty
import com.sirloin.jvmlib.net.toInetAddress
import com.sirloin.jvmlib.net.toIpV6AddressBytes
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource
import java.net.InetAddress

/**
 * @since 2022-02-08
 */
internal class InetAddressUtilsTest {
    @ParameterizedTest(name = "InetAddress conversion for ''{0}''")
    @ValueSource(strings = ["localhost", "8.8.8.8"])
    fun `InetAddress to bytes conversion and vice versa should be exactly same`(hostname: String) {
        // when:
        val sourceAddress = InetAddress.getByName(hostname)

        // then:
        val bytes = sourceAddress.toIpV6AddressBytes()

        // expect:
        sourceAddress shouldBe bytes.toInetAddress()
    }

    @Test
    fun `InetAddress#isEmpty works properly on InetAddress#empty result`() {
        // given:
        val emptyInetAddress = InetAddresses.EMPTY

        // expect:
        emptyInetAddress.isNullOrEmpty() shouldBe true
    }
}
