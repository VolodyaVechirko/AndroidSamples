package com.example.webtest.db.room

import androidx.room.Dao
import androidx.room.Query
import androidx.room.RawQuery
import androidx.room.Transaction
import androidx.sqlite.db.SimpleSQLiteQuery
import androidx.sqlite.db.SupportSQLiteQuery

@Dao
abstract class UserDao : BaseDao<User>() {

    @Query("SELECT * FROM users")
    abstract fun getAll(): List<User>

    @Query("DELETE FROM users WHERE id = :id")
    abstract fun delete(id: Int)

    @Query("DELETE FROM users")
    abstract fun deleteAll()

    @Transaction
    open fun replaceAll(users: List<User>) {
        rawQuery("DROP TABLE IF EXISTS `#temp`")
        rawQuery("CREATE TABLE `#temp` ( id  INTEGER PRIMARY KEY )")

        users.map {
            rawQuery("INSERT OR IGNORE INTO `#temp` VALUES ( ${it.id} ) ")
        }

        rawQuery("DELETE FROM users WHERE id NOT IN (SELECT t.id FROM `#temp` t)")

        insertOrUpdate(users)
    }

    @RawQuery
    abstract fun _raw(query: SupportSQLiteQuery): Int

    private fun rawQuery(query: String) = _raw(SimpleSQLiteQuery(query))
}
