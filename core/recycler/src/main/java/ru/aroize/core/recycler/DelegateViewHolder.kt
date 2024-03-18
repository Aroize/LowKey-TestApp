package ru.aroize.core.recycler

import android.view.View
import androidx.annotation.StringRes
import androidx.recyclerview.widget.RecyclerView

abstract class DelegateViewHolder<T : ListItem>(item: View) : RecyclerView.ViewHolder(item) {
    abstract fun bind(item: T)

    protected fun getString(@StringRes res: Int, vararg args: Any): String {
        return itemView.context.getString(res, *args)
    }
}