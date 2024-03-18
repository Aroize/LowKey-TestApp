package ru.aroize.core.network

import okhttp3.Interceptor
import okhttp3.OkHttpClient
import java.time.Duration

fun interface HttpClientProvider {
    fun provide(): OkHttpClient
}

class HttpClientProviderImpl(
    private val interceptors: List<Interceptor>
) : HttpClientProvider {
    override fun provide(): OkHttpClient {
        return OkHttpClient.Builder()
            .apply { interceptors.forEach { addInterceptor(it) } }
            .connectTimeout(Duration.ofMinutes(1))
            .readTimeout(Duration.ofMinutes(1))
            .writeTimeout(Duration.ofMinutes(1))
            .build()
    }
}