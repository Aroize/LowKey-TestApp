package ru.aroize.pexels

import android.app.Application
import ru.aroize.api.PostDetailsFragmentFactory
import ru.aroize.api.di.PostDetailsDIComponent
import ru.aroize.api.di.PostDetailsDIComponentHolder
import ru.aroize.db.DatabaseInitializer
import ru.aroize.impl.PostDetailsFragmentFactoryImpl
import ru.aroize.pexels.init.NetworkInitializer

class PexelsApp : Application() {
    override fun onCreate() {
        super.onCreate()
        initialize()
    }

    private fun initialize() {
        NetworkInitializer.init()
        DatabaseInitializer.init(this)
        PostDetailsDIComponentHolder.set {
            val postDetailsFragmentFactory = PostDetailsFragmentFactoryImpl()
            object : PostDetailsDIComponent {
                override val fragmentFactory: PostDetailsFragmentFactory
                    get() = postDetailsFragmentFactory
            }
        }
    }
}