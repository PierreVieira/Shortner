package com.pierre.shortener.di

import com.pierre.shortener.presentation.AppViewModel
import com.pierre.shortner.core.koin_initializer.di.commonKoinInitializer
import org.koin.core.KoinApplication
import org.koin.core.module.Module
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

private val appModule = module {
    viewModelOf(::AppViewModel)
}

fun initializeKoin(
    config: (KoinApplication.() -> Unit)? = null,
    platformModules: List<Module> = emptyList(),
) {
    commonKoinInitializer(
        extraModules = listOf(appModule) + platformModules,
        config = config
    )
}
