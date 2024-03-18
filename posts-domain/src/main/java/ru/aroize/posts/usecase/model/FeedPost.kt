package ru.aroize.posts.usecase.model

data class FeedPost(
    val id: Int,
    val photographer: String,
    val photos: List<Photo>,
    val alt: String,
    val thumbnailColor: String
)
