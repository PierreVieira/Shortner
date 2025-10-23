package com.pierre.shortner.core.room_provider.db

import androidx.room.ConstructedBy
import androidx.room.Database
import androidx.room.RoomDatabase
import com.pierre.shortner.core.room_provider.entity.LinkEntity
import com.pierre.shortner.core.room_provider.dao.LinkDao

@Database(
    entities = [LinkEntity::class],
    version = 1,
    exportSchema = false,
)
@ConstructedBy(DatabaseConstructor::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun linkDao(): LinkDao
}
