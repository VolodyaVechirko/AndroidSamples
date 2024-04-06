package com.example.dbtest.sql

import android.database.Cursor
import android.util.Log

class PostDao(val helper: DatabaseHelper) {

    fun insert(post: Post): Long {
        helper.writableDatabase.use { db ->
            return db.insertOrReplace(post)
        }
    }

    fun insert(posts: List<Post>) {
        val db = helper.writableDatabase
        val sql = "INSERT INTO ${Post.TABLE} VALUES(?, ?, ?)"
        val statement = db.compileStatement(sql)
        db.beginTransaction()
        try {
            posts.forEach {
                with(statement) {
                    clearBindings()
                    bindLong(1, it.id.toLong())
                    bindString(2, it.text)
                    bindLong(3, it.userId.toLong())
                    execute()
                }
            }
            db.setTransactionSuccessful()
        } finally {
            db.endTransaction()
        }
    }

    fun getAll(): List<Post> {
        val db = helper.readableDatabase
        var cursor: Cursor? = null
        try {
            cursor = db.query(Post.TABLE)
            if (cursor != null && cursor.moveToFirst()) {
                val list = mutableListOf<Post>()
                do {
                    list.add(Post.parse(cursor))
                } while (cursor.moveToNext())
                return list
            }
        } catch (e: Exception) {
            Log.e(TAG, "Exception: " + e.message)
        } finally {
            cursor?.close()
            db.close()
        }
        return emptyList()
    }

    fun delete(post: Post) {
        helper.writableDatabase.use { db ->
            db.delete(post)
        }
    }

    fun delete(id: Int) {
        delete(Post(id, "ignored", 0))
    }

    fun deleteAll() {
        helper.writableDatabase.use { db ->
            db.delete(Post.TABLE, null, null)
        }
    }

    companion object {
        private const val TAG = "PostDao"
    }
}
