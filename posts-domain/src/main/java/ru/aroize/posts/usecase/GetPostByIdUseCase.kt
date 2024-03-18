package ru.aroize.posts.usecase

import ru.aroize.posts.repository.FeedRepository
import ru.aroize.posts.usecase.model.FeedPost

interface GetPostByIdUseCase {
    suspend operator fun invoke(id: Int): FeedPost
}

internal class GetPostByIdUseCaseImpl(
    private val feedRepository: FeedRepository,
    private val mapper: DomainFeedPostMapper = DomainFeedPostMapperImpl()
): GetPostByIdUseCase {
    override suspend fun invoke(id: Int): FeedPost {
        val result = feedRepository.byId(id)
        return mapper.transform(result)
    }
}