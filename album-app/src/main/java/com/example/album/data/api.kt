package com.example.album.data

import retrofit2.http.*

interface Api {

    // User
    @GET("users")
    suspend fun getUsers(): List<UserModel>

    @GET("users/{userId}")
    suspend fun getUser(
        @Path("userId") userId: String
    ): UserModel


    // Albums
    @GET("albums")
    suspend fun getAlbums(): List<AlbumModel>

    @GET("albums")
    suspend fun getAlbums(
        @Query("userId") userId: String
    ): List<AlbumModel>

    @GET("albums/{albumId}")
    suspend fun getAlbum(
        @Path("albumId") albumId: String
    ): AlbumModel

    @GET("photos")
    suspend fun getPhotos(
        @Query("albumId") albumId: String
    ): List<PhotoModel>

    @GET("photos/{photoId}")
    suspend fun getPhoto(
        @Path("photoId") photoId: String
    ): PhotoModel


    // Posts
    @GET("posts")
    suspend fun getPosts(): List<PostModel>

    @GET("posts")
    suspend fun getPosts(
        @Query("userId") userId: String
    ): List<PostModel>

    @GET("posts/{postId}")
    suspend fun getPost(
        @Path("postId") postId: String
    ): PostModel

    @FormUrlEncoded
    @POST("posts")
    suspend fun createPosts(
        @FieldMap map: Map<String, String>
    ): PostModel

    @FormUrlEncoded
    @PUT("posts/{postId}")
    suspend fun updatePosts(
        @Path("postId") postId: String,
        @FieldMap map: Map<String, String>
    ): PostModel

    @DELETE("posts/{postId}")
    suspend fun deletePost(
        @Path("postId") postId: String
    )

    @GET("comments")
    suspend fun getComments(
        @Query("postId") postId: String
    ): List<CommentModel>

    @GET("comments/{commentId}")
    suspend fun getComment(
        @Path("commentId") commentId: String
    ): CommentModel
}
