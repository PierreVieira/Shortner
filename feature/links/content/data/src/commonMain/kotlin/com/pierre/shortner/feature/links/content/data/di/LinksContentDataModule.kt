package com.pierre.shortner.feature.links.content.data.di

import com.pierre.shortner.feature.links.content.data.mapper.LinkEntityMapper
import com.pierre.shortner.feature.links.content.data.mapper.LinkEntityMapperImpl
import com.pierre.shortner.feature.links.content.data.repository.LinksRepositoryImpl
import com.pierre.shortner.feature.links.content.domain.repository.LinksRepository
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.bind
import org.koin.dsl.module

val linksContentDataModule = module {
    factoryOf(::LinksRepositoryImpl).bind<LinksRepository>()
    factoryOf(::LinkEntityMapperImpl).bind<LinkEntityMapper>()
}
