package ru.aroize.feed.ui

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import ru.aroize.api.di.PostDetailsDIComponentHolder
import ru.aroize.core.arch.PexelsFragment
import ru.aroize.core.arch.UiState
import ru.aroize.core.recycler.CompositeAdapter
import ru.aroize.core.recycler.castAdapterDelegate
import ru.aroize.core.recycler.help.LoadingAdapter
import ru.aroize.core.utils.pagingLoader
import ru.aroize.feed.R
import ru.aroize.feed.di.FeedUIComponentHolder

class FeedFragment: PexelsFragment(R.layout.fragment_feed) {

    private val viewModel: FeedViewModel by viewModels {
        FeedUIComponentHolder.get().createFeedViewModel()
    }

    private val adapter by lazy {
        CompositeAdapter(
            FeedPostAdapterDelegate { id -> navigateToPost(id) }.castAdapterDelegate(),
            LoadingAdapter().castAdapterDelegate()
        )
    }

    private val detailsFactory by lazy { PostDetailsDIComponentHolder.get().fragmentFactory }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        val swipe = view.findViewById<SwipeRefreshLayout>(R.id.root).also { swipe ->
            swipe.setOnRefreshListener {
                viewModel.refresh()
            }
        }

        view.findViewById<RecyclerView>(R.id.feed).also { recycler ->
            recycler.adapter = adapter
            recycler.pagingLoader { viewModel.loadNextPage() }
        }

        lifecycleScope.launch {
            viewModel.uiState.collectLatest { state ->
                when (state.state) {
                    UiState.State.READY -> {
                        swipe.isRefreshing = false
                        adapter.update(
                            LoadingAdapter.addLoadingItem(state.content.photos)
                        )
                    }
                    UiState.State.LOADING -> Unit
                    UiState.State.FAILED -> {
                        showSnackbarError(view, state.error!!.message)
                    }
                }
            }
        }
    }

    private fun navigateToPost(postId: Int) {
        replacer.replace(detailsFactory.create(postId))
    }
}