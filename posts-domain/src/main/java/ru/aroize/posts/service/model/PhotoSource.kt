package ru.aroize.posts.service.model

data class PhotoSource(
    val original: String,
    val large: String,
    val large2x: String,
    val medium: String,
    val small: String,
    val portrait: String,
    val landscape: String,
    val tiny: String
)