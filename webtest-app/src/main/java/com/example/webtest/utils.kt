package com.example.webviewtest

import android.content.ContentValues
import android.database.sqlite.SQLiteDatabase
import android.graphics.Color
import android.util.Log
import android.view.View
import android.view.Window

fun Window.fillStatusBar() {
    this.decorView.systemUiVisibility =
        View.SYSTEM_UI_FLAG_LAYOUT_STABLE or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
    this.statusBarColor = Color.TRANSPARENT
}

fun Any.log(t: Any?) {
    Log.d(this.javaClass.simpleName, t.toString())
}


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
