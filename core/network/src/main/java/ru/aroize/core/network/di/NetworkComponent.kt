package ru.aroize.core.network.di

import retrofit2.Retrofit
import ru.aroize.core.di.DIComponent
import ru.aroize.core.di.LazyComponentHolder

interface NetworkComponent: DIComponent {
    val retrofit: Retrofit
}

object NetworkComponentHolder : LazyComponentHolder<NetworkComponent>()