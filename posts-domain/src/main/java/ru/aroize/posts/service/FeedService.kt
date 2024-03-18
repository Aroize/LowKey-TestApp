package ru.aroize.posts.service

import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import ru.aroize.posts.service.model.FeedPaginatedResponse
import ru.aroize.posts.service.model.PhotoResponse

interface FeedService {
    @GET("v1/curated")
    suspend fun curated(
        @Query("page") page: Int,
        @Query("per_page") count: Int
    ): FeedPaginatedResponse

    @GET("v1/photos/{id}")
    suspend fun byId(
        @Path("id") id: Int
    ): PhotoResponse
}