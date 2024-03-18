package ru.aroize.posts.usecase

import ru.aroize.posts.repository.FeedRepository
import ru.aroize.posts.usecase.model.FeedPost

interface GetFeedUseCase {
    suspend fun feed(force: Boolean, page: Int): List<FeedPost>
}

internal class GetFeedUseCaseImpl(
    private val feedRepository: FeedRepository,
    private val mapper: DomainFeedPostMapper = DomainFeedPostMapperImpl()
): GetFeedUseCase {
    override suspend fun feed(force: Boolean, page: Int): List<FeedPost> {
        val result = feedRepository.feed(force, page)
        return result.map { mapper.transform(it) }
    }
}

