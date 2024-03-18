package ru.aroize.core.arch

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import androidx.annotation.IdRes
import androidx.annotation.LayoutRes
import androidx.core.view.setPadding
import com.google.android.material.snackbar.Snackbar


interface UsableSnackbar {
    fun show(view: View)
    fun dismiss()

    companion object {
        fun builder() = Builder()
    }
}

class Builder {

    @IdRes
    private var primaryButtonId: Int = 0
    @IdRes
    private var secondaryButtonId: Int = 0
    @IdRes
    private var textId: Int = 0
    @LayoutRes
    private var layoutId: Int = 0
    private var text: String = ""
    private var primaryText: String = ""
    private var secondaryText: String = ""
    private var primaryClick: () -> Unit = {  }
    private var secondaryClick: () -> Unit = {  }
    private var dismissOnClick: Boolean = true
    private var duration: Int = Snackbar.LENGTH_LONG

    fun layoutId(@LayoutRes id: Int) = apply { layoutId = id }
    fun text(@IdRes id: Int, value: String) = apply {
        textId = id
        text = value
    }
    fun onPrimaryClick(@IdRes id: Int, block: () -> Unit) = apply {
        primaryButtonId = id
        primaryClick = block
    }
    fun primaryText(text: String) = apply { primaryText = text }
    fun onSecondaryClick(@IdRes id: Int, block: () -> Unit) = apply {
        secondaryButtonId = id
        secondaryClick = block
    }
    fun secondaryText(text: String) = apply { secondaryText = text }
    fun duration(value: Int) = apply { duration = value }
    fun dismissOnClick(value: Boolean) = apply { dismissOnClick = value }

    fun build(): UsableSnackbar = UsableSnackbarImpl(
        primaryButtonId, secondaryButtonId, textId, text, primaryText, secondaryText, primaryClick, secondaryClick, dismissOnClick, layoutId, duration
    )
}

private class UsableSnackbarImpl(
    @IdRes private val primaryButtonId: Int,
    @IdRes private val secondaryButtonId: Int,
    @IdRes private val textId: Int,
    private val text: String,
    private val primaryText: String,
    private val secondaryText: String,
    private val onPrimaryClick: () -> Unit,
    private val onSecondaryClick: () -> Unit,
    private val dismissOnClick: Boolean,
    @LayoutRes private val layoutId: Int,
    private val duration: Int
): UsableSnackbar {

    private var snackbar: Snackbar? = null

    override fun show(view: View) {
        if (snackbar != null) return
        snackbar = Snackbar.make(view, "", duration)
        snackbar?.let { sb ->
            val inflater = LayoutInflater.from(view.context)
            val snackbarView = inflater.inflate(layoutId, null)
            snackbarView.findViewById<TextView>(textId)?.let {
                it.text = text
            }
            snackbarView.findViewById<TextView>(primaryButtonId)?.let {
                it.text = primaryText
                it.setOnClickListener {
                    onPrimaryClick()
                    if (dismissOnClick) dismiss()
                }
            }
            snackbarView.findViewById<TextView>(secondaryButtonId)?.let {
                it.text = secondaryText
                it.setOnClickListener {
                    onSecondaryClick()
                    if (dismissOnClick) dismiss()
                }
            }
            sb.view.setBackgroundColor(Color.TRANSPARENT)
            val sbLayout = sb.view as Snackbar.SnackbarLayout
            sbLayout.setPadding(0)
            sbLayout.addView(snackbarView, 0)
            sb.show()
        }
    }
    override fun dismiss() {
        snackbar?.dismiss()
        snackbar = null
    }
}