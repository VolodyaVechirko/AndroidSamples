package com.example.webtest.db

import android.content.Context
import android.util.Log
import com.example.webtest.db.sql.DatabaseHelper
import com.example.webtest.db.sql.PostDao
import com.example.webtest.db.sql.UserDao

class Sql(val context: Context) {
    val db = DatabaseHelper(context.applicationContext)

    val userDao by lazy { UserDao(db) }
    val postDao by lazy { PostDao(db) }

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