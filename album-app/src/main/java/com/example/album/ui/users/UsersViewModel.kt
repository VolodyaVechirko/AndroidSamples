package com.example.album.ui.users

import androidx.databinding.ObservableBoolean
import androidx.databinding.ObservableField
import androidx.lifecycle.viewModelScope
import com.example.album.R
import com.example.album.core.CoreViewModel
import com.example.album.data.getClient
import com.example.album.ui.UserArgs
import kotlinx.coroutines.launch

class UsersViewModel : CoreViewModel() {

    val items = ObservableField<List<UserItemViewModel>>(emptyList())
    val loading = ObservableBoolean(false)
    val emptyView = object : ObservableField<Boolean>(loading) {
        override fun get(): Boolean {
            return !loading.get() && items.get()!!.isEmpty()
        }
    }

    fun init() {
        loading.set(true)
        viewModelScope.launch {
            val list = getClient().getUsers().map {
                UserItemViewModel(it, onClick = { openUserPosts(it.id) })
            }
            items.set(list)
            loading.set(false)
        }
    }

    private fun openUserPosts(userId: String) {
        navController?.navigate(
            R.id.navigation_posts,
            UserArgs(userId).toBundle()
        )
    }
}
