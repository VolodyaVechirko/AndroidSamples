package com.example.album.ui.users

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import com.example.album.core.CoreListAdapter
import com.example.album.core.ItemViewModel
import com.example.album.databinding.ItemUserBinding

class UsersAdapter : CoreListAdapter<ItemViewModel, ViewDataBinding>() {
    override fun inflateView(
        inflater: LayoutInflater,
        parent: ViewGroup,
        viewType: Int
    ): ViewDataBinding {
        return ItemUserBinding.inflate(inflater, parent, false)
    }

    companion object {
        @JvmStatic
        fun create() = UsersAdapter()
    }
}