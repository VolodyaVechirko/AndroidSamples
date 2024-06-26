package com.example.testmaterial3

import kotlinx.coroutines.launch
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Test

class CoroutineTest {

    private val testDispatcher = StandardTestDispatcher()

    @Test
    fun standardTest() = runTest(testDispatcher) {
        val userRepo = UserRepository()

        launch { userRepo.register("Alice") }
        launch { userRepo.register("Bob") }
        advanceUntilIdle() // Yields to perform the registrations

        assertEquals(listOf("Alice", "Bob"), userRepo.getAllUsers())
    }

    class UserRepository {
        private val users = mutableSetOf<String>()

        fun register(name: String) {
            users.add(name)
        }

        fun getAllUsers(): List<String> {
            return users.toList()
        }
    }
}
