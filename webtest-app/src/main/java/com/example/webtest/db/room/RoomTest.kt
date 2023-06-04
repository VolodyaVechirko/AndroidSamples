package com.example.webtest.db.room

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
abstract class RoomTest : RoomDatabase() {
    companion object {
        private const val TAG = "RoomTest"
        private const val DB_NAME = "test-room"

        fun create(context: Context) =
            Room.databaseBuilder(context, RoomTest::class.java, DB_NAME)
                .fallbackToDestructiveMigration()
                .addCallback(object : Callback() {
                    override fun onCreate(db: SupportSQLiteDatabase) {
                        super.onCreate(db)
                        Log.d(TAG, "onCreate")
                    }

                    override fun onOpen(db: SupportSQLiteDatabase) {
                        super.onOpen(db)
                        Log.d(TAG, "onOpen")
                    }

                    override fun onDestructiveMigration(db: SupportSQLiteDatabase) {
                        super.onDestructiveMigration(db)
                        Log.d(TAG, "onDestructiveMigration")
                    }
                })
                .build()
    }

    abstract val userDao: UserDao
    abstract val postDao: PostDao
}
