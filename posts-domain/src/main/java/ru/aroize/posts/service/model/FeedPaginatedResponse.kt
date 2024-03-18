package ru.aroize.posts.service.model

import com.google.gson.annotations.SerializedName

data class FeedPaginatedResponse(
    val page: Int,
    @SerializedName("per_page") val count: Int,
    val photos: List<PhotoResponse>
)
