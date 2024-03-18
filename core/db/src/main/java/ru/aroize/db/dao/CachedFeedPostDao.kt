package ru.aroize.db.dao

import androidx.room.ColumnInfo
import androidx.room.Dao
import androidx.room.Entity
import androidx.room.Insert
import androidx.room.PrimaryKey
import androidx.room.Query
import androidx.room.Update


@Entity(tableName = "cached_feed_posts")
data class CachedFeedPost(
    @PrimaryKey val id: Int,
    @ColumnInfo("next_id") val nextId: Int? = null,
    val width: Int,
    val height: Int,
    val photographer: String,
    val color: String,
    val alt: String,
    val original: String,
    val large: String,
    val large2x: String,
    val medium: String,
    val small: String,
    val portrait: String,
    val landscape: String,
    val tiny: String
)

@Dao
abstract class CachedFeedPostDao {
    @Query("""
        WITH RECURSIVE related_entities AS (
            SELECT 
                id, next_id, width, height, 
                photographer, color, alt,
                original, large, large2x, medium, small, portrait, landscape, tiny
            FROM cached_feed_posts
            WHERE id = :id
            UNION ALL
            SELECT 
                e.id, e.next_id, e.width, e.height, 
                e.photographer, e.color, e.alt, 
                e.original, e.large, e.large2x, e.medium, e.small, e.portrait, e.landscape, e.tiny
            FROM cached_feed_posts AS e
            JOIN related_entities AS re ON e.id = re.next_id
        )
        SELECT * FROM related_entities LIMIT :limit OFFSET :offset;
    """)
    abstract suspend fun findLinkedBy(id: Int, limit: Int, offset: Int): List<CachedFeedPost>

    @Query("""
        WITH RECURSIVE related_entities AS (
            SELECT 
                id, next_id, width, height, 
                photographer, color, alt,
                original, large, large2x, medium, small, portrait, landscape, tiny
            FROM cached_feed_posts
            WHERE id = :id
            UNION ALL
            SELECT 
                e.id, e.next_id, e.width, e.height, 
                e.photographer, e.color, e.alt, 
                e.original, e.large, e.large2x, e.medium, e.small, e.portrait, e.landscape, e.tiny
            FROM cached_feed_posts AS e
            JOIN related_entities AS re ON e.id = re.next_id
        )
        SELECT * FROM related_entities;
    """)
    abstract suspend fun findAllLinkedBy(id: Int): List<CachedFeedPost>

    @Query("SELECT * FROM cached_feed_posts WHERE id in (:ids)")
    abstract suspend fun findCollisions(ids: List<Int>): List<CachedFeedPost>

    @Query("SELECT * FROM cached_feed_posts WHERE id = :id")
    abstract suspend fun findById(id: Int): CachedFeedPost?

    @Insert
    abstract suspend fun insert(entries: List<CachedFeedPost>)

    @Update
    abstract suspend fun update(entries: List<CachedFeedPost>)
}