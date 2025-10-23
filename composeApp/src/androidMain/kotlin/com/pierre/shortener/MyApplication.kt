package com.pierre.shortener

import android.app.Application
import com.pierre.shortener.di.initializeKoin
import com.pierre.shortner.core.room_provider.db.getDatabaseBuilder
import com.pierre.shortner.material_you.data.di.materialYouDataModule
import com.pierre.shortner.material_you.domain.di.materialYouDomainModule
import com.pierre.shortner.material_you.presentation.di.materialYouPresentationModule
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

class MyApplication: Application() {
    override fun onCreate() {
        super.onCreate()
        val context = this@MyApplication
        val androidRoomModule = module {
            single { getDatabaseBuilder(context) }
        }
        initializeKoin(
            config = {
                androidContext(context)
            },
            platformModules = listOf(
                androidRoomModule,
                materialYouDataModule,
                materialYouDomainModule,
                materialYouPresentationModule,
            )
        )
    }
}
