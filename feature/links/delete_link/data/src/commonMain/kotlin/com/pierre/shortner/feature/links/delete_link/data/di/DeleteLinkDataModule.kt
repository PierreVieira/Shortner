package com.pierre.shortner.feature.links.delete_link.data.di

import com.pierre.shortner.feature.links.delete_link.data.repository.DeleteLinkRepositoryImpl
import com.pierre.shortner.feature.links.delete_link.domain.repository.DeleteLinkRepository
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.bind
import org.koin.dsl.module

val deleteLinkDataModule = module {
    factoryOf(::DeleteLinkRepositoryImpl).bind<DeleteLinkRepository>()
}
