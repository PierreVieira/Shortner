package com.pierre.shortner.core.room_provider.di

import androidx.room.RoomDatabase
import androidx.sqlite.driver.bundled.BundledSQLiteDriver
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import org.koin.dsl.module
import com.pierre.shortner.core.room_provider.db.AppDatabase
import com.pierre.shortner.core.room_provider.dao.LinkDao

val roomModule = module {
    single<AppDatabase> {
        val builder: RoomDatabase.Builder<AppDatabase> = get()
        builder
            .setDriver(BundledSQLiteDriver())
            .setQueryCoroutineContext(Dispatchers.IO)
            .fallbackToDestructiveMigration(true)
            .build()
    }

    single<LinkDao> { get<AppDatabase>().linkDao() }
}
