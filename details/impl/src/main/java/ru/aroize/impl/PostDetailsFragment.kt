package ru.aroize.impl

import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.View
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import ru.aroize.core.arch.PexelsFragment
import ru.aroize.core.arch.UiState
import ru.aroize.impl.di.PostDetailsUiComponentHolder

class PostDetailsFragment : PexelsFragment(R.layout.fragment_details) {

    private lateinit var imageView: AppCompatImageView
    private lateinit var photographerTv: AppCompatTextView
    private lateinit var altTv: AppCompatTextView

    private val viewModel: PostDetailsViewModel by viewModels {
        val postId = requireArguments().getInt(ARG_POST_ID)
        PostDetailsUiComponentHolder.get().postDetailsViewModel(postId)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        imageView = view.findViewById(R.id.image)
        photographerTv = view.findViewById(R.id.photographer)
        altTv = view.findViewById(R.id.alt)

        lifecycleScope.launch {
            viewModel.uiState.collectLatest { state ->
                when (state.state) {
                    UiState.State.READY -> {
                        updateUi(state.content)
                    }
                    UiState.State.LOADING -> Unit
                    UiState.State.FAILED -> {
                        showSnackbarError(view, state.error!!.message)
                    }
                }
            }
        }
    }

    private fun updateUi(content: PostDetailsContent) {
        val thumbnail = ColorDrawable(content.post.thumbnail).apply { alpha = 128 }
        Glide.with(imageView)
            .load(content.post.photoUrl)
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .placeholder(thumbnail)
            .into(imageView)
        photographerTv.text = content.post.photographer
        altTv.text = content.post.alt
    }

    companion object {

        private const val ARG_POST_ID = "post_id"

        fun createInstance(postId: Int) = PostDetailsFragment().apply {
            arguments = Bundle().also { bundle ->
                bundle.putInt(ARG_POST_ID, postId)
            }
        }
    }
}