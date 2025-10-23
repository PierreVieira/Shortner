package com.pierre.shortner.feature.links.data.di

import com.pierre.shortner.core.room_provider.dao.LinkDao
import com.pierre.shortner.feature.links.data.datasource.LinksRemoteDataSource
import com.pierre.shortner.feature.links.data.datasource.LinksRemoteDataSourceImpl
import com.pierre.shortner.feature.links.data.repository.LinksRepositoryImpl
import com.pierre.shortner.feature.links.domain.repository.LinksRepository
import com.pierre.shortner.network.data.handler.RequestHandler
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.module

val linksDataModule = module {
    factoryOf(::LinksRemoteDataSourceImpl) { bind<LinksRemoteDataSource>() }
    factoryOf(::LinksRepositoryImpl) { bind<LinksRepository>() }
}
