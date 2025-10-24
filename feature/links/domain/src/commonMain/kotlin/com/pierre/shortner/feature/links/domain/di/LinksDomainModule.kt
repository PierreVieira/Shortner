package com.pierre.shortner.feature.links.domain.di

import com.pierre.shortner.feature.links.domain.usecase.DeleteLinkUseCase
import com.pierre.shortner.feature.links.domain.usecase.GetAllLinksUseCase
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.module

val linksDomainModule = module {
    factoryOf(::GetAllLinksUseCase)
    factoryOf(::DeleteLinkUseCase)
}
