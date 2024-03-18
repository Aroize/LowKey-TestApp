package ru.aroize.core.utils

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

fun RecyclerView.pagingLoader(
    preload: Int = 7,
    onLoadCalled: () -> Unit
) {
    val manager = layoutManager as? LinearLayoutManager
        ?: throw IllegalStateException("Paging can be provided only for LinearLayoutManager")
    addOnScrollListener(
        object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                val lastItemIdx = manager.findLastVisibleItemPosition()
                if (lastItemIdx + preload >= (adapter?.itemCount ?: Int.MAX_VALUE)) {
                    onLoadCalled()
                }
            }
        }
    )
}