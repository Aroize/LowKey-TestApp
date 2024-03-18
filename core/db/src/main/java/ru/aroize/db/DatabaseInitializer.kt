package ru.aroize.db

import android.content.Context
import androidx.room.Room
import ru.aroize.db.dao.CachedFeedPostDao
import ru.aroize.db.di.DatabaseComponent
import ru.aroize.db.di.DatabaseComponentHolder

object DatabaseInitializer {
    fun init(appContext: Context) {
        DatabaseComponentHolder.set {
            val room = Room.databaseBuilder(
                appContext,
                Database::class.java,
                DatabaseConstants.NAME
            ).build()
            object : DatabaseComponent {
                override fun cachedFeedPostDao(): CachedFeedPostDao = room.cachedFeedPostDao()
            }
        }
    }
}