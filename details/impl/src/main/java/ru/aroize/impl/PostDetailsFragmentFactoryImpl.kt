package ru.aroize.impl

import androidx.fragment.app.Fragment
import ru.aroize.api.PostDetailsFragmentFactory

class PostDetailsFragmentFactoryImpl: PostDetailsFragmentFactory {

    override fun create(): Fragment = throw NotImplementedError()

    override fun create(postId: Int): Fragment = PostDetailsFragment.createInstance(postId)
}