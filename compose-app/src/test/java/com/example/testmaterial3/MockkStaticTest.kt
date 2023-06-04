package com.example.testmaterial3

import android.util.Base64
import io.mockk.every
import io.mockk.mockkStatic
import org.hamcrest.CoreMatchers
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Test
import java.time.Instant

class MockkStaticTest {
    @Test
    fun userTokenTest() {
        mockkStatic(Base64::class)
        every { Base64.decode(any<String>(), any()) } returns ByteArray(16)
        val bytes = Base64.decode("ololo", Base64.NO_WRAP)

        val expireDate = Instant.ofEpochSecond(1555587645)

        val expectedExpireDate = Instant.ofEpochSecond(1555587645)
        assertThat(expireDate, CoreMatchers.`is`(expectedExpireDate))
    }
}