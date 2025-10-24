package com.pierre.shortner.feature.links.input.data.di

import com.pierre.shortner.feature.links.input.data.repository.LinkInputRepositoryImpl
import com.pierre.shortner.feature.links.input.data.repository.datasource.LinkInputRemoteDataSource
import com.pierre.shortner.feature.links.input.data.repository.datasource.LinkInputRemoteDataSourceImpl
import com.pierre.shortner.feature.links.input.domain.repository.LinkInputRepository
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.bind
import org.koin.dsl.module

val inputDataModule = module {
    factoryOf(::LinkInputRemoteDataSourceImpl).bind<LinkInputRemoteDataSource>()
    factoryOf(::LinkInputRepositoryImpl).bind<LinkInputRepository>()
}
