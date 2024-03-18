package ru.aroize.feed.ui.model

import ru.aroize.core.arch.Content

data class FeedUiContent(
    val photos: List<FeedPostUi> = emptyList()
): Content