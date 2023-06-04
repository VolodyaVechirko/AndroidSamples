package com.example.album.core

import android.view.View
import androidx.databinding.BindingAdapter
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView

fun RecyclerView.safeScrollToPosition(position: Int) {
    layoutManager?.let {
        if (position in 0..it.itemCount) {
            scrollToPosition(position)
        }
    }
}

@BindingAdapter("items")
fun setRecyclerViewItems(recyclerView: RecyclerView, items: List<ItemViewModel>) {
    recyclerView.adapter?.let { adapter ->
        (adapter as CoreListAdapter<ItemViewModel, ViewDataBinding>)
            .submitList(items)
    }
}

@BindingAdapter(value = ["items", "scrollToPosition"])
fun setRecyclerViewItemsAndScroll(
    recyclerView: RecyclerView,
    items: List<ItemViewModel>,
    position: Int? = null
) {
    recyclerView.adapter?.let { adapter ->
        (adapter as CoreListAdapter<ItemViewModel, ViewDataBinding>)
            .submitList(items) {
                position?.let { recyclerView.safeScrollToPosition(it) }
            }
    }
}

@BindingAdapter("visibility")
fun setVisibility(view: View, visible: Boolean) {
    view.visibility = if (visible) View.VISIBLE else View.GONE
}
