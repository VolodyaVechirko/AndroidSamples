package com.example.testmaterial3

import org.junit.Assert.assertEquals
import org.junit.Test
import java.math.BigDecimal
import kotlin.math.PI

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    @Test
    fun addition_isCorrect() {
        assertEquals(4, 2 + 2)
        assertEquals("3.141592653589793", getPI().take(17))
    }

    private fun getPI(): String {
        val scaledPi = BigDecimal(PI).setScale(100)

        // Display Pi
        println("Pi: $scaledPi")

        val i = scaledPi.toString().indexOfFirst { it == '0' }
        println("indexOfFirst 0: $i")

        return scaledPi.toString()
    }
}