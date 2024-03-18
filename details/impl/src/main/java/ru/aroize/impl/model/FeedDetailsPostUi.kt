package ru.aroize.impl.model

import androidx.annotation.ColorInt

data class FeedDetailsPostUi(
    val id: Int = 0,
    val photoUrl: String = "",
    val photographer: String = "",
    val alt: String = "",
    @ColorInt val thumbnail: Int = 0
)