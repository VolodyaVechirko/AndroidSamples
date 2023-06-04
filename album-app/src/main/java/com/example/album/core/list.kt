package com.example.album.core

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.album.BR

/**
 * The base ViewModel class for an item to be shown in a list.
 */
interface ItemViewModel {
    /**
     * @see [DiffUtil.ItemCallback.areItemsTheSame]
     * @return `true` if the two items represent the same object or `false` if they are different.
     */
    fun isSameItemAs(other: ItemViewModel): Boolean

    /**
     * @see [DiffUtil.ItemCallback.areContentsTheSame]
     * @return `true` if the contents of the items are the same or `false` if they are different.
     */
    fun isSameContentAs(other: ItemViewModel): Boolean
}


/**
 * The base ViewModel class for a uniquely identifiable item to be shown in a list.
 */
interface StableIdItemViewModel : ItemViewModel {
    /** The id of the item, unique across the data-set. */
    val id: Long
    override fun isSameItemAs(other: ItemViewModel) =
        other is StableIdItemViewModel && this.id == other.id
}


/**
 * A [RecyclerView.ViewHolder] implementation that handles binding of an [ItemViewModel]
 * with a [ViewDataBinding].
 */
open class CoreItemViewHolder<in VM : ItemViewModel, out DB : ViewDataBinding>(
    protected val binding: DB
) : RecyclerView.ViewHolder(binding.root) {
    open val viewModelBindingId: Int = BR.viewModel
    open fun bind(item: VM) {
        binding.setVariable(viewModelBindingId, item)
        binding.executePendingBindings()
    }
}

/**
 * A [DiffUtil.ItemCallback] implementation that takes care of the comparison of [ItemViewModel]
 * items, forwarding the calls to [ItemViewModel.isSameItemAs] and [ItemViewModel.isSameContentAs].
 */
open class CoreItemCallback<IVM : ItemViewModel> : DiffUtil.ItemCallback<IVM>() {
    override fun areItemsTheSame(oldItem: IVM, newItem: IVM) = oldItem.isSameItemAs(newItem)
    override fun areContentsTheSame(oldItem: IVM, newItem: IVM) = oldItem.isSameContentAs(newItem)
}


/**
 * [RecyclerView.Adapter] base class for presenting list data in a RecyclerView using the MVVM
 * pattern and handles creation of ViewHolders, binding of items to ViewHolders and computing of
 * diffs between lists on a background thread. To the minimum, consumers only need to overload
 * [itemViewModelBindingVarId] and [inflateView] to provide the right behaviour.
 *
 * The adapter automatically creates view holders as needed, using the result of [inflateView] as
 * the view, and binds each item to the holder using [itemViewModelBindingVarId] as the variable id.
 *
 * The adapter needs to be provided with data via the [submitList] method. For a correct behaviour,
 * **the provided lists should be immutable**. Once a new list is submitted, a comparison with the
 * existing list is automatically performed on a background thread and the diff results are
 * dispatched to the attached RecyclerView.
 *
 * The items handled by this adapter need to be of [ItemViewModel] type. Furthermore, if the items
 * have stable ids, the [StableIdItemViewModel] type should be used and [setHasStableIds] should be
 * called with `true` during the initialization of extending classes. The Adapter automatically
 * exposes the right id, so calls to [getItemId] return the correct value. If a custom diff callback
 * is required (e.g. to return a payload in certain cases), the Item Callback can be provided as an
 * argument (defaults to [CoreItemCallback]).
 *
 * By default, the view type for all positions is `0`, but extending classes can override this by
 * overloading the [getItemViewType] method and handling the type in `inflateView` to display each
 * type differently.
 *
 * @param IVM the type of the bound [ItemViewModel]
 * @param B  the type of the [ViewDataBinding] corresponding to items. If multiple types of bindings
 * are necessary, the type should be kept as [ViewDataBinding].
 */
abstract class CoreListAdapter<IVM : ItemViewModel, out B : ViewDataBinding>(
    diffItemCallback: DiffUtil.ItemCallback<IVM> = CoreItemCallback()
) : ListAdapter<IVM, RecyclerView.ViewHolder>(diffItemCallback) {
    override fun getItemId(position: Int): Long =
        if (hasStableIds()) (getItem(position) as? StableIdItemViewModel)?.id ?: position.toLong()
        else RecyclerView.NO_ID

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder =
        CoreItemViewHolder<IVM, B>(
            inflateView(LayoutInflater.from(parent.context), parent, viewType)
        )

    @Suppress("UNCHECKED_CAST")
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) =
        (holder as CoreItemViewHolder<IVM, B>).bind(getItem(position))

    /**
     * Called when a new view needs to be inflated to be attached to the View Holder for the
     * provided position.
     *
     * If different items should have different layouts, the [getItemViewType] method should be
     * overriden and the `viewType` parameter should be used to create the proper layout.
     */
    abstract fun inflateView(inflater: LayoutInflater, parent: ViewGroup, viewType: Int): B
}
