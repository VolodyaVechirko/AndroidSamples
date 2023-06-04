package com.example.webtest.db

import android.content.Context
import android.util.Log
import com.example.webtest.db.room.RoomTest

class Room(val context: Context) {
    val db = RoomTest.create(context.applicationContext)

    val userDao = db.userDao
    val postDao = db.postDao

    fun print() {
        Log.d("Users", "----------------------")
        userDao.getAll().forEachIndexed { i, it ->
            Log.d("Users-$i", it.toString())
        }

        Log.d("Posts", "----------------------")
        postDao.getAll().forEachIndexed { i, it ->
            Log.d("Posts-$i", it.toString())
        }
    }
}