package com.vvechirko.camerax.friends

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class SearchViewModel(
    private val repo: FriendsRepository = FriendsRepository()
) : ViewModel() {
    var query: String by mutableStateOf("")
    var searchResults: List<String> by mutableStateOf(emptyList())

    private val queryFlow = MutableStateFlow("")

    init {
        queryFlow
            .debounce(200L)
            .onEach { value ->
                searchResults = repo.search(value)
                Log.d("App:", "onEach: $value")
            }
            .launchIn(viewModelScope)
    }

    fun onTextChange(value: String) {
        query = value
        queryFlow.value = value
    }

//    fun onTextChanged(value: String) {
//        query = value
//        viewModelScope.launch {
//            searchResults = repo.search(value)
//            Log.d("App:", "onInput: $value")
//        }
//    }
}