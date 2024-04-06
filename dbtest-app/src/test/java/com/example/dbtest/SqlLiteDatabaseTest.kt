package com.example.dbtest

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import com.example.dbtest.sql.DatabaseHelper
import com.example.dbtest.sql.Post
import com.example.dbtest.sql.User
import org.junit.After
import org.junit.Before
import org.junit.Ignore
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@Config(manifest = Config.NONE)
@RunWith(RobolectricTestRunner::class)
class SqlLiteDatabaseTest {

    private lateinit var db: DatabaseHelper

    @Before
    fun setUp() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = DatabaseHelper(context, true)
    }

    @Ignore("Test failure")
    @Test
    fun complexTest() {
        assert(db.userDao.getAll().isEmpty())

        // Insert 10 users
        val users = mutableListOf<User>()
        repeat(10) { i ->
            val id = i + 1
            users.add(User(id, "User $id"))
        }
        db.userDao.insert(users)

        // Insert 4 posts for each user
        val posts = mutableListOf<Post>()
        repeat(10) { j ->
            repeat(4) { i ->
                val id = 10 * j + i + 1
                posts.add(Post(id, "Post text $id", j + 1))
            }
        }
        // TODO: Error android.database.sqlite.SQLiteConstraintException: FOREIGN KEY constraint failed (code 787 SQLITE_CONSTRAINT_FOREIGNKEY)
        //   at org.robolectric.nativeruntime.SQLiteConnectionNatives.nativeExecute(Native Method)
        db.postDao.insert(posts)

        println("users size = ${db.userDao.getAll().size}")
        assert(db.userDao.getAll().size == 10)
        println("posts size = ${db.postDao.getAll().size}")
        assert(db.postDao.getAll().size == 40)

        // Verify cascade removal

        // Delete user with id [4]
        db.userDao.delete(id = 4)
        assert(!userExist(id = 4))
        assert(!userPostsExist(id = 4))

        // Delete user with id [9]
        db.userDao.delete(id = 9)
        assert(!userExist(id = 9))
        assert(!userPostsExist(id = 9))

        // Verify [replaceFull] effect

        // Insert fresh list of users
        val list = listOf(
            User(2, "User 302"), // existing record
            User(6, "User 306"), // existing record
            User(9, "User 309"), // removed record
            User(11, "User 311"), // new record
            User(13, "User 313")  // new record
        )

        db.userDao.replaceFull(list)

        // Verify total user count is 5
        assert(db.userDao.getAll().size == 5)
        // Verify total posts count is 8
        assert(db.postDao.getAll().size == 8)

        // Verify user2 present with 4 posts
        assert(userExist(id = 2))
        assert(userPostsCount(id = 2) == 4)

        // Verify user6 present with 4 posts
        assert(userExist(id = 6))
        assert(userPostsCount(id = 6) == 4)

        // Verify user9 present no posts
        assert(userExist(id = 9))
        assert(!userPostsExist(id = 9))

        // Verify user11 present no posts
        assert(userExist(id = 11))
        assert(!userPostsExist(id = 11))

        // Verify user13 present no posts
        assert(userExist(id = 13))
        assert(!userPostsExist(id = 13))
    }

    @After
    fun tearDown() {
        db.close()
    }

    private fun userExist(id: Int): Boolean =
        db.userDao.getAll().any { it.id == id }

    private fun userPostsExist(id: Int): Boolean =
        db.postDao.getAll().any { it.userId == id }

    private fun userPostsCount(id: Int): Int =
        db.postDao.getAll().count { it.userId == id }

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