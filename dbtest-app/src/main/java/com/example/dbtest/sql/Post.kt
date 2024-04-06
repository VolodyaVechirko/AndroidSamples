package com.example.dbtest.sql

import android.annotation.SuppressLint
import android.content.ContentValues
import android.database.Cursor

data class Post(
    override val id: Int,
    val text: String,
    val userId: Int
) : Identity {
    override val table: String = TABLE

    override fun values() = ContentValues().apply {
        put(COLUMN_ID, id)
        put(COLUMN_TEXT, text)
        put(COLUMN_USER_ID, userId)
    }

    companion object {
        const val TABLE = "post"
        const val COLUMN_ID = Identity.ID
        const val COLUMN_TEXT = "text"
        const val COLUMN_USER_ID = "user_id"

        const val CREATE = "CREATE TABLE $TABLE (" +
                "$COLUMN_ID INTEGER PRIMARY KEY, " +
                "$COLUMN_TEXT TEXT NOT NULL, " +
                "$COLUMN_USER_ID INTEGER NOT NULL, " +
                "CONSTRAINT userPostConst FOREIGN KEY ($COLUMN_USER_ID) " +
                "REFERENCES ${User.TABLE} (${User.COLUMN_ID}) " +
                "ON UPDATE CASCADE ON DELETE CASCADE)"

        @SuppressLint("Range")
        fun parse(cursor: Cursor): Post = with(cursor) {
            val id = getInt(getColumnIndex(COLUMN_ID))
            val text = getString(getColumnIndex(COLUMN_TEXT))
            val userId = getInt(getColumnIndex(COLUMN_USER_ID))
            return Post(id, text, userId)
        }
    }
}
