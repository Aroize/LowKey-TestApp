package ru.aroize.posts.usecase.model

data class Photo(
    val scale: Scale,
    val url: String
) {
    enum class Scale {
        ORIGINAL,
        TINY,
        SMALL,
        MEDIUM,
        LARGE,
        LARGE2X,
        PORTRAIT,
        LANDSCAPE
    }
}