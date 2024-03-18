package ru.aroize.posts.di

import ru.aroize.core.coroutines.CoroutineDispatchersComponentHolder
import ru.aroize.core.di.ComponentHolder
import ru.aroize.core.di.DIComponent
import ru.aroize.core.network.createService
import ru.aroize.db.di.DatabaseComponentHolder
import ru.aroize.posts.repository.FeedRepositoryImpl
import ru.aroize.posts.service.FeedService
import ru.aroize.posts.usecase.GetFeedUseCase
import ru.aroize.posts.usecase.GetFeedUseCaseImpl
import ru.aroize.posts.usecase.GetPostByIdUseCase
import ru.aroize.posts.usecase.GetPostByIdUseCaseImpl

interface FeedUseCaseComponent : DIComponent {
    val getFeedUseCase: GetFeedUseCase
    val getPostByIdUseCase: GetPostByIdUseCase
}

object FeedUseCaseComponentHolder : ComponentHolder<FeedUseCaseComponent>() {
    override fun build(): FeedUseCaseComponent {
        val dispatchers = CoroutineDispatchersComponentHolder.get().dispatchers
        val cachedFeedPostDao = DatabaseComponentHolder.get().cachedFeedPostDao()
        val feedService = createService<FeedService>()
        val repository = FeedRepositoryImpl(dispatchers, cachedFeedPostDao, feedService)
        return object: FeedUseCaseComponent {
            override val getFeedUseCase: GetFeedUseCase
                get() = GetFeedUseCaseImpl(repository)
            override val getPostByIdUseCase: GetPostByIdUseCase
                get() = GetPostByIdUseCaseImpl(repository)
        }
    }
}