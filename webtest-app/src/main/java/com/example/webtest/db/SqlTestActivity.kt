package com.example.webtest.db

import android.app.Activity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.recyclerview.widget.RecyclerView
import com.example.webtest.db.room.User
import java.util.concurrent.Executors

class SqlTestActivity : Activity() {

    val handler = Handler(Looper.getMainLooper())
    val IO = Executors.newFixedThreadPool(2)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val recyclerView = RecyclerView(this)
        setContentView(recyclerView)

        val db = Room(applicationContext)

//        val users = mutableListOf<User>()
//        repeat(10) { i ->
//            val id = i + 1
//            users.add(User(id, "User $id"))
//        }

//        val posts = mutableListOf<Post>()
//        repeat(10) { j ->
//            repeat(4) { i ->
//                val id = 10 * j + i + 1
//                posts.add(Post(id, "Post text $id", j + 1))
//            }
//        }

//        post(0) {
//            db.userDao.insertOrUpdate(users)
//            db.postDao.insertOrUpdate(posts)
//        }

        post(1) {
            db.print()
        }

        post(2) {
            val list = listOf(
                User(2, "User 302"),
                User(6, "User 306"),
                User(5, "User 307"),
                User(9, "User 309")
            )

//            db.userDao.delete(4)
//            db.userDao.delete(9)

//            db.userDao.replaceAll(list)

        }

        post(3) {
            db.print()
        }
    }

    fun post(sec: Int, r: Runnable) {
        handler.postDelayed({
            IO.execute { r.run() }
        }, sec * 1000L)
    }
}
