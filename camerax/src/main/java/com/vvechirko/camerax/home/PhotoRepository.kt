package com.vvechirko.camerax.home

import android.content.Context

fun getPhotos(context: Context): List<Photo> =
    (context.filesDir.listFiles()?.toList() ?: emptyList())
        .filter { it.name.contains(".jpg") }
        .map { Photo(it.name, it.absolutePath) }
        .reversed()

fun getPhoto(context: Context, id: String): Photo =
    (context.filesDir.listFiles()?.toList() ?: emptyList())
        .first { it.name.equals(id) }
        .let { Photo(it.name, it.absolutePath) }


data class Photo(
    // filename
    val id: String,
    // Local absolutePath
    val url: String
)