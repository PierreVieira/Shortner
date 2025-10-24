package com.pierre.shortner.feature.links.top_bar.data.di

import com.pierre.shortner.feature.links.top_bar.data.repository.ShortenerTopBarRepositoryImpl
import com.pierre.shortner.feature.links.top_bar.domain.repository.ShortenerTopBarRepository
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.bind
import org.koin.dsl.module

val shortenerTopBarDataModule = module {
    factoryOf(::ShortenerTopBarRepositoryImpl).bind<ShortenerTopBarRepository>()
}
