package ru.aroize.impl

import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.withContext
import ru.aroize.core.arch.Content
import ru.aroize.core.arch.DefaultError
import ru.aroize.core.arch.DefaultLoading
import ru.aroize.core.arch.PexelsViewModel
import ru.aroize.core.arch.UiState
import ru.aroize.core.coroutines.CoroutineDispatchers
import ru.aroize.impl.model.FeedDetailsPostUi
import ru.aroize.impl.model.UiFeedPostMapper
import ru.aroize.impl.model.UiFeedPostMapperImpl
import ru.aroize.posts.usecase.GetPostByIdUseCase


class PostDetailsContent(val post: FeedDetailsPostUi): Content

class PostDetailsViewModel(
    private val postId: Int,
    private val dispatchers: CoroutineDispatchers,
    private val getByIdUseCase: GetPostByIdUseCase,
    private val mapper: UiFeedPostMapper = UiFeedPostMapperImpl()
) : PexelsViewModel() {

    private val _uiState = MutableStateFlow(UiState<PostDetailsContent, DefaultLoading, DefaultError>(
        PostDetailsContent(FeedDetailsPostUi()), DefaultLoading, null
    ))

    val uiState: StateFlow<UiState<PostDetailsContent, DefaultLoading, DefaultError>>
        get() = _uiState

    init {
        loadPostInternal()
    }

    override fun onException(exception: Throwable) {
        _uiState.update { state ->
            state.copy(error = DefaultError(exception.message as String))
        }
    }

    private fun loadPostInternal() {
        viewModelScope.launchSafe(dispatchers.io) {
            val post = getByIdUseCase(postId).let { mapper.transform(it) }
            withContext(dispatchers.main) {
                _uiState.update {
                    UiState(content = PostDetailsContent(post))
                }
            }
        }
    }

}