package com.example.webtest.db.sql

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log

class DatabaseHelper(context: Context) :
    SQLiteOpenHelper(context.applicationContext, DATABASE_NAME, null, DATABASE_VERSION) {

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
        private const val DATABASE_VERSION = 2
        private const val DATABASE_NAME = "testdb"
    }
}
