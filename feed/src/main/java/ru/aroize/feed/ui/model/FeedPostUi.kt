package ru.aroize.feed.ui.model

import androidx.annotation.ColorInt
import ru.aroize.core.recycler.DiffListItem

data class FeedPostUi(
    val id: Int,
    val photoUrl: String,
    val photographer: String,
    val alt: String,
    @ColorInt val thumbnail: Int
): DiffListItem {
    override fun areItemsSame(other: DiffListItem): Boolean {
        if (other !is FeedPostUi) return false
        return id == other.id
    }

    override fun areContentsSame(other: DiffListItem): Boolean {
        if (other !is FeedPostUi) return false
        return this == other
    }
}