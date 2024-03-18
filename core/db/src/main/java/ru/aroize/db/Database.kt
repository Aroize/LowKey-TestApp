package ru.aroize.db

import androidx.room.Database
import androidx.room.RoomDatabase
import ru.aroize.db.dao.CachedFeedPost
import ru.aroize.db.dao.CachedFeedPostDao

@Database(
    entities = [
        CachedFeedPost::class,
    ],
    version = DatabaseConstants.VERSION
)
abstract class Database : RoomDatabase() {
    abstract fun cachedFeedPostDao(): CachedFeedPostDao
}