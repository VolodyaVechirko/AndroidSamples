package com.example.album.ui.posts

import androidx.databinding.Bindable
import androidx.lifecycle.viewModelScope
import com.example.album.BR
import com.example.album.core.CoreViewModel
import com.example.album.core.bind
import com.example.album.data.getClient
import kotlinx.coroutines.launch

class PostsViewModel : CoreViewModel() {

    @get:Bindable
    var items: List<PostItemViewModel> by bind(emptyList(), BR.items)
        private set

    @get:Bindable
    var isLoading: Boolean by bind(false, BR.loading, BR.emptyView)
        private set

    @get:Bindable
    val emptyView: Boolean
        get() = !isLoading && items.isEmpty()

    fun init(userId: String?) {
        isLoading = true
        viewModelScope.launch {
            items = with(getClient()) {
                getPosts()
//                if (userId != null) getPosts(userId) else getPosts()
            }.map {
                PostItemViewModel(it, { })
            }
            isLoading = false
        }
    }
}