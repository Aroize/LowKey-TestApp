package ru.aroize.core.navigation

import androidx.fragment.app.Fragment

interface FragmentFactory<T: Fragment> {
    fun create(): T
}