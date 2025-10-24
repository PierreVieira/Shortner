package com.pierre.shortner.feature.links.top_bar.di

import com.pierre.shortner.feature.links.top_bar.viewmodel.ShortenerTopBarViewModel
import com.pierre.shortner.feature.links.top_bar.factory.ShortenerTopBarButtonsFactory
import com.pierre.shortner.feature.links.top_bar.factory.ShortenerTopBarButtonsFactoryImpl
import com.pierre.shortner.feature.links.top_bar.mapper.ShortenerTopBarUiEventMapper
import com.pierre.shortner.feature.links.top_bar.mapper.ShortenerTopBarUiEventMapperImpl
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.bind
import org.koin.dsl.module
import org.koin.core.module.dsl.viewModelOf

val shortenerTopBarModule = module {
    factoryOf(::ShortenerTopBarUiEventMapperImpl).bind<ShortenerTopBarUiEventMapper>()
    factoryOf(::ShortenerTopBarButtonsFactoryImpl).bind<ShortenerTopBarButtonsFactory>()
    factoryOf(::ShortenerTopBarUiEventMapperImpl).bind<ShortenerTopBarUiEventMapper>()
    viewModelOf(::ShortenerTopBarViewModel)
}
