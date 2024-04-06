package com.example.dbtest.room

import android.content.Context
import android.util.Log
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase

@Database(
    entities = [User::class, Post::class],
    exportSchema = false,
    version = 1
)
abstract class RoomDB : RoomDatabase() {
    abstract val userDao: UserDao
    abstract val postDao: PostDao

    companion object {
        fun create(context: Context) =
            Room.databaseBuilder(context, RoomDB::class.java, DB_NAME)
                .fallbackToDestructiveMigration()
                .addCallback(RoomDatabaseCallback())
                .build()

        private const val DB_NAME = "test-room"
    }
}

class RoomDatabaseCallback : RoomDatabase.Callback() {
    private val TAG = "RoomDB"

    override fun onCreate(db: SupportSQLiteDatabase) {
        Log.d(TAG, "onCreate")
    }

    override fun onOpen(db: SupportSQLiteDatabase) {
        Log.d(TAG, "onOpen")
    }

    override fun onDestructiveMigration(db: SupportSQLiteDatabase) {
        Log.d(TAG, "onDestructiveMigration")
    }
}
