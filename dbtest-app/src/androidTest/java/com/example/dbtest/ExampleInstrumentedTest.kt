package com.example.dbtest

import androidx.room.Room
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.dbtest.room.RoomDB

import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Assert.*

@RunWith(AndroidJUnit4::class)
class ExampleInstrumentedTest {
    @Test
    fun useAppContext() {
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        assertEquals("com.example.dbtest", appContext.packageName)

        val db = Room.inMemoryDatabaseBuilder(appContext, RoomDB::class.java)
            .allowMainThreadQueries()
            .build()


    }
}