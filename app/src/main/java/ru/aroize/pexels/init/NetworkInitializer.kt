package ru.aroize.pexels.init

import retrofit2.Retrofit
import ru.aroize.core.network.AuthenticatorInterceptor
import ru.aroize.core.network.HostManagerImpl
import ru.aroize.core.network.HttpClientProviderImpl
import ru.aroize.core.network.NetworkFactory
import ru.aroize.core.network.di.NetworkComponent
import ru.aroize.core.network.di.NetworkComponentHolder
import ru.aroize.pexels.BuildConfig

object NetworkInitializer {
    fun init() {
        NetworkComponentHolder.set {
            val httpClientProvider = HttpClientProviderImpl(
                listOf(AuthenticatorInterceptor(BuildConfig.PEXELS_API_KEY),)
            )
            val retrofit = NetworkFactory.create(
                httpClientProvider = httpClientProvider,
                hostManager = HostManagerImpl()
            )
            object : NetworkComponent {
                override val retrofit: Retrofit
                    get() = retrofit
            }
        }
    }
}