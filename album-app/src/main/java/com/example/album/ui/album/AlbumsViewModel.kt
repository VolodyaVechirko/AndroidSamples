package com.example.album.ui.album

import androidx.databinding.ObservableBoolean
import androidx.databinding.ObservableField
import androidx.lifecycle.viewModelScope
import com.example.album.core.CoreViewModel
import com.example.album.data.getClient
import kotlinx.coroutines.launch

class AlbumsViewModel : CoreViewModel() {
    val items = ObservableField<List<AlbumItemViewModel>>(emptyList())
    val loading = ObservableBoolean(false)
    val emptyView = object : ObservableField<Boolean>(loading) {
        override fun get(): Boolean {
            return !loading.get() && items.get()!!.isEmpty()
        }
    }

    fun init(userId: String?) {
        loading.set(true)
        viewModelScope.launch {
            val list = getClient().getAlbums().map {
                AlbumItemViewModel(it, onClick = { openAlbumPhoto(it.id) })
            }
            items.set(list)
            loading.set(false)
        }
    }

    private fun openAlbumPhoto(albumId: String) {
//        navController?.navigate(
//            R.id.navigation_posts,
//            UserArgs(userId).toBundle()
//        )
    }
}
