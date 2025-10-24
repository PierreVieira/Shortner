package com.pierre.shortner.feature.links.delete_all.di

import com.pierre.shortner.feature.links.delete_all.data.DeleteAllLinksRepositoryImpl
import com.pierre.shortner.feature.links.delete_all.domain.repository.DeleteAllLinksRepository
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.bind
import org.koin.dsl.module

val deleteAllLinksDataModule = module {
    factoryOf(::DeleteAllLinksRepositoryImpl).bind<DeleteAllLinksRepository>()
}
