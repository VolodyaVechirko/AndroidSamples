package com.example.dbtest

import android.app.Activity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import com.example.dbtest.room.Post
import com.example.dbtest.room.RoomDB
import com.example.dbtest.room.User
import java.util.concurrent.Executors

/**
 * Activity to test database storage when we need to replace records.
 * Updates all records which already exist, inserts not existing records
 * and removes obsolete ones.
 */
class SqlTestActivity : Activity() {
    private val handler = Handler(Looper.getMainLooper())
    private val io = Executors.newFixedThreadPool(2)

    private lateinit var db: RoomDB

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        db = RoomDB.create(this.applicationContext)

//        val users = mutableListOf<User>()
//        repeat(10) { i ->
//            val id = i + 1
//            users.add(User(id, "User $id"))
//        }
//
//        val posts = mutableListOf<Post>()
//        repeat(10) { j ->
//            repeat(4) { i ->
//                val id = 10 * j + i + 1
//                posts.add(Post(id, "Post text $id", j + 1))
//            }
//        }

//        post(0) {
//            printDb()
//            db.userDao.insertOrUpdate(users)
//            db.postDao.insert(posts)
//        }

//        post(1) {
//            printDb()
//        }

//        post(2) {
//            db.userDao.delete(4)
//            db.userDao.delete(9)
//
//            val list = listOf(
//                User(2, "User 302"),
//                User(6, "User 306"),
//                User(5, "User 307"),
//                User(9, "User 309")
//            )
//            db.userDao.replaceFull(list)
//        }
//
//        post(3) {
//            printDb()
//        }
    }

    private fun post(sec: Int, r: Runnable) {
        handler.postDelayed({
            io.execute { r.run() }
        }, sec * 1000L)
    }

    private fun printDb() {
        println("Users ----------------------")
        db.userDao.getAll().forEachIndexed { i, it ->
            println("Users-$i $it")
        }

        println("Posts ----------------------")
        db.postDao.getAll().forEachIndexed { i, it ->
            println("Posts-$i $it")
        }
    }
}