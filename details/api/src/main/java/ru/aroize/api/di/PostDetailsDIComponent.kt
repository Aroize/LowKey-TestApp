package ru.aroize.api.di

import ru.aroize.api.PostDetailsFragmentFactory
import ru.aroize.core.di.DIComponent
import ru.aroize.core.di.LazyComponentHolder

interface PostDetailsDIComponent: DIComponent {
    val fragmentFactory: PostDetailsFragmentFactory
}

object PostDetailsDIComponentHolder : LazyComponentHolder<PostDetailsDIComponent>()