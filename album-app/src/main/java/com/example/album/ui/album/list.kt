package com.example.album.ui.album

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import com.example.album.core.CoreListAdapter
import com.example.album.core.ItemViewModel
import com.example.album.data.AlbumModel
import com.example.album.databinding.ItemAlbumBinding

class AlbumAdapter : CoreListAdapter<ItemViewModel, ViewDataBinding>() {
    override fun inflateView(
        inflater: LayoutInflater,
        parent: ViewGroup,
        viewType: Int
    ): ViewDataBinding {
        return ItemAlbumBinding.inflate(inflater, parent, false)
    }

    companion object {
        @JvmStatic
        fun create() = AlbumAdapter()
    }
}

class AlbumItemViewModel(
    val album: AlbumModel,
    val onClick: () -> Unit
) : ItemViewModel {

    override fun isSameItemAs(other: ItemViewModel): Boolean {
        return other is AlbumItemViewModel && album.id == other.album.id
    }

    override fun isSameContentAs(other: ItemViewModel): Boolean {
        return other is AlbumItemViewModel && album == other.album
    }
}
