package ru.aroize.feed.ui

import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import ru.aroize.core.recycler.AdapterDelegate
import ru.aroize.core.recycler.DelegateViewHolder
import ru.aroize.core.recycler.ListItem
import ru.aroize.feed.R
import ru.aroize.feed.ui.model.FeedPostUi

class FeedPostAdapterDelegate(
    private val onClick: (Int) -> Unit
) : AdapterDelegate<FeedPostUi> {

    override fun isForViewType(item: ListItem): Boolean = item is FeedPostUi

    override fun createViewHolder(
        inflater: LayoutInflater,
        parent: ViewGroup
    ): DelegateViewHolder<FeedPostUi> {
        return FeedPostViewHolder(
            inflater.inflate(R.layout.list_item_post, parent, false),
            onClick
        )
    }

    class FeedPostViewHolder(
        view: View,
        private val onClick: (Int) -> Unit
    ) : DelegateViewHolder<FeedPostUi>(view) {

        private lateinit var post: FeedPostUi

        private val imageView = itemView.findViewById<AppCompatImageView>(R.id.image)
        private val authorView = itemView.findViewById<AppCompatTextView>(R.id.author)

        init {
            itemView.setOnClickListener { onClick(post.id) }
        }

        override fun bind(item: FeedPostUi) {
            post = item
            val thumbnail = ColorDrawable(item.thumbnail).apply { alpha = 128 }
            authorView.text = item.photographer
            Glide.with(imageView)
                .load(item.photoUrl)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .placeholder(thumbnail)
                .centerCrop()
                .into(imageView)
        }
    }
}