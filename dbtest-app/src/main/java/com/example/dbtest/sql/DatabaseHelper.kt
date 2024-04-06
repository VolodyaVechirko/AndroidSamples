package com.example.dbtest.sql

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log

/**
 * Creates inMemory database if database name is null
 */
class DatabaseHelper(
    context: Context,
    inMemory: Boolean = false
) : SQLiteOpenHelper(
    context.applicationContext,
    if (inMemory) null else DATABASE_NAME,
    null,
    DATABASE_VERSION
) {

    val userDao by lazy { UserDao(this) }

    val postDao by lazy { PostDao(this) }

    override fun onCreate(db: SQLiteDatabase) {
        try {
            db.execSQL(User.CREATE)
            db.execSQL(Post.CREATE)
            Log.d(TAG, "Database created!")
        } catch (e: Exception) {
            Log.e(TAG, "onCreate", e)
        }
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS ${User.TABLE}")
        db.execSQL("DROP TABLE IF EXISTS ${Post.TABLE}")

        // Create tables again
        onCreate(db)
    }

    override fun onOpen(db: SQLiteDatabase) {
        super.onOpen(db)
        //enable foreign key constraints like ON UPDATE CASCADE, ON DELETE CASCADE
        db.execSQL("PRAGMA foreign_keys=ON;")
    }

    companion object {
        private const val TAG = "DatabaseHelper"
        private const val DATABASE_VERSION = 1
        private const val DATABASE_NAME = "testdb"
    }
}
