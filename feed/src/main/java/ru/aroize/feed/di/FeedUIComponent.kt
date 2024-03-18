@file:Suppress("UNCHECKED_CAST")

package ru.aroize.feed.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import ru.aroize.core.coroutines.CoroutineDispatchersComponentHolder
import ru.aroize.core.di.ComponentHolder
import ru.aroize.core.di.DIComponent

import ru.aroize.feed.ui.FeedViewModel
import ru.aroize.posts.di.FeedUseCaseComponentHolder

interface FeedUIComponent : DIComponent {
    fun createFeedViewModel(): ViewModelProvider.Factory
}

internal object FeedUIComponentHolder : ComponentHolder<FeedUIComponent>() {
    override fun build(): FeedUIComponent {

        val dispatchers = CoroutineDispatchersComponentHolder.get().dispatchers

        return object : FeedUIComponent {
            override fun createFeedViewModel() = object: ViewModelProvider.Factory {
                override fun <T : ViewModel> create(modelClass: Class<T>): T {
                    return FeedViewModel(
                        dispatchers = dispatchers,
                        getFeedUseCase = FeedUseCaseComponentHolder.get().getFeedUseCase
                    ) as T
                }
            }
        }
    }
}