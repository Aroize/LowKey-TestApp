package ru.aroize.posts.service.model

import com.google.gson.annotations.SerializedName

data class PhotoResponse(
    val id: Int,
    val width: Int,
    val height: Int,
    val photographer: String,
    @SerializedName("avg_color") val color: String,
    val src: PhotoSource,
    val alt: String
)