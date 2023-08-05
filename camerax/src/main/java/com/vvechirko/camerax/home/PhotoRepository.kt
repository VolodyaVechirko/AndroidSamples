package com.vvechirko.camerax.home

import android.content.Context

fun loadPhotos(context: Context): List<String> =
    (context.filesDir.listFiles()?.toList() ?: emptyList())
        .filter { it.name.contains(".jpg") }
        .map { it.absolutePath }
        .reversed()