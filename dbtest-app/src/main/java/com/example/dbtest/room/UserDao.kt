package com.example.dbtest.room

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

    /**
     * This method useful when we need to sync local database with data we received
     * from backend for instance. It updates all records which already exist,
     * inserts not existing records and removes obsolete ones.
     *
     * When we receive a list of [users] to save we will have the situation:
     * there might be existing records (which need to be updated),
     * there might be new records (which need to be inserted),
     * there might be obsolete records (which need to be removed).
     *
     * There is a simpler approach how to achieve it. We might just remove all records
     * and insert new. But this method performs all actions in efficient way. It uses
     * a helper table to sort out data which need to be removed.
     */
    @Transaction
    open fun replaceFull(users: List<User>) {
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
