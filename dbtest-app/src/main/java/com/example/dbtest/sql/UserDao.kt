package com.example.dbtest.sql

class UserDao(val helper: DatabaseHelper) {

    fun insertOrUpdate(user: User) {
        helper.writableDatabase.use { db ->
            val id = db.insertOrIgnore(user)
            if (id == -1L) {
                db.update(user)
            }
        }
    }

    fun insertOrUpdate(users: List<User>) {
        helper.writableDatabase.use { db ->
            db.transaction {
                users.forEach { user ->
                    val id = insertOrIgnore(user)
                    if (id == -1L) {
                        update(user)
                    }
                }
            }
        }
    }

    fun insert(users: List<User>) {
        helper.writableDatabase.use { db ->
            db.transaction {
                users.forEach { user ->
                    insertOrIgnore(user)
                }
            }
        }
    }

    fun update(user: User) {
        helper.writableDatabase.use { db ->
            db.update(user)
        }
    }

    fun getAll(): List<User> {
        val list = mutableListOf<User>()
        helper.readableDatabase.use { db ->
            db.query(User.TABLE)?.use {
                if (it.moveToFirst()) {
                    do list.add(User.parse(it)) while (it.moveToNext())
                }
            }
        }
        return list
    }

    fun replaceFull(users: List<User>) {
        helper.writableDatabase.use { db ->
            db.transaction {
                removeOrphan(users)
                insertOrUpdate(users)
            }
        }
    }

    fun removeOrphan(users: List<User>) {
        val temp = "tempTable"
        helper.writableDatabase.use { db ->
            db.execSQL("DROP TABLE IF EXISTS $temp")
            db.execSQL("CREATE TABLE $temp ( `id`  INTEGER )")

            users.forEach {
                db.execSQL("INSERT OR IGNORE INTO $temp VALUES ( ${it.id} ) ")
            }

            db.execSQL(
                "DELETE FROM ${User.TABLE} WHERE ${User.COLUMN_ID} " +
                        "NOT IN (SELECT t.id FROM $temp t)"
            )
        }
    }

    fun delete(user: User) {
        helper.writableDatabase.use { db ->
            db.delete(user)
        }
    }

    fun delete(id: Int) {
        delete(User(id, "ignored"))
    }

    fun deleteAll() {
        helper.writableDatabase.use { db ->
            db.delete(User.TABLE, null, null)
        }
    }

    companion object {
        private const val TAG = "UserDao"
    }
}
