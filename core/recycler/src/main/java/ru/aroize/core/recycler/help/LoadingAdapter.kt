package ru.aroize.core.recycler.help

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import ru.aroize.core.recycler.AdapterDelegate
import ru.aroize.core.recycler.DelegateViewHolder
import ru.aroize.core.recycler.DiffListItem
import ru.aroize.core.recycler.ListItem
import ru.aroize.core.recycler.R

class LoadingAdapter : AdapterDelegate<LoadingAdapter.LoadingItem> {

    object LoadingItem : DiffListItem {
        override fun areItemsSame(other: DiffListItem): Boolean = other is LoadingItem
        override fun areContentsSame(other: DiffListItem): Boolean = other is LoadingItem
    }

    override fun isForViewType(item: ListItem): Boolean = item is LoadingItem

    override fun createViewHolder(
        inflater: LayoutInflater,
        parent: ViewGroup
    ): DelegateViewHolder<LoadingItem> {
        return LoadingViewHolder(
            inflater.inflate(R.layout.list_item_loading, parent, false)
        )
    }

    internal class LoadingViewHolder(view: View) : DelegateViewHolder<LoadingItem>(view) {
        override fun bind(item: LoadingItem) = Unit
    }

    companion object {
        @JvmStatic
        fun<T: DiffListItem> addLoadingItem(data: List<T>): List<DiffListItem> {
            if (data.isEmpty()) return data
            return data + LoadingItem
        }
    }
}