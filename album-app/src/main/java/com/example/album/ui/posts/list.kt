package com.example.album.ui.posts

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import com.example.album.core.CoreListAdapter
import com.example.album.core.ItemViewModel
import com.example.album.data.PostModel
import com.example.album.databinding.ItemPostBinding

class PostsAdapter : CoreListAdapter<ItemViewModel, ViewDataBinding>() {
    override fun inflateView(
        inflater: LayoutInflater,
        parent: ViewGroup,
        viewType: Int
    ): ViewDataBinding {
        return ItemPostBinding.inflate(inflater, parent, false)
    }

    companion object {
        @JvmStatic
        fun create() = PostsAdapter()
    }
}

class PostItemViewModel(
    val post: PostModel,
    val onClick: () -> Unit
) : ItemViewModel {

    override fun isSameItemAs(other: ItemViewModel): Boolean {
        return other is PostItemViewModel && post.id == other.post.id
    }

    override fun isSameContentAs(other: ItemViewModel): Boolean {
        return other is PostItemViewModel && post == other.post
    }
}
