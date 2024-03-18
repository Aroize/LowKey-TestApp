package ru.aroize.core.network

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


object NetworkFactory {

    private val gson: Gson by lazy { GsonBuilder().create() }

    fun create(
        httpClientProvider: HttpClientProvider,
        hostManager: HostManager
    ): Retrofit {
        return Retrofit.Builder()
            .baseUrl(hostManager.provide())
            .addConverterFactory(GsonConverterFactory.create(gson))
            .client(httpClientProvider.provide())
            .build()
    }
}