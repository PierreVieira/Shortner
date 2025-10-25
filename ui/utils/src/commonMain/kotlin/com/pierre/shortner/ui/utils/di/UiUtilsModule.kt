package com.pierre.shortner.ui.utils.di

import com.pierre.shortner.ui.utils.string_provider.StringProvider
import com.pierre.shortner.ui.utils.string_provider.StringProviderImpl
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.bind
import org.koin.dsl.module

val uiUtilsModule = module {
    factoryOf(::StringProviderImpl).bind<StringProvider>()
}
