package com.example.dbtest.room

import androidx.room.Dao
import androidx.room.Query
import com.example.dbtest.room.BaseDao
import com.example.dbtest.room.Post

@Dao
abstract class PostDao : BaseDao<Post>() {

    @Query("SELECT * FROM posts")
    abstract fun getAll(): List<Post>

    @Query("DELETE FROM posts")
    abstract fun deleteAll()

//    @Query("SELECT * FROM posts")
//    abstract fun getFlowable(): Flowable<List<Post>>

//    @Query("SELECT * FROM posts")
//    abstract fun getFlow(): LiveData<List<Post>>
}