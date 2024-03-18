package ru.aroize.posts.repository

import android.util.Log
import kotlinx.coroutines.withContext
import ru.aroize.core.coroutines.CoroutineDispatchers
import ru.aroize.db.dao.CachedFeedPost
import ru.aroize.db.dao.CachedFeedPostDao
import ru.aroize.posts.service.FeedService
import ru.aroize.posts.service.model.PhotoResponse
import ru.aroize.posts.service.model.PhotoSource
import java.util.concurrent.ConcurrentHashMap


interface FeedPostMapper {
    fun transform(cache: CachedFeedPost): PhotoResponse
    fun transform(remotes: List<PhotoResponse>): List<CachedFeedPost>
}

internal class FeedPostMapperImpl: FeedPostMapper {
    override fun transform(cache: CachedFeedPost): PhotoResponse {
        return PhotoResponse(
            id = cache.id,
            width = cache.width,
            height = cache.height,
            photographer = cache.photographer,
            color = cache.color,
            alt = cache.alt,
            src = PhotoSource(
                original = cache.original,
                large = cache.large,
                large2x = cache.large2x,
                medium = cache.medium,
                small = cache.small,
                portrait = cache.portrait,
                landscape = cache.landscape,
                tiny = cache.tiny
            )
        )
    }

    override fun transform(remotes: List<PhotoResponse>): List<CachedFeedPost> {
        return remotes.mapIndexed { index, remote ->
            val nextIdx = if (index == remotes.lastIndex) null else remotes[index + 1].id
            CachedFeedPost(
                remote.id,
                nextIdx,
                remote.width,
                remote.height,
                remote.photographer,
                remote.color,
                remote.alt,
                remote.src.original,
                remote.src.large,
                remote.src.large2x,
                remote.src.medium,
                remote.src.small,
                remote.src.portrait,
                remote.src.landscape,
                remote.src.tiny
            )
        }
    }
}

interface FeedRepository {
    suspend fun feed(force: Boolean, page: Int): List<PhotoResponse>
    suspend fun byId(postId: Int): PhotoResponse
}

internal class FeedRepositoryImpl(
    private val dispatchers: CoroutineDispatchers,
    private val cachedFeedPostDao: CachedFeedPostDao,
    private val feedService: FeedService,
    private val mapper: FeedPostMapper = FeedPostMapperImpl(),
): FeedRepository {

    private val cacheEntries = ConcurrentHashMap<Int, Int>()

    override suspend fun feed(force: Boolean, page: Int): List<PhotoResponse> = withContext(dispatchers.io) {
        if (force || cacheEntries[page] == null) {
            Log.i(TAG, "!!! Loading feed from remote !!!")
            loadRemote(page)
        } else {
            Log.i(TAG, "!!! Feed full cache hit !!!")
            val startId = requireNotNull(cacheEntries[page])
            cachedFeedPostDao
                .findLinkedBy(startId, limit = PAGE_SIZE, offset = 0)
                .map(mapper::transform)
        }
    }

    override suspend fun byId(postId: Int): PhotoResponse = withContext(dispatchers.io) {
        cachedFeedPostDao
            .findById(postId)?.let(mapper::transform) ?: feedService.byId(postId)
    }

    private suspend fun loadRemote(page: Int): List<PhotoResponse> {
        val actualFeed = feedService.curated(page = page, count = PAGE_SIZE).photos
        val collisions = cachedFeedPostDao.findCollisions(actualFeed.map { it.id })

        when {
            collisions.isEmpty() -> {
                // no cache hit
                val entries = mapper.transform(actualFeed)
                cachedFeedPostDao.insert(entries)
            }
            collisions.size == actualFeed.size -> {
                // whole block cache hit
            }
            collisions.last().nextId == null -> {
                // partial cache block hit
                val collisionsIds = collisions.map { it.id }.toSet()
                val missing = actualFeed
                    .filter { actual -> actual.id !in collisionsIds }
                    .let(mapper::transform)
                val blockStart = collisions.last()
                    .copy(nextId = missing.first().id)
                val partialCacheBlock = collisions.dropLast(1) + blockStart
                cachedFeedPostDao.update(partialCacheBlock)
                cachedFeedPostDao.insert(missing)
            }
            else -> {
                // maybe linked
                val collisionsIds = collisions.map { it.id }.toSet()
                val missing = actualFeed
                    .filter { actual -> actual.id !in collisionsIds }
                    .let(mapper::transform)
                cachedFeedPostDao.insert(missing)

                if (cacheEntries[page - 1] != null) {
                    val prevBlockStartId = requireNotNull(cacheEntries[page - 1])
                    val prevBlock = cachedFeedPostDao.findAllLinkedBy(prevBlockStartId)
                    if (prevBlock.size <= PAGE_SIZE) {
                        val linkingEntry = prevBlock.last().copy(nextId = missing.first().id)
                        cachedFeedPostDao.update(listOf(linkingEntry))
                    }
                }

                val linkedCacheEntries = cachedFeedPostDao.findAllLinkedBy(missing.first().id)

                linkedCacheEntries
                    .chunked(PAGE_SIZE)
                    .forEachIndexed { index, cachedEntries ->
                        if (cachedEntries.size == PAGE_SIZE) {
                            cacheEntries[page + index] = cachedEntries.first().id
                        }
                    }
            }
        }

        cacheEntries[page] = actualFeed.first().id

        return actualFeed
    }

    private companion object {
        const val PAGE_SIZE = 20

        const val TAG = "FeedCacheRepository"
    }
}
