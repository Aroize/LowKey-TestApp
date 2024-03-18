package ru.aroize.api

import androidx.fragment.app.Fragment
import ru.aroize.core.navigation.FragmentFactory

interface PostDetailsFragmentFactory: FragmentFactory<Fragment> {
    fun create(postId: Int): Fragment
}