package com.example.album.ui.album

import android.content.Context
import androidx.fragment.app.viewModels
import com.example.album.R
import com.example.album.core.CoreFragment
import com.example.album.databinding.ActivityAlbumsBinding
import com.example.album.ui.userId

class AlbumsFragment : CoreFragment<AlbumsViewModel, ActivityAlbumsBinding>() {
    override val layoutId: Int = R.layout.activity_albums
    override val viewModel: AlbumsViewModel by viewModels()

    override fun onAttach(context: Context) {
        super.onAttach(context)
        viewModel.init(arguments?.userId())
    }
}