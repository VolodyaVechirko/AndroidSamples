package com.example.dbtest.sql

import android.content.ContentValues
import android.database.sqlite.SQLiteDatabase

// SQLiteDatabase
interface Identity {
    val table: String
    val id: Int
    fun values(): ContentValues

    companion object {
        const val ID = "id"
    }
}

fun SQLiteDatabase.query(
    table: String,
    selection: String? = null,
    args: Array<String>? = null,
    projection: Array<String>? = null,
    groupBy: String? = null,
    having: String? = null,
    orderBy: String? = null,
    limit: String? = null
) = query(table, projection, selection, args, groupBy, having, orderBy, limit)


fun SQLiteDatabase.insertOrIgnore(obj: Identity) =
    insertWithOnConflict(obj.table, null, obj.values(), SQLiteDatabase.CONFLICT_IGNORE)

fun SQLiteDatabase.insertOrReplace(obj: Identity) =
    insertWithOnConflict(obj.table, null, obj.values(), SQLiteDatabase.CONFLICT_REPLACE)

fun SQLiteDatabase.update(obj: Identity) =
    update(obj.table, obj.values(), "${Identity.ID} = ${obj.id}", arrayOf())

fun SQLiteDatabase.delete(obj: Identity) =
    delete(obj.table, "${Identity.ID} = ${obj.id}", arrayOf())

inline fun <T> SQLiteDatabase.transaction(
    body: SQLiteDatabase.() -> T
): T {
    beginTransaction()
    try {
        val result = body()
        setTransactionSuccessful()
        return result
    } finally {
        endTransaction()
    }
}