package ru.aroize.core.arch

import android.content.Context
import android.view.View
import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment
import ru.aroize.core.navigation.FragmentReplacer

abstract class PexelsFragment(@LayoutRes layoutResId: Int = 0) : Fragment(layoutResId) {

    protected lateinit var replacer: FragmentReplacer

    override fun onAttach(context: Context) {
        super.onAttach(context)
        replacer = (requireActivity() as? FragmentReplacer)
            ?: throw IllegalStateException("Parent activity must implement FragmentReplacer interface")
    }

    protected fun showSnackbarError(view: View, text: String) {
        UsableSnackbar.builder()
            .layoutId(R.layout.snackbar_view)
            .text(R.id.snackbar_tv, text)
            .build()
            .show(view)
    }
}