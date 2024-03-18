package ru.aroize.feed.ui

import ru.aroize.core.navigation.FragmentFactory

interface FeedFragmentFactory: FragmentFactory<FeedFragment>

class FeedFragmentFactoryImpl : FeedFragmentFactory {
    override fun create(): FeedFragment = FeedFragment()
}