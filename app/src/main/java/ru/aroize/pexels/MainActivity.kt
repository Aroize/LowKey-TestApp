package ru.aroize.pexels

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import ru.aroize.core.navigation.FragmentReplacer
import ru.aroize.feed.ui.FeedFragmentFactory
import ru.aroize.feed.di.FeedComponentHolder

class MainActivity : AppCompatActivity(), FragmentReplacer {

    private lateinit var feedFragmentFactory: FeedFragmentFactory

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        supportActionBar?.hide()

        feedFragmentFactory = FeedComponentHolder.get().feedFragmentFactory()

        setContentView(R.layout.fragment_container)

        supportFragmentManager.beginTransaction()
            .add(R.id.container, feedFragmentFactory.create())
            .commitAllowingStateLoss()
    }

    override fun replace(to: Fragment) {
        supportFragmentManager.beginTransaction()
            .setCustomAnimations(
                androidx.appcompat.R.anim.abc_fade_in, androidx.appcompat.R.anim.abc_fade_out,
                androidx.appcompat.R.anim.abc_fade_in, androidx.appcompat.R.anim.abc_fade_out
            )
            .replace(R.id.container, to)
            .addToBackStack(null)
            .commit()
    }
}
