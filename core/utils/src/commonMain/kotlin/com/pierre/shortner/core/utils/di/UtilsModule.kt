package com.pierre.shortner.core.utils.di

import com.pierre.shortner.core.utils.date.LocalDateTimeProvider
import com.pierre.shortner.core.utils.date.LocalDateTimeProviderImpl
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.bind
import org.koin.dsl.module

val utilsModule = module {
    factoryOf(::LocalDateTimeProviderImpl).bind<LocalDateTimeProvider>()
}
