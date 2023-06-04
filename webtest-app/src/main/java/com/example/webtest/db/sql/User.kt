package com.example.webtest.db.sql

import android.annotation.SuppressLint
import android.content.ContentValues
import android.database.Cursor
import com.example.webviewtest.Identity

data class User(
    override val id: Int,
    val name: String
) : Identity {
    override val table: String = TABLE

    override fun values() = ContentValues().apply {
        put(COLUMN_ID, id)
        put(COLUMN_NAME, name)
    }

    companion object {
        const val TABLE = "user"
        const val COLUMN_ID = Identity.ID
        const val COLUMN_NAME = "name"

        const val CREATE = "CREATE TABLE $TABLE (" +
                "$COLUMN_ID INTEGER PRIMARY KEY, " +
                "$COLUMN_NAME TEXT NOT NULL " +
                ")"

        @SuppressLint("Range")
        fun parse(cursor: Cursor): User = with(cursor) {
            val id = getInt(getColumnIndex(COLUMN_ID))
            val name = getString(getColumnIndex(COLUMN_NAME))
            return User(id, name)
        }
    }
}
