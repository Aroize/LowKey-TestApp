package ru.aroize.posts.usecase

import ru.aroize.posts.service.model.PhotoResponse
import ru.aroize.posts.usecase.model.FeedPost
import ru.aroize.posts.usecase.model.Photo

interface DomainFeedPostMapper {
    fun transform(photo: PhotoResponse): FeedPost
}

internal class DomainFeedPostMapperImpl : DomainFeedPostMapper {
    override fun transform(photo: PhotoResponse): FeedPost {
        return FeedPost(
            id = photo.id,
            photographer = photo.photographer,
            alt = photo.alt,
            thumbnailColor = photo.color,
            photos = transformPhotoSource(photo.src)
        )
    }

    private fun transformPhotoSource(source: ru.aroize.posts.service.model.PhotoSource): List<Photo> {

        return Photo.Scale.values().map {
            when (it) {
                Photo.Scale.ORIGINAL -> Photo(it, source.original)
                Photo.Scale.TINY -> Photo(it, source.tiny)
                Photo.Scale.SMALL -> Photo(it, source.small)
                Photo.Scale.MEDIUM -> Photo(it, source.medium)
                Photo.Scale.LARGE -> Photo(it, source.large)
                Photo.Scale.LARGE2X -> Photo(it, source.large2x)
                Photo.Scale.PORTRAIT -> Photo(it, source.portrait)
                Photo.Scale.LANDSCAPE -> Photo(it, source.landscape)
            }
        }
    }
}