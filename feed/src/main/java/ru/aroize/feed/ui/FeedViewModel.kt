package ru.aroize.feed.ui

import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.withContext
import ru.aroize.core.arch.DefaultError
import ru.aroize.core.arch.DefaultLoading
import ru.aroize.core.arch.PexelsViewModel
import ru.aroize.core.arch.UiState
import ru.aroize.core.coroutines.CoroutineDispatchers
import ru.aroize.feed.ui.model.FeedUiContent
import ru.aroize.posts.usecase.GetFeedUseCase
import java.util.concurrent.atomic.AtomicInteger


class FeedViewModel(
    private val dispatchers: CoroutineDispatchers,
    private val getFeedUseCase: GetFeedUseCase,
    private val uiMapper: UiFeedPostMapper = UiFeedPostMapperImpl()
) : PexelsViewModel() {

    private var job: Job? = null

    private var actualPage = AtomicInteger(0)

    init { loadInternal() }

    private val _uiState = MutableStateFlow(
        UiState<FeedUiContent, DefaultLoading, DefaultError>(FeedUiContent())
    )

    val uiState: StateFlow<UiState<FeedUiContent, DefaultLoading, DefaultError>>
        get() = _uiState

    fun refresh() {
        actualPage.getAndSet(0)
        _uiState.update { UiState(content = FeedUiContent()) }
        loadInternal(withDrop = true)
    }

    fun loadNextPage() = loadInternal()

    private fun loadInternal(withDrop: Boolean = false) {
        if (job?.isActive == true) return
        job = viewModelScope.launchSafe(dispatchers.io) {
            val feed = getFeedUseCase.feed(force = withDrop, page = actualPage.get() + 1)
                .map(uiMapper::transform)
            withContext(dispatchers.main) {
                _uiState.update { state ->
                    val photos = if (withDrop) feed else state.content.photos + feed
                    UiState(content = FeedUiContent(photos = photos))
                }
                actualPage.incrementAndGet()
            }
        }
    }
}