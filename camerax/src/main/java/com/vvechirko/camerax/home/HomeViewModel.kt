package com.vvechirko.camerax.home

import android.content.Context
import androidx.lifecycle.ViewModel

class HomeViewModel : ViewModel() {

    fun loadPhotos(context: Context): List<String> =
        (context.filesDir.listFiles()?.toList() ?: emptyList())
            .filter { it.name.contains(".jpg") }
            .map { it.absolutePath }
            .reversed()
}