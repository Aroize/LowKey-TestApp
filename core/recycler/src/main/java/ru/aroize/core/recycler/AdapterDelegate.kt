package ru.aroize.core.recycler

import android.view.LayoutInflater
import android.view.ViewGroup

interface AdapterDelegate<T : ListItem> {
    fun isForViewType(item: ListItem): Boolean
    fun createViewHolder(inflater: LayoutInflater, parent: ViewGroup): DelegateViewHolder<T>
}