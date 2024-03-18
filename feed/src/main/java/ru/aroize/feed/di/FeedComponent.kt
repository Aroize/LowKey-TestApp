package ru.aroize.feed.di

import ru.aroize.core.di.ComponentHolder
import ru.aroize.core.di.DIComponent
import ru.aroize.feed.ui.FeedFragmentFactory
import ru.aroize.feed.ui.FeedFragmentFactoryImpl

fun interface FeedComponent : DIComponent {
    fun feedFragmentFactory(): FeedFragmentFactory
}

object FeedComponentHolder : ComponentHolder<FeedComponent>() {
    override fun build(): FeedComponent {
        val feedFragmentFactory = FeedFragmentFactoryImpl()
        return FeedComponent { feedFragmentFactory }
    }
}