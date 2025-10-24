package com.pierre.shortner.core.room_provider.db

import androidx.room.Room
import androidx.room.RoomDatabase
import java.io.File
import com.pierre.shortner.core.room_provider.utils.DatabaseUtils

fun getDatabaseBuilder(): RoomDatabase.Builder<AppDatabase> {
    val dbFile = File(System.getProperty("java.io.tmpdir"), DatabaseUtils.PATH)
    return Room.databaseBuilder<AppDatabase>(
        name = dbFile.absolutePath,
    )
}

