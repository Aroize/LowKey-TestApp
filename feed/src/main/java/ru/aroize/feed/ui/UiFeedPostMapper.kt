package ru.aroize.feed.ui

import android.graphics.Color
import ru.aroize.feed.ui.model.FeedPostUi
import ru.aroize.posts.usecase.model.FeedPost
import ru.aroize.posts.usecase.model.Photo

interface UiFeedPostMapper {
    fun transform(domain: FeedPost): FeedPostUi
}

internal class UiFeedPostMapperImpl : UiFeedPostMapper {
    override fun transform(
        domain: FeedPost
    ): FeedPostUi {
        return FeedPostUi(
            id = domain.id,
            photographer = domain.photographer,
            alt = domain.alt,
            thumbnail = Color.parseColor(domain.thumbnailColor),
            photoUrl = domain.photos.find { it.scale == Photo.Scale.LARGE2X }?.url as String
        )
    }
}