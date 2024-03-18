package ru.aroize.impl.model

import android.graphics.Color
import ru.aroize.posts.usecase.model.FeedPost
import ru.aroize.posts.usecase.model.Photo

interface UiFeedPostMapper {
    fun transform(domain: FeedPost): FeedDetailsPostUi
}

internal class UiFeedPostMapperImpl : UiFeedPostMapper {
    override fun transform(
        domain: FeedPost
    ): FeedDetailsPostUi {
        return FeedDetailsPostUi(
            id = domain.id,
            photographer = domain.photographer,
            alt = domain.alt,
            thumbnail = Color.parseColor(domain.thumbnailColor),
            photoUrl = domain.photos.find { it.scale == Photo.Scale.ORIGINAL }?.url as String
        )
    }
}