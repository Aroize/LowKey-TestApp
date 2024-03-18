package ru.aroize.db.di

import ru.aroize.core.di.DIComponent
import ru.aroize.core.di.LazyComponentHolder
import ru.aroize.db.dao.CachedFeedPostDao

interface DatabaseComponent : DIComponent {
    fun cachedFeedPostDao(): CachedFeedPostDao
}

object DatabaseComponentHolder: LazyComponentHolder<DatabaseComponent>()