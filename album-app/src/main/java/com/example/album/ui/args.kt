package com.example.album.ui

import android.os.Bundle

class UserArgs(val userId: String) {
    fun toBundle() = Bundle().apply {
        putString("userId", userId)
    }
}

fun Bundle.userId() = getString("userId")
