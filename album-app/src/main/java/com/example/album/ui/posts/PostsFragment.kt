package com.example.album.ui.posts

import android.content.Context
import androidx.fragment.app.viewModels
import com.example.album.R
import com.example.album.core.CoreFragment
import com.example.album.databinding.ActivityPostsBinding
import com.example.album.ui.userId

class PostsFragment : CoreFragment<PostsViewModel, ActivityPostsBinding>() {
    override val layoutId: Int = R.layout.activity_posts
    override val viewModel: PostsViewModel by viewModels()

    override fun onAttach(context: Context) {
        super.onAttach(context)
        viewModel.init(arguments?.userId())
    }
}