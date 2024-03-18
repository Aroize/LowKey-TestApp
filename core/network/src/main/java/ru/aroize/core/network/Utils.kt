package ru.aroize.core.network

import ru.aroize.core.network.di.NetworkComponentHolder

inline fun<reified T> createService(): T {
    val retrofit = NetworkComponentHolder.get().retrofit
    return retrofit.create(T::class.java)
}