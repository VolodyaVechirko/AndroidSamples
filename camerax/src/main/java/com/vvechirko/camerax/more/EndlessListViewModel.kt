package com.vvechirko.camerax.more

import android.util.Log
import androidx.compose.runtime.Immutable
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class EndlessListViewModel : ViewModel() {
    private val _state = MutableStateFlow(ScreenState())
    val state = _state.asStateFlow()

    private val _isRefresh = MutableStateFlow(false)
    val isRefresh = _isRefresh.asStateFlow()

    init {
        if (_state.value.items.isEmpty()) {
            getItems(page = 0)
        }

        _state.onEach {
            Log.d("App", "ScreenState: items.count ${it.items.size}")
        }.launchIn(viewModelScope)
    }

    private fun getItems(page: Int) {
        Log.d("App", "getItems page $page")
        _state.update { it.copy(isLoading = true) }
        viewModelScope.launch {
            val newItems = generateList(page)
            _state.update {
                it.copy(
                    isLoading = false,
                    items = if (page == 0) newItems else it.items + newItems,
                    currentPage = page,
                    isLastPage = newItems.isEmpty()
                )
            }

            // error
//            _state.update {
//                it.copy(
//                    error = "Some error occurred",
//                    isLoading = false,
//                )
//            }
        }
    }

    fun refresh() {
        viewModelScope.launch {
            _isRefresh.update { true }
            getItems(page = 0)
            _isRefresh.update { false }
        }
    }

    fun getMoreItems() {
        if (state.value.isLoading) {
            return
        }
        val nextPage = state.value.currentPage + 1
        Log.d("App", "getMoreItems $nextPage")
        if (!state.value.isLastPage && state.value.items.isNotEmpty()) {
            getItems(page = nextPage)
        }
    }
}

@Immutable
data class ScreenState(
    val items: List<String> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null,
    val currentPage: Int = 0,
    val isLastPage: Boolean = false
)


const val PER_PAGE = 16
const val LAST_PAGE = 9
private suspend fun generateList(page: Int): List<String> = withContext(Dispatchers.IO) {
    delay(500L)
    if (page == LAST_PAGE) emptyList()
    else mutableListOf<String>().apply {
        repeat(PER_PAGE) {
            add("Item number ${page * PER_PAGE + it}")
        }
    }
}