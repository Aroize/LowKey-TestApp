package ru.aroize.core.network

import okhttp3.Interceptor
import okhttp3.Response

class AuthenticatorInterceptor(
    private val apiToken: String
) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request().newBuilder()
            .header(ACCESS_HEADER, apiToken)
            .build()
        return chain.proceed(request)
    }

    companion object {
        private const val ACCESS_HEADER = "Authorization"
    }
}