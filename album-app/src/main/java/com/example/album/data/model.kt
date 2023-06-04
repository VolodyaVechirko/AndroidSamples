package com.example.album.data

data class AddressModel(
    val street: String,
    val suite: String,
    val city: String,
    val zipcode: String
)

data class AlbumModel(
    val id: String,
    val userId: String,
    val title: String
)

data class CommentModel(
    val id: String,
    val postId: String,
    val name: String,
    val email: String,
    val body: String
)

data class PhotoModel(
    val id: String,
    val albumId: String,
    val title: String,
    val thumbnailUrl: String,
    val url: String
)

data class PostModel(
    val id: String,
    val title: String,
    val body: String,
    val userId: String
)

data class UserModel(
    val id: String,
    val name: String,
    val username: String,
    val address: AddressModel,
    val phone: String,
    val website: String
)
