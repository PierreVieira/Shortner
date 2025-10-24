package com.pierre.shortner.feature.links.top_bar.presentation.di

import com.pierre.shortner.feature.links.top_bar.presentation.factory.DeleteAllUiActionFactory
import com.pierre.shortner.feature.links.top_bar.presentation.factory.ShortenerTopBarButtonsFactory
import com.pierre.shortner.feature.links.top_bar.presentation.factory.impl.DeleteAllUiActionFactoryImpl
import com.pierre.shortner.feature.links.top_bar.presentation.factory.impl.ShortenerTopBarButtonsFactoryImpl
import com.pierre.shortner.feature.links.top_bar.presentation.mapper.ShortenerTopBarUiEventToUiActionMapper
import com.pierre.shortner.feature.links.top_bar.presentation.mapper.ShortenerTopBarUiEventToUiActionMapperImpl
import com.pierre.shortner.feature.links.top_bar.presentation.viewmodel.ShortenerTopBarViewModel
import org.koin.core.module.dsl.factoryOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.bind
import org.koin.dsl.module

val shortenerTopBarPresentationModule = module {
    factoryOf(::ShortenerTopBarButtonsFactoryImpl).bind<ShortenerTopBarButtonsFactory>()
    factoryOf(::ShortenerTopBarUiEventToUiActionMapperImpl).bind<ShortenerTopBarUiEventToUiActionMapper>()
    factoryOf(::DeleteAllUiActionFactoryImpl).bind<DeleteAllUiActionFactory>()
    viewModelOf(::ShortenerTopBarViewModel)
}
