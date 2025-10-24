package com.pierre.shortner.feature.links.data.di

import com.pierre.shortner.feature.links.data.repository.LinksRepositoryImpl
import com.pierre.shortner.feature.links.domain.repository.LinksRepository
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.module

val linksDataModule = module {
    factoryOf(::LinksRepositoryImpl) { bind<LinksRepository>() }
}
