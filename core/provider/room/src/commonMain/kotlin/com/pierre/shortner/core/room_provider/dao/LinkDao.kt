package com.pierre.shortner.core.room_provider.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow
import com.pierre.shortner.core.room_provider.entity.LinkEntity

@Dao
interface LinkDao {
    @Query("SELECT * FROM links ORDER BY id DESC")
    fun getAllAsFlow(): Flow<List<LinkEntity>>

    @Query("SELECT * FROM links ORDER BY id DESC")
    suspend fun getAll(): List<LinkEntity>

    @Query("SELECT * FROM links WHERE id = :id")
    suspend fun getById(id: Long): LinkEntity?

    @Query("SELECT * FROM links WHERE shortedLink = :shortedLink")
    suspend fun getByShortedLink(shortedLink: String): LinkEntity?

    @Query("SELECT * FROM links WHERE originalLink = :originalLink")
    suspend fun getByOriginalLink(originalLink: String): LinkEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(entity: LinkEntity): Long

    @Update
    suspend fun update(entity: LinkEntity)

    @Query("DELETE FROM links WHERE id = :id")
    suspend fun deleteById(id: Long)

    @Query("DELETE FROM links WHERE shortedLink = :shortedLink")
    suspend fun deleteByShortedLink(shortedLink: String)

    @Query("DELETE FROM links")
    suspend fun clear()
}
