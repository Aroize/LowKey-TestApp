@file:Suppress("UNCHECKED_CAST")

package ru.aroize.impl.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.CreationExtras
import ru.aroize.core.coroutines.CoroutineDispatchersComponentHolder
import ru.aroize.core.di.ComponentHolder
import ru.aroize.core.di.DIComponent
import ru.aroize.impl.PostDetailsViewModel
import ru.aroize.posts.di.FeedUseCaseComponentHolder

internal interface PostDetailsUiComponent : DIComponent {
    fun postDetailsViewModel(postId: Int): ViewModelProvider.Factory
}

internal object PostDetailsUiComponentHolder : ComponentHolder<PostDetailsUiComponent>() {
    override fun build(): PostDetailsUiComponent {
        return object : PostDetailsUiComponent {
            override fun postDetailsViewModel(postId: Int): ViewModelProvider.Factory {
                return object : ViewModelProvider.Factory {
                    override fun <T : ViewModel> create(
                        modelClass: Class<T>,
                        extras: CreationExtras
                    ): T {
                        val dispatchers = CoroutineDispatchersComponentHolder.get().dispatchers
                        val getPostByIdUseCase = FeedUseCaseComponentHolder.get().getPostByIdUseCase
                        return PostDetailsViewModel(
                            postId,
                            dispatchers,
                            getPostByIdUseCase
                        ) as T
                    }
                }
            }
        }
    }
}