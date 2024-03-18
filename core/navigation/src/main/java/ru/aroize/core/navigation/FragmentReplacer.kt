package ru.aroize.core.navigation

import androidx.fragment.app.Fragment

interface FragmentReplacer {
    fun replace(to: Fragment)
}